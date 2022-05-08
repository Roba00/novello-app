package com.yn_1.novello_app.book;

import android.os.Parcel;
import android.os.Parcelable;
import android.widget.ImageButton;

import com.yn_1.novello_app.message.Message;

import java.io.Serializable;

/**
 * Class representation of a book.
 *
 * @author Roba Abbajabal
 * @author Maxim Popov
 */
public class Book implements Serializable, Parcelable {

    //book fields
    int bookID;
    String title;
    String author;
    String genre;
    int publicationYear;
    String isbn;
    double rating;
    double price;
    String description;
    private String readingURL;
    private String imageURL;
    private ImageButton linkedImageButton;

    // User-specific fields, for categories like currently reading, wishlist, etc.
    // Works because each user gets their own instance of book
    // Array because a book can be in multiple categories.
    private String userCategoryID;


    /**
     * Constructor for creating a book instance.
     * @param bookID Integer representing the book id.
     * @param title String representing the book title.
     * @param author String representing the book author.
     * @param genre String representing the book genre.
     * @param publicationYear Integer representing the book's publication year.
     * @param isbn String representing the book's isbn.
     * @param rating Double representing the book's average isbn.
     * @param price Double representing the book's price.
     * @param description String representing the book's description.
     * @param readingURL String representing the book's reading URL.
     * @param imageURL String representing the book's image URL.
     */
    public Book(int bookID, String title, String author, String genre, int publicationYear,
                String isbn, double rating, double price, String description, String readingURL, String imageURL) {
        this.bookID = bookID;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.rating = rating;
        this.readingURL = readingURL;
        this.price = price;
        this.description = description;
        this.imageURL = imageURL;
    }

    /**
     * Constructor for instantiating a copy of the book for the user library
     * @param otherBook The book to be copied
     * @param userCategoryID The new category of the book should be in.
     */
    public Book(Book otherBook, String userCategoryID) {
        this(otherBook.bookID, otherBook.title, otherBook.author, otherBook.genre,
                otherBook.publicationYear, otherBook.isbn, otherBook.rating, otherBook.price,
                otherBook.description, otherBook.readingURL, otherBook.imageURL);

        this.userCategoryID = userCategoryID;
    }

    /**
     * Old constructor for creating a book instance.
     * @deprecated Not to be used for new classes, replaced by {@link #Book(int, String, String, String, int, String, double, double, String, String, String)}}
     * @param title String representing the title of the book
     */
    @Deprecated
    public Book(String title) {
        this.title = title;
        this.author = "unknown author";
        this.publicationYear = -1;
        this.isbn = "unknown isbn";
        this.rating = -1;
    }

    public Book(Parcel in) {
        this.bookID = in.readInt();
        this.title = in.readString();
        this.author = in.readString();
        this.genre = in.readString();
        this.publicationYear = in.readInt();
        this.isbn = in.readString();
        this.rating = in.readDouble();
        this.readingURL = in.readString();
        this.price = in.readDouble();
        this.description = in.readString();
        this.imageURL = in.readString();
    }

    /**
     * Gets the book's title.
     * @return String representing the book's title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the book's author.
     * @return String representing the book's author.
     */
    public String getAuthor() {
        return author;
    }

    /**
     * Gets the book's genre.
     * @return String representing the book's genre.
     */
    public String getGenre() {return genre;}

    /**
     * Gets the book's average rating.
     * @return Double representing the book's average rating.
     */
    public double getRating() {
        return rating;
    }

    /**
     * Gets the book's isbn.
     * @return String representing the book's isbn.
     */
    public String getISBN() {
        return isbn;
    }

    /**
     * Gets the textual representation of a book.
     * @return String representation of the book.
     */
    @Override
    public String toString() {
        return title + " by " + author;
    }

    /**
     * Gets the book's ID.
     * @return Integer representation of the book ID.
     */
    public int getBookID() {
        return bookID;
    }

    /**
     * Gets a link to the book's reading page.
     * @return String representing the book's reading URL.
     */
    public String getReadingURL() {
        return readingURL;
    }

    /**
     * Gets the book's publication year.
     * @return Integer representing the book's publication year.
     */   
    public int getPublicationYear() {
        return publicationYear;
    }

    /**
     * Gets the book's price
     * @return Double representing the book's price.
     */
    public double getPrice() {
        return price;
    }

    /**
     * Gets the book's description.
     * @return String representing the book's description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets a link to the book's cover image.
     * @return String representing the book's cover image URL.
     */
    public String getImageURL() { return imageURL; }

    /**
     * Gets the book's image button.
     * @deprecated To be removed in future, because it is bad code design to hold
     *  any type of XML component in a book instance.
     * @return ImageButton representing a button of the book.
     */
    public ImageButton getImageButton() { return linkedImageButton; }

    /**
     * Sets the book's image button.
     * @deprecated To be removed in future, because it is bad code design to hold
     *  any type of XML component in a book instance.
     * @param button ImageButton to represent a button of the book.
     */
    public void setImageButton(ImageButton button) { linkedImageButton = button; }

    /**
     * Gets the book's user category ID.
     * @return A String representing the book's user category ID.
     */
    public String getUserCategoryId() { return userCategoryID; }

    /**
     * Sets the book's user category ID.
     * @param categoryName An integer representing the book's user category ID.
     */
    public void setUserCategoryID(String categoryName) { userCategoryID = categoryName; }

    public static final Creator<Message> CREATOR = new Creator<Message>() {
        @Override
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        @Override
        public Message[] newArray(int size) {
            return new Message[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(bookID);
        dest.writeString(title);
        dest.writeString(author);
        dest.writeString(genre);
        dest.writeInt(publicationYear);
        dest.writeString(isbn);
        dest.writeDouble(rating);
        dest.writeString(readingURL);
        dest.writeDouble(price);
        dest.writeString(description);
        dest.writeString(readingURL);
    }
}
