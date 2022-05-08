package com.yn_1.novello_app.account;

import com.yn_1.novello_app.cart.CartModel;
import com.yn_1.novello_app.cart.CartView;

import java.util.ArrayList;

/**
 * Profile screen presenter
 */
public class ProfilePresenter {

    ProfileView view;
    ProfileModel model;

    User user;

    ArrayList<String> friendsList;

    /**
     * Constructor
     * Creates model
     */
    public ProfilePresenter(ProfileView view) {
        this.view = view;
        model = new ProfileModel(this);
    }

    /**
     * Sets user for Presenter and Model
     * @param user current user
     */
    public void setUser(User user) {
        this.user = user;
        model.setUser(user);
    }

    /**
     * Sends database request to obtain the friend's lit
     */
    public void showFriendsList() {
        model.getFriendsList();
    }

    /**
     * Sets the friends list
     * @param friendsList is the user's friend list (username only)
     */
    public void setFriendsList(ArrayList<String> friendsList) {
        this.friendsList = friendsList;
        String[] friendsArray = new String[friendsList.size()];
        for (int i = 0; i < friendsList.size(); i++) {
            friendsArray[i] = friendsList.get(i);
        }
        view.showFriends(friendsArray);
    }

}
