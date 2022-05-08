package com.yn_1.novello_app.chat.privateChat;

import android.graphics.Bitmap;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.chat.Chat;
import com.yn_1.novello_app.chat.ChatContract;
import com.yn_1.novello_app.volley_requests.ImageRequester;
import com.yn_1.novello_app.volley_requests.VolleyCommand;

import java.util.List;
import java.util.Map;

public class PrivateChatModel implements PrivateChatContract.Model {

    User currentUser;
    List<Chat> chats;
    Map<Integer, Bitmap> userImages;

    public PrivateChatModel(User currentUser, List<Chat> chats) {
        this.currentUser = currentUser;
        this.chats = chats;
    }

    public List<Chat> getChats() {
        return chats;
    }

    @Override
    public Map<Integer, Bitmap> getUserProfileImages() {
        return userImages;
    }

    @Override
    public int getChatCount() {
        return chats.size();
    }

    @Override
    public User getUser() {
        return currentUser;
    }


    private int imagesIndex;
    @Override
    public void fetchProfileImagesOfChat(PrivateChatContract.VolleyListener listener) {
        imagesIndex = 0;
        listener.onAllProfileImagesFetched();
        /*for (Chat chat : chats) {
             for (User user : chat.getUsers()) {
                 String userProfileImageUrl = user.getProfileImageUrl();
                 ImageRequester req = new ImageRequester();
                 req.getRequest(userProfileImageUrl, null, new VolleyCommand<Bitmap>() {
                     @Override
                     public void execute(Bitmap data) {
                         Bitmap image = Bitmap.createScaledBitmap(data,
                                 profileImageSize[0], profileImageSize[1],
                                 true);
                         userImages.put(user.getUserId(), image);
                         imagesIndex++;
                         if (imagesIndex == chats.size()) {
                             listener.onAllProfileImagesFetched();
                         }
                     }

                     @Override
                     public void onError(VolleyError error) {

                     }
                 }, null, null);
             }
        }*/
     }
}
