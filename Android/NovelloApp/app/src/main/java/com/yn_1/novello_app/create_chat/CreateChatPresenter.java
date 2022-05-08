package com.yn_1.novello_app.create_chat;

import com.yn_1.novello_app.NavBarActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class CreateChatPresenter implements CreateChatContract.Presenter, CreateChatContract.VolleyListener {

    CreateChatContract.Model model;
    CreateChatContract.View view;

    public CreateChatPresenter(CreateChatContract.Model model, CreateChatContract.View view) {
        this.model = model;
        this.view = view;
        model.setListener(this);
    }

    @Override
    public void onFragmentCreated() {
        model.fetchFriends();
    }

    @Override
    public void onCreateChatButtonPressed(boolean[] friendsSelected) {
        JSONArray body = new JSONArray();
        if (!view.getInputtedTitle().equals("") && view.getInputtedAccess() != -1)
        {
            try {
                JSONObject chatTitle = new JSONObject();
                chatTitle.put("name", view.getInputtedTitle());;
                chatTitle.put("type", view.getInputtedAccess());
                body.put(chatTitle);

                JSONObject currentUser = new JSONObject();
                currentUser.put("userId", ((NavBarActivity) ((CreateChatFragment)view).
                        getActivity()).getUser().getUserId());
                body.put(currentUser);

                for (int i = 0; i < friendsSelected.length; i++) {
                    if (friendsSelected[i]) {
                        // selectedFriendIds.add(model.getIds().get(i));
                        JSONObject userInChat = new JSONObject();
                        userInChat.put("userId", model.getIds().get(i));
                        body.put(userInChat);
                    }
                }

                if (body.length() > 2) {
                    // model.sendChat(selectedFriendIds);
                    model.sendChat(body);
                    onFriendsCreated();
                }
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onFriendFetched(List<Integer> friendIds, List<String> friendUsernames) {
        view.createFriendsList(friendIds, friendUsernames);
    }

    @Override
    public void onFriendsCreated() {
        view.navigateToMessageScreen();
    }
}
