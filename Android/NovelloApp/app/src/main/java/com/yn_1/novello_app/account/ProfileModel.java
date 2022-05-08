package com.yn_1.novello_app.account;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.cart.CartPresenter;
import com.yn_1.novello_app.volley_requests.JsonArrayRequester;
import com.yn_1.novello_app.volley_requests.VolleyCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Profile screen model.
 */
public class ProfileModel {

    ProfilePresenter presenter;

    User user;

    /**
     * Constructor
     * Creates a presenter
     */
    public ProfileModel(ProfilePresenter presenter) {
        this.presenter = presenter;
    }

    /**
     * Sets the user
     * @param user current user
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * Receives the friends list from the database
     */
    public void getFriendsList() {
        JsonArrayRequester jsonArrayRequester = new JsonArrayRequester();
        JsonArrayCommand command = new JsonArrayCommand();
        jsonArrayRequester.getRequest("friends/" + user.getUserId(), null, command, null, null);
    }

    /**
     * Sends friends list to presenter
     * @param friendsList is the user's friend list (usernames only)
     */
    private void setFriendsList(ArrayList<String> friendsList) {
        presenter.setFriendsList(friendsList);
    }

    /**
     * Class used to get result of request
     */
    private class JsonArrayCommand implements VolleyCommand<JSONArray> {

        @Override
        public void execute(JSONArray data) {
            //todo: I am given lots of objects and I only need some of them
            try {
                ArrayList<String> friendsList = new ArrayList<>();
                for (int i = 0; i < data.length(); i++) {
                    JSONObject friendJson = data.getJSONObject(i).getJSONObject("receiver");
                    friendsList.add(friendJson.getString("username"));
                }
                setFriendsList(friendsList);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(VolleyError error) {

        }

    }

}
