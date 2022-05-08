package com.yn_1.novello_app.chat;

import com.yn_1.novello_app.account.User;

import java.util.List;

public interface ChatContract {
    interface Model {
        enum ChatType {
            NULL(""),
            PUBLIC("public"),
            PRIVATE("private");

            private String string;

            ChatType(String string) {
                this.string = string;
            }

            @Override
            public String toString() {
                return string;
            }
        };

        void fetchChats(ChatType type, VolleyListener listener);

        User getUser();

        List<Chat> getPrivateChats();

        List<Chat> getPublicChats();

        int getPrivateChatCount();

        int getPublicChatCount();

        int getTotalChatCount();
    }

    interface View {
        void initializeTabListener();
        void navigateToCreateChatScreen();
    }

    interface Presenter {
        void onFragmentCreated();
        List<Chat>[] transferChatsToView();
    }

    interface VolleyListener {
        void onChatsReceived();
    }
}
