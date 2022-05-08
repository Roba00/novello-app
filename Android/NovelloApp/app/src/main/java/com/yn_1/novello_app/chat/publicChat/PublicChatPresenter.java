package com.yn_1.novello_app.chat.publicChat;

import com.yn_1.novello_app.chat.Chat;

import java.util.List;

public class PublicChatPresenter implements PublicChatContract.Presenter, PublicChatContract.VolleyListener {

    PublicChatContract.Model model;
    PublicChatContract.View view;

    public PublicChatPresenter(PublicChatContract.Model model, PublicChatContract.View view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void onFragmentCreated() {
        model.fetchProfileImagesOfChat(this);
    }

    @Override
    public List<Chat> transferChatsToView() {
        return model.getChats();
    }

    @Override
    public void onAllProfileImagesFetched() {
        view.displayChatList(model.getChats(), model.getUserProfileImages());
    }
}
