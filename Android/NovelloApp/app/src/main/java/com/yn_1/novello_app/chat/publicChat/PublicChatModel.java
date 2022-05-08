package com.yn_1.novello_app.chat.publicChat;

import android.graphics.Bitmap;

import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.chat.Chat;

import java.util.List;
import java.util.Map;

public class PublicChatModel implements PublicChatContract.Model {

    User currentUser;
    List<Chat> chats;
    Map<Integer, Bitmap> userImages;

    public PublicChatModel(User currentUser, List<Chat> chats) {
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
    public void fetchProfileImagesOfChat(PublicChatContract.VolleyListener listener) {
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
