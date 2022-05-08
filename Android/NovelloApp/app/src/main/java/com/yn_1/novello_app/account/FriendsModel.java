package com.yn_1.novello_app.account;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.volley_requests.JsonArrayRequester;
import com.yn_1.novello_app.volley_requests.JsonObjectRequester;
import com.yn_1.novello_app.volley_requests.VolleyCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Model for friends screen
 */
public class FriendsModel {

    FriendsPresenter presenter;

    /**
     * Constructor
     * @param presenter is the friends presenter
     */
    public FriendsModel(FriendsPresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Requests to add a friend
     * @param username to add
     */
    public void addFriendRequest(String username) {
        JsonObjectRequester jsonObjectRequester = new JsonObjectRequester();
        JsonObjectCommand command = new JsonObjectCommand();
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("senderId", ((NavBarActivity)presenter.view.getActivity()).getUser().getUserId());
            jsonObject.put("receiverusrname", username);
            jsonObject.put("friendshipStatus", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        jsonObjectRequester.postRequest("friend", jsonObject, command, null, null);
    }

    /**
     * Parses received data and sends success status to presenter
     * @param data
     */
    private void parseData(JSONObject data) {
        boolean success = data != null;
        presenter.friendAdded(success);
    }

    /**
     * Class used to get result of request
     */
    private class JsonObjectCommand implements VolleyCommand<JSONObject> {

        @Override
        public void execute(JSONObject data) {
            parseData(data);
        }

        @Override
        public void onError(VolleyError error) {

        }

    }

}
