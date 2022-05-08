package myProject.chat;

import myProject.user.User;
import myProject.user.UserInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.*;

@Controller
@ServerEndpoint(value = "/chat/{roomId}/{userId}")
public class ChatSocket {

    public static Map<Integer, Map<Session, User>> messageMap = new Hashtable<>();
    private static MessageRepository msgRepo;
    private static UserInterface udb;
    private static MessageRepository mdb;
    private static ChatRoomRepository rdb;

    @Autowired
    public void setMessageRepository(MessageRepository repo) {
        msgRepo = repo;  // we are setting the static variable
    }

    @Autowired
    public void setUdb(UserInterface repo) {
        udb = repo;
    }

    @Autowired
    public void setMdb(MessageRepository repo) {
        mdb = repo;
    }

    @Autowired
    public void setRdb(ChatRoomRepository repo) {
        rdb = repo;
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("userId") Integer userId, @PathParam("roomId") int roomId) throws IOException {

        Map<Session, User> map = messageMap.getOrDefault(roomId, new HashMap<>());
        User user = udb.findById(userId).orElseThrow(NoSuchElementException::new);
        map.put(session, user);
        messageMap.put(roomId, map);
        sendMessageToPArticularUser(session, getChatHistory(roomId));
    }


    @OnMessage
    public void onMessage(Session session, String message, @PathParam("roomId") int roomId) {

        User user = messageMap.get(roomId).get(session);
        broadcast(user.getUsername() + ": " + message, roomId);

        msgRepo.save(new Message(user, message, rdb.findById(roomId).orElseThrow(NoSuchElementException::new)));
    }


    @OnClose
    public void onClose(Session session, @PathParam("roomId") int roomId) {
        messageMap.get(roomId).remove(session);
    }


    private void sendMessageToPArticularUser(Session session, String message) throws IOException {
        session.getBasicRemote().sendText(message);
    }


    private void broadcast(String message, int roomId) {
        messageMap.get(roomId).forEach((session, user) -> {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private String getChatHistory(int roomId) {
        List<Message> messages = msgRepo.findAll();

        StringBuilder sb = new StringBuilder();
        if (messages != null && messages.size() != 0) {
            for (Message message : messages) {
                if (message.getChatRoom().getId() == roomId)
                    sb.append(message.getUser() + ": " + message.getContent() + "\n");
            }
        }
        return sb.toString();
    }

}
