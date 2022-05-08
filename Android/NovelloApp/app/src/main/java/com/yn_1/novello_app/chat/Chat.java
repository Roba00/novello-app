package com.yn_1.novello_app.chat;

import android.os.Parcel;
import android.os.Parcelable;

import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.message.Message;

import java.util.List;

public class Chat implements Parcelable {
    private int chatId;
    private String chatName;
    private List<User> users;
    private List<Message> messages;

    public Chat(int chatId, String chatName, List<User> users) {
        this(chatId, chatName, users, null);
    }

    public Chat(int chatId, String chatName, List<User> users, List<Message> messages) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.users = users;
        this.messages = messages;
    }

    public Chat(Chat other) {
        this.chatId = other.chatId;
        this.chatName = other.chatName;
        this.users = other.users;
        this.messages = other.messages;
    }

    public Chat (Parcel in) {
        this.chatId = in.readInt();
        this.chatName = in.readString();
        in.readList(users, User.class.getClassLoader());
        in.readList(messages, Message.class.getClassLoader());
    }

    public static final Creator<Chat> CREATOR = new Creator<Chat>() {
        @Override
        public Chat createFromParcel(Parcel in) {
            return new Chat(in);
        }

        @Override
        public Chat[] newArray(int size) {
            return new Chat[size];
        }
    };

    public int getChatId() {
        return chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(chatId);
        dest.writeString(chatName);
        dest.writeList(users);
        dest.writeList(messages);
    }
}
