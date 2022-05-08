package com.yn_1.novello_app.account;

import android.os.Parcel;
import android.os.Parcelable;

import com.yn_1.novello_app.book.Book;

import java.io.Serializable;
import java.util.List;

/**
 * Adult user account.
 */
public class AdultUser implements User, Parcelable, Serializable {

    String username;
    String password;
    UserType userType = UserType.ADULT;
    int id;
    String name;
    String userProfileImageUrl;

    /**
     * Constructor for creating a user
     * @param username username
     * @param password password
     * @param id user id
     */
    public AdultUser(String username, String password, int id) {
        this.username = username;
        this.password = password;
        this.id = id;
    }

    /**
     * Constructor for getting another user
     * @param id
     */
    public AdultUser(int id, String username, String userProfileImageUrl) {
        this.id = id;
        this.username = username;
        this.userProfileImageUrl = userProfileImageUrl;
    }

    /**
     * Constructor for writing AdultUser to a Parcel
     * @param in
     */
    public AdultUser(Parcel in) {
        id = in.readInt();
        name = in.readString();
        username = in.readString();
        password = in.readString();
        userProfileImageUrl = in.readString();
    }

    @Override
    public void setUsername(String username) {

        this.username = username;

    }

    @Override
    public void setPassword(String password) {

        this.password = password;

    }

    @Override
    public void setUserType(UserType type) {

        this.userType = type;

    }

    @Override
    public void setProfileImageUrl(String url) {
        userProfileImageUrl = url;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getUserId() {

        return id;

    }

    @Override
    public String getUsername() {

        return username;

    }

    @Override
    public String getPassword() {

        return password;

    }

    @Override
    public UserType getUserType() {

        return userType;

    }

    @Override
    public List<User> getFriends() {

        //store friends as sorted list
        return null;

    }

    @Override
    public List<Book> getLibrary() {

        //store library as sorted list
        return null;

    }

    @Override
    public String getUserUrlPath() {

        return null;

    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getProfileImageUrl() {
        return userProfileImageUrl;
    }

    @Override
    public void addUserToFriendsList(User user) {



    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(userProfileImageUrl);
    }

    public static final Creator<AdultUser> CREATOR = new Creator<AdultUser>() {
        @Override
        public AdultUser createFromParcel(Parcel in) {
            return new AdultUser(in);
        }

        @Override
        public AdultUser[] newArray(int size) {
            return new AdultUser[size];
        }
    };
}
