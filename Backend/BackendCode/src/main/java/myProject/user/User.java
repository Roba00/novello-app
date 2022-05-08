package myProject.user;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import myProject.book.BookData;
import myProject.chat.ChatRoom;
import myProject.chat.Message;
import myProject.friends.Friends;

import javax.persistence.*;
import java.util.Set;

@Entity
@Api(value = "UserClass")
public
class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @ApiModelProperty(allowableValues = "Kevin")
    @Column
    public String username;

    @ApiModelProperty(allowableValues = "1")
    @Column
    Integer accountType;

    @ApiModelProperty(allowableValues = "letMeIn")
    @Column
    String password;

    @ApiModelProperty(allowableValues = "Where was my mother born?")
    @Column
    String securityQuestion;

    @ApiModelProperty(allowableValues = "Alaska")
    @Column
    String securityAnswer;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
    Set<myProject.book.BookData> BookData;

    @JsonIgnore
    @OneToMany(mappedBy = "user")
//    @JoinColumn(name = "message_id")
    Set<Message> messages;

    @JsonIgnore
    @ManyToMany()
    @JoinTable(
            name = "chatRooms",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chatRoom_id"))
    Set<ChatRoom> chatRooms;

    @OneToMany(mappedBy = "sender")
    Set<Friends> Friends;

    @JsonIgnore
    @OneToMany(mappedBy = "receiver")
    Set<Friends> Friends_receiver;

    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserInfo_id")
    private UserInfo userInfo;

    @JsonIgnore
    public Set<BookData> getBookData() {
        return BookData;
    }

    public void setBookData(Set<myProject.book.BookData> bookData) {
        BookData = bookData;
    }

    public Set<ChatRoom> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(Set<ChatRoom> chatRooms) {
        this.chatRooms = chatRooms;
    }

    @JsonIgnore
    public Set<Friends> getFriends() {
        return Friends;
    }

    public void setFriends(Set<myProject.friends.Friends> friends) {
        Friends = friends;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Set<Message> getMessages() {
        return messages;
    }

    public void setMessages(Set<Message> messages) {
        this.messages = messages;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }
}