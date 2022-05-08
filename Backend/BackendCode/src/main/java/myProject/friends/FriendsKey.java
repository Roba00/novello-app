package myProject.friends;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class FriendsKey implements Serializable {
    @Column(name = "Senderid")
    Integer senderId;

    @Column(name = "Receiverid")
    Integer receiverId;

    public Integer getSenderId() {
        return senderId;
    }

    public void setSenderId(Integer senderId) {
        this.senderId = senderId;
    }

    public Integer getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(Integer receiverId) {
        this.receiverId = receiverId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((senderId == null) ? 0 : senderId.hashCode());
        result = prime * result + ((receiverId == null) ? 0 : receiverId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        FriendsKey other = (FriendsKey) obj;
        if (senderId == null) {
            if (other.senderId != null)
                return false;
        } else if (!senderId.equals(other.senderId))
            return false;
        if (receiverId == null) {
            return other.receiverId == null;
        } else return receiverId.equals(other.receiverId);
    }
}
