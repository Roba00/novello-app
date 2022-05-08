package myProject.book;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.util.Set;

@Entity
@Api(value = "BookClass")
class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer bookID;

    @ApiModelProperty(allowableValues = "100030499")
    @Column
    String isbn;

    @ApiModelProperty(allowableValues = "Star Wars")
    @Column
    String title;

    @ApiModelProperty(allowableValues = "Teddy")
    @Column
    String author;

    @ApiModelProperty(allowableValues = "2008")
    @Column
    Integer publicationYear;

    @ApiModelProperty(allowableValues = "4.56")
    @Column
    Double overallRating;

    @ApiModelProperty(allowableValues = "9.99")
    @Column
    Double msrp;

    @ApiModelProperty(allowableValues = "Drama")
    @Column
    String genre;

    @ApiModelProperty(allowableValues = "Book about the wars of stars")
    @Column(columnDefinition = "TEXT")
    String description;

    @ApiModelProperty(allowableValues = "www.books.com")
    @Column
    String readingUrl;

    @ApiModelProperty(allowableValues = "www.image.com")
    @Column
    String imageUrl;

    @JsonIgnore
    @OneToMany(mappedBy = "book")
    Set<BookData> bookData;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getReadingUrl() {
        return readingUrl;
    }

    public void setReadingUrl(String readingUrl) {
        this.readingUrl = readingUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Integer publicationYear) {
        this.publicationYear = publicationYear;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Double getMsrp() {
        return msrp;
    }

    public void setMsrp(Double msrp) {
        this.msrp = msrp;
    }

    public Integer getBookID() {
        return bookID;
    }

    public void setBookID(Integer bookID) {
        this.bookID = bookID;
    }

    public double getOverallRating() {
        return overallRating;
    }

    public void setOverallRating(Double overallRating) {
        this.overallRating = overallRating;
    }

    @JsonIgnore
    public Set<BookData> getBookData() {
        return bookData;
    }
}
