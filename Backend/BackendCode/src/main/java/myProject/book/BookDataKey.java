package myProject.book;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class BookDataKey implements Serializable {

    @Column(name = "user_Id")
    Integer userId;

    @Column(name = "Book_Id")
    Integer bookId;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((bookId == null) ? 0 : bookId.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj)
//            return true;
//        if (obj == null)
//            return false;
//        if (getClass() != obj.getClass())
//            return false;
//        BookDataKey other = (BookDataKey) obj;
//        if (bookId == null) {
//            if (other.bookId != null)
//                return false;
//        } else if (!bookId.equals(other.bookId))
//            return false;
//        if (userId == null) {
//            return other.userId == null;
//        } else return userId.equals(other.userId);
//    }
}

