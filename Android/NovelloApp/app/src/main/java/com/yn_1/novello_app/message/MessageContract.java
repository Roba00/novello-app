package com.yn_1.novello_app.message;

import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.util.List;

public interface MessageContract {
    interface Model {
        void setListener(WebSocketListener listener);
        void beginWebSocket();
        void sendClientMessage(String message);
        void addMessageToList(Message message);
        List<Message> getMessageList();
        void closeSocket();
    }

    interface View {
        String getInputText();
        void notifyRecyclerMessageAdded(int finalElementIndex, Message message);
    }

    interface Presenter {
        void onFragmentCreated();
        void onSendButtonClicked(String message);
        void onExit();
    }

    interface WebSocketListener {
        void onOpen(ServerHandshake handshake);
        void onMessage(@Nullable String message);
        void onClose(int code, String reason, boolean remote);
    }
}
