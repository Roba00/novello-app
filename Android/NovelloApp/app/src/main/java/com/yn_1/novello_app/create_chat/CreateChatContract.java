package com.yn_1.novello_app.create_chat;

import org.json.JSONArray;

import java.util.List;

public interface CreateChatContract {
    interface Model {
        void setListener(VolleyListener listener);
        void fetchFriends();
        void sendChat(JSONArray send);
        List<Integer> getIds();
    }

    interface View {
        void createFriendsList(List<Integer> friendIds, List<String> friendUsernames);
        void navigateToMessageScreen();
        String getInputtedTitle();
        int getInputtedAccess();
    }

    interface Presenter {
        void onFragmentCreated();
        void onCreateChatButtonPressed(boolean[] friendsSelected);
    }

    interface VolleyListener {
        void onFriendFetched(List<Integer> friendIds, List<String> friendUsernames);
        void onFriendsCreated();
    }
}
