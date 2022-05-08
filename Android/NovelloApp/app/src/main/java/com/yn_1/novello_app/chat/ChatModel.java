package com.yn_1.novello_app.chat;

import android.util.Log;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.account.AdultUser;
import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.volley_requests.JsonArrayRequester;
import com.yn_1.novello_app.volley_requests.VolleyCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ChatModel implements ChatContract.Model {

    private User currentUser;

    private List<Chat> privateChats;
    private List<Chat> publicChats;

    public ChatModel(User user) {
        currentUser = user;
    }

    @Override
    public void fetchChats(ChatType chatType, ChatContract.VolleyListener listener) {
        privateChats = new ArrayList<Chat>();
        publicChats = new ArrayList<Chat>();

        JsonArrayRequester req = new JsonArrayRequester();
        String urlPath = "room/"+currentUser.getUserId();

        req.getRequest(urlPath, null, new VolleyCommand<JSONArray>() {
            @Override
            public void execute(JSONArray data) {
                // Cycle through JSON Array List
                for (int i = 0; i < data.length(); i++) {
                    try {
                        // Gets the chat JSON object.
                        JSONObject chatObject = data.getJSONObject(i);

                        // Create a chat from the chat index and users involved.
                        int chatId = chatObject.getInt("id");
                        String chatName = chatObject.getString("name");
                        int chatAccess = chatObject.getInt("type");
                        JSONArray userArray = chatObject.getJSONArray("users");
                        List<User> users = new ArrayList();
                        for (int j = 0; j < userArray.length(); j++) {
                            JSONObject userObject = userArray.getJSONObject(j);
                            int userId = userObject.getInt("id");
                            String name = userObject.getString("username");
                            // String userImageUrl = userObject.getString("image");
                            User user = new AdultUser(userId, name, null);
                            users.add(user);
                        }
                        Chat chat = new Chat(chatId, chatName, users);

                        // Add new chat element to list
                        switch (chatAccess) {
                            case 1:
                                privateChats.add(new Chat(chat));
                                break;
                            case 2:
                                publicChats.add(new Chat(chat));
                                break;
                        }

                        // If final element is finished, receive chats
                        if (i == data.length() - 1) {
                            listener.onChatsReceived();
                        }

                    } catch (JSONException e) {
                        Log.d("Chat", "Unable to parse JSON of " + chatType.toString() + " chat.");
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("Chat", "Unable to fetch " + chatType.toString() + " chat.");
            }
        }, null, null);
    }

    @Override
    public User getUser() {
        return currentUser;
    }

    @Override
    public List<Chat> getPrivateChats() {
        return privateChats;
    }

    @Override
    public List<Chat> getPublicChats() {
        return publicChats;
    }

    @Override
    public int getPrivateChatCount() {
        return privateChats.size();
    }

    @Override
    public int getPublicChatCount() {
        return  publicChats.size();
    }

    @Override
    public int getTotalChatCount() {
        return privateChats.size() + publicChats.size();
    }
}
