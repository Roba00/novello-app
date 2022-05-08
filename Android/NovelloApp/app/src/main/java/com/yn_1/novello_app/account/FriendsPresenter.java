package com.yn_1.novello_app.account;

/**
 * Presenter for friends screen
 */
public class FriendsPresenter {

    FriendsView view;
    FriendsModel model;

    /**
     * Constructor
     * @param view is the friends view
     */
    public FriendsPresenter(FriendsView view) {
        this.view = view;
        this.model = new FriendsModel(this);
    }

    /**
     * Requests to add a friend
     * @param username to add
     */
    public void addFriendRequest(String username) {
        model.addFriendRequest(username);
    }

    /**
     * Informs view of whether or not friend was added
     * @param success is true if the friend was added
     */
    public void friendAdded(boolean success) {
        view.friendAdded(success);
    }

}
