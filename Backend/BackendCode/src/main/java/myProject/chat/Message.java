package myProject.chat;

import lombok.Data;
import myProject.user.User;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "messages")
@Data
public class Message {
    @ManyToOne
    @JoinColumn(name = "message_Id")
    User user;

    @ManyToOne
    @JoinColumn(name = "chatRoom_Id")
    ChatRoom chatRoom;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Lob
    private String content;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "sent")
    private Date sent = new Date();

    public Message(User user, String content, ChatRoom chatRoom) {
        this.user = user;
        this.content = content;
        this.chatRoom = chatRoom;
    }

    public Message() {
    }

    public ChatRoom getChatRoom() {
        return chatRoom;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }
}
