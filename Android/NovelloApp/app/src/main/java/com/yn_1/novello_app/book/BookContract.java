package com.yn_1.novello_app.book;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.util.Map;

/**
 * Contract for Book's MVP Design Pattern.
 * @author Roba Abbajabal
 */
public interface BookContract {
    /**
     * Book Model <br>
     * For storing data and communicating with database.
     */
    interface Model {

        /**
         *  Contains the fields for the book size.
         */
        int[] BOOK_SIZE = {175*2, 280*2};

        /**
         *
         * @param view
         */
        void fetchBook(BookContract.View view);
        void fetchImage(String imageUrl, BookContract.View view);

        /**
         * Gets the current book.
         * @return The book shown on the screen.
         */
        Book getBook();

        /**
         * Get's the current book cover.
         * @return A Bitmap image representing the book cover.
         */
        Bitmap getBookCover();

        /**
         * Fetches reviews of a book from the server.
         * @param view The screen's view representing the current fragment.
         */
        void fetchReviews(BookContract.View view);

        /**
         * Gets all reviews indexed by an integer.
         * @return A map representing the reviews.
         */
        Map<Integer, String[]> getReviews();
    }

    /**
     * Book View <br>
     * For displaying data via the UI. Is the fragment of the screen.
     */
    interface View {
        /**
         * Starts the view after the model is done fetching data from the server.
         */
        void startView();

        /**
         * Displays all components of the fragment, after receiving data from the server.
         * @param book The book to show data of.
         */
        void displayComponents(Book book);

        /**
         * Populates the reviews table with books received by the server.
         * @param reviews A map representing all reviews.
         */
        void populateReviewsTable(Map<Integer, String[]> reviews);
    }

    /**
     * Book Presenter <br>
     * For handling UI logic.
     */
    interface Presenter {
        /**
         * Handles initialization logic before the library fragment is displayed.
         */
        void beforeViewCreated();

        /**
         * Handles initialization logic when the library fragment is displayed.
         */
        void onViewCreated();

        /**
         * Handles logic of displaying a book cover.
         * @param coverView An ImageView component representing the cover of the book.
         */
        void onDisplayBookCover(ImageView coverView);
    }
}
