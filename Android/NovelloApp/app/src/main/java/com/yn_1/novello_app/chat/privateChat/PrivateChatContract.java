package com.yn_1.novello_app.chat.privateChat;

import android.graphics.Bitmap;

import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.chat.Chat;

import java.util.List;
import java.util.Map;

public interface PrivateChatContract {
    interface Model {
        int[] profileImageSize = {-1, -1};

        void fetchProfileImagesOfChat(PrivateChatContract.VolleyListener listener);

        User getUser();
        List<Chat> getChats();
        Map<Integer, Bitmap> getUserProfileImages();
        int getChatCount();
    }

    interface View {
        void displayChatList(List<Chat> items1, Map<Integer, Bitmap> items2);
    }

    interface Presenter {
        void onFragmentCreated();
        List<Chat> transferChatsToView();
    }

    interface VolleyListener {
        void onAllProfileImagesFetched();
    }

    interface ViewListener {
        void navigateToMessageView(int id);
    }
}
