package com.yn_1.novello_app.create_chat;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.volley_requests.JsonArrayRequester;
import com.yn_1.novello_app.volley_requests.VolleyCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CreateChatModel implements CreateChatContract.Model {

    User currentUser;
    CreateChatContract.VolleyListener listener;

    List<Integer> ids;
    List<String> usernames;

    public CreateChatModel(User currentUser) {
        this.currentUser = currentUser;
    }

    @Override
    public void setListener(CreateChatContract.VolleyListener listener) {
        this.listener = listener;
    }

    @Override
    public void fetchFriends() {
        JsonArrayRequester req = new JsonArrayRequester();
        req.getRequest("friends/" + currentUser.getUserId(), null, new VolleyCommand<JSONArray>() {
            @Override
            public void execute(JSONArray data) {
                ids = new ArrayList<>();
                usernames = new ArrayList<>();

                for (int i = 0; i < data.length(); i++) {
                    try {
                        JSONObject object = data.getJSONObject(i);
                        int id = object.getJSONObject("receiver").getInt("id");
                        ids.add(id);
                        String username = object.getJSONObject("receiver").getString("username");
                        usernames.add(username);
                        if (i == data.length() - 1) {
                            listener.onFriendFetched(ids, usernames);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        }, null, null);
    }

    @Override
    public void sendChat(JSONArray friends) {
        JsonArrayRequester req = new JsonArrayRequester();
        req.postRequest("chatRoom", friends, new VolleyCommand<JSONArray>() {
            @Override
            public void execute(JSONArray data) {
                // listener.onFriendsCreated();
            }

            @Override
            public void onError(VolleyError error) {

            }
        }, null, null);
    }

    public List<Integer> getIds() {
        return ids;
    }
}
