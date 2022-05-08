package com.yn_1.novello_app.message;

import android.util.Log;

import androidx.annotation.Nullable;

import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.account.User;

import org.java_websocket.handshake.ServerHandshake;

import java.util.Date;
import java.util.Scanner;

public class MessagePresenter implements MessageContract.Presenter, MessageContract.WebSocketListener {

    private MessageContract.Model model;
    private MessageContract.View view;

    public MessagePresenter(MessageContract.Model model, MessageContract.View view) {
        this.model = model;
        this.view = view;
        model.setListener(this);
    }

    @Override
    public void onFragmentCreated() {
        model.beginWebSocket();
    }

    @Override
    public void onSendButtonClicked(String message) {
        String send = "\n";
        User user =((NavBarActivity) ((MessageFragment) view).getActivity()).getUser();
        send += user.getUserId() + "\n";
        send += user.getUsername() + "\n";
        send += System.currentTimeMillis() + "\n";
        send += message;
        model.sendClientMessage(send);
    }


    @Override
    public void onOpen(ServerHandshake handshake) {

    }

    @Override
    public void onMessage(@Nullable String message) {
        Scanner scan = new Scanner(message);
        while (scan.hasNextLine()) {
            scan.nextLine();
            int userId = scan.nextInt();
            scan.nextLine();
            String username = scan.nextLine();
            Date date = new Date(scan.nextLong());
            scan.nextLine();
            String messageBody = scan.nextLine();

            // TODO: Replace placeholder arguments!
            Message messageObject = new Message(-1, null, userId, username, date, messageBody);

            model.addMessageToList(messageObject);
            view.notifyRecyclerMessageAdded(model.getMessageList().size(), messageObject);
        }
        scan.close();
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {

    }

    @Override
    public void onExit() {
        model.closeSocket();
    }
}
