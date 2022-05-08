package com.yn_1.novello_app.account;

import android.os.Parcelable;

import com.yn_1.novello_app.book.Book;

import java.io.Serializable;
import java.util.List;

/**
 * User interface representing a user account. <br>
 * The creation of a user account should be done in user implementation constructor.
 *
 * @author Roba Abbajabal
 */
public interface User extends Parcelable {

    /**
     * Sets the username
     * @param username username
     */
    void setUsername(String username);

    /**
     * Sets the password
     * @param password password
     */
    void setPassword(String password);

    /**
     * Sets the user type
     * @param type account type
     */
    void setUserType(UserType type);

    /**
     *
     * @param url Url to the user profile image
     */
    void setProfileImageUrl(String url);

    /**
     *
     * @param name The name of the user
     */
    void setName(String name);

    /**
     * @return the user ID
     */
    int getUserId();

    /**
     * @return the username
     */
    String getUsername();

    /**
     * @return the password
     */
    String getPassword();

    /**
     * @return the user type
     */
    UserType getUserType();

    /**
     * @return a list of friends
     */
    List<User> getFriends();

    /**
     * @return a list of books in the library
     */
    List<Book> getLibrary();

    /**
     * @return the user URL path
     */
    String getUserUrlPath();

    /**
     *
     * @return The name of the user
     */
    String getName();

    /**
     *
     * @return the user profile image
     */
    String getProfileImageUrl();

    /**
     * Adds the given user to the friends list
     * @param user user to add to friends list
     */
    void addUserToFriendsList(User user);
}
