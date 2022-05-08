package com.yn_1.novello_app.message;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

public class Message implements Parcelable {
    private int messageId;
    private Bitmap userProfileImage;
    private int userId;
    private String userName;
    private Date messageDate;
    private String message;

    public Message(int messageId, Bitmap userProfileImage, int userId, String userName, Date messageDate, String message) {
        this.messageId = messageId;
        this.userProfileImage = userProfileImage;
        this.userId = userId;
        this.userName = userName;
        this.messageDate = messageDate;
        this.message = message;
    }

    public Message(Parcel in) {
        this.messageId = in.readInt();
        this.userProfileImage = in.readParcelable(Bitmap.class.getClassLoader());
        this.userId = in.readInt();
        this.userName = in.readString();
        this.messageDate = (Date) in.readSerializable();
        this.message = in.readString();
    }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(messageId);
        dest.writeParcelable(userProfileImage, flags);
        dest.writeInt(userId);
        dest.writeString(userName);
        dest.writeSerializable(messageDate);
        dest.writeString(message);
    }

    public int getMessageId() {
        return messageId;
    }

    public Bitmap getUserProfileImage() {
        return userProfileImage;
    }

    public int getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public Date getMessageDate() {
        return messageDate;
    }

    public String getMessage() {
        return message;
    }
}
