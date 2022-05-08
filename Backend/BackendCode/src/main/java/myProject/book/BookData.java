package myProject.book;

import io.swagger.annotations.ApiModelProperty;
import myProject.user.User;

import javax.persistence.*;

@Entity
public class BookData {

    @EmbeddedId
    BookDataKey id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "User_id")
    User user;

    @ManyToOne
    @MapsId("bookId")
    @JoinColumn(name = "Book_id")
    Book book;

    @ApiModelProperty(allowableValues = "9.5")
    double rating;

    @ApiModelProperty(allowableValues = "This book was really good!")
    String review;

    @ApiModelProperty(allowableValues = "3")
    int category;

    @ApiModelProperty(allowableValues = "243")
    int page;

    public BookDataKey getId() {
        return id;
    }

    public void setId(BookDataKey id) {
        this.id = id;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    public Book getBook() {
        return book;
    }

    public void setBook(Book id) {
        this.book = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) return true;
//        if (obj == null) return false;
//        if (getClass() != obj.getClass()) return false;
//        BookData other = (BookData) obj;
//        if (id == null) {
//            return other.id == null;
//        } else return id.equals(other.id);
//    }
}
