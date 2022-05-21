import React from 'react';

class Book extends React.Component {

    /**
     * Constructor for creating a book instance.
     * @param bookID Integer representing the book id.
     * @param bookTitle String representing the book title.
     * @param author String representing the book author.
     * @param genre String representing the book genre.
     * @param publicationYear Integer representing the book's publication year.
     * @param isbn String representing the book's isbn.
     * @param rating Double representing the book's average isbn.
     * @param price Double representing the book's price.
     * @param bookDescription String representing the book's description.
     * @param readingUrl String representing the book's reading URL.
     * @param imageUrl String representing the book's image URL.
     */
    constructor(bookID, bookTitle, author, genre, publicationYear,
                isbn, rating, price, bookDescription, readingUrl, imageUrl) {

        // Passes over instance variables into the props
        super([bookID, bookTitle,  author, genre, publicationYear,
            isbn, rating, price, bookDescription, readingUrl, imageUrl]);


        // Initializes class variables
        this._bookID = bookID;
        this._bookTitle = bookTitle;
        this._author = author;
        this._genre = genre;
        this._publicationYear = publicationYear;
        this._isbn = isbn;
        this._rating = rating;
        this._price = price;
        this._bookDescription = bookDescription;
        this._readingUrl = readingUrl;
        this._imageUrl = imageUrl;
    }

    /**
     * Renders the appearance of a book as a React component
     * @returns {JSX.Element} The underlying HTML code to render.
     */
    render() {
        return (
            <img src={this.imageUrl} alt={this.bookTitle}>
            </img>
        )
    }

    // Setters and getters below

    get bookID() {
        return this._bookID;
    }

    set bookID(value) {
        this._bookID = value;
    }

    get bookTitle() {
        return this._bookTitle;
    }

    set bookTitle(value) {
        this._bookTitle = value;
    }

    get author() {
        return this._author;
    }

    set author(value) {
        this._author = value;
    }

    get genre() {
        return this._genre;
    }

    set genre(value) {
        this._genre = value;
    }

    get publicationYear() {
        return this._publicationYear;
    }

    set publicationYear(value) {
        this._publicationYear = value;
    }

    get isbn() {
        return this._isbn;
    }

    set isbn(value) {
        this._isbn = value;
    }

    get rating() {
        return this._rating;
    }

    set rating(value) {
        this._rating = value;
    }

    get price() {
        return this._price;
    }

    set price(value) {
        this._price = value;
    }

    get bookDescription() {
        return this._bookDescription;
    }

    set bookDescription(value) {
        this._bookDescription = value;
    }

    get readingUrl() {
        return this._readingUrl;
    }

    set readingUrl(value) {
        this._readingUrl = value;
    }

    get imageUrl() {
        return this._imageUrl;
    }

    set imageUrl(value) {
        this._imageUrl = value;
    }
}