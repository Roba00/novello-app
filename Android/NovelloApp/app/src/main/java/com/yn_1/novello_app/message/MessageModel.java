package com.yn_1.novello_app.message;

import android.util.Log;

import com.yn_1.novello_app.Const;
import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.chat.Chat;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MessageModel implements MessageContract.Model {

    MessageContract.WebSocketListener websocketListener;

    Chat chat;
    User currentUser;
    ArrayList<Message> messageList = new ArrayList<>();

    private WebSocketClient cc;


    public MessageModel(Chat chat, User currentUser, List<Message> messageList) {
        this.chat = chat;
        this.currentUser = currentUser;
    }

    @Override
    public void setListener(MessageContract.WebSocketListener listener) {
        websocketListener = listener;
    }

    @Override
    public void beginWebSocket() {
        Draft[] drafts = {
                new Draft_6455()
        };

        final String url = Const.baseUrl + "chat/" + chat.getChatId()
                + "/" + currentUser.getUserId();

        try {
            Log.d("Socket", "Attempting connection");
            cc = new WebSocketClient(new URI(url), (Draft) drafts[0]) {
                @Override
                public void onMessage(String message) {
                    Log.d("Socket MESSAGE", "onMessage() returned: \n" + message);
                    websocketListener.onMessage(message);
                    // cc.reconnect();
                }

                @Override
                public void onOpen(ServerHandshake handshake) {
                    Log.d("Socket OPEN", "onOpen() returned: " + "is connecting");
                    websocketListener.onOpen(handshake);
                }

                @Override
                public void onClose(int code, String reason, boolean remote) {
                    Log.d("Socket CLOSE", "onClose() returned: " + reason);
                    websocketListener.onClose(code, reason, remote);
                }

                @Override
                public void onError(Exception e) {
                    Log.d("Socket ERROR", "onError() returned: " + e.getMessage().toString());
                }
            };
            cc.connect();
        } catch (URISyntaxException e) {
            Log.d("URI Exception", e.getMessage().toString());
            e.printStackTrace();
        }
    }

    @Override
    public void sendClientMessage(String message) {
        cc.send(message);
    }

    @Override
    public void addMessageToList(Message message) {
        messageList.add(message);
    }

    @Override
    public List<Message> getMessageList() {
        return messageList;
    }

    @Override
    public void closeSocket() {
        cc.close();
        Log.d("Socket", "Socket closed.");
    }
}
