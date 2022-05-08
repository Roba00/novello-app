package com.yn_1.novello_app.review;

import com.yn_1.novello_app.account.User;

/**
 * Contract for  (Posting) Reviews' MVP Design Pattern.
 * @author Roba Abbajabal
 */
public interface ReviewContract {

    /**
     * (Posting) Review Model <br>
     * For storing data and communicating with database.
     */
    interface Model {
        /**
         * Posts a review to the server.
         * @param volleyListener Presenter's listener to be run on succeed.
         * @param user Representation of user that posts the review.
         * @param rating Double representing the rating.
         * @param review String representing the review.
         */
        void postReview(ReviewContract.VolleyListener volleyListener, User user, double rating, String review);

        /**
         * Gets the book ID
         * @return Integer representing the book ID.
         */
        int getBookID();
    }

    /**
     * (Posting) Review View <br>
     * For displaying data via the UI. Is the fragment of the screen.
     */
    interface View {
        /**
         * Navigates to the book's screen after review is posted.
         * @param bookID Integer representing the book to navigate to.
         */
        void navigateToBookScreen(int bookID);
    }

    /**
     * (Posting) Review Presenter <br>
     * For handling UI logic.
     */
    interface Presenter extends VolleyListener {
        /**
         * Handles logic for when the submit review button is pressed.
         * @param user Representation of user submitting the review.
         * @param rating Double representing the user's rating.
         * @param review String representing the user's review.
         */
        void onPostButtonPressed(User user, double rating, String review);
    }

    /**
     * (Posting) Review Volley Listener <br>
     * For listening and performing actions when a volley command from the Model succeeds.
     */
    interface VolleyListener {
        /**
         * Handles logic for when a review submits successfully.
         */
        void onReviewSucceeded();
    }
}
