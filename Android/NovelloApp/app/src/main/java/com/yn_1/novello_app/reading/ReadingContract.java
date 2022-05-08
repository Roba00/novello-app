package com.yn_1.novello_app.reading;

import com.yn_1.novello_app.account.User;

/**
 * Contract for Reading's MVP Design Pattern.
 * @author Roba Abbajabal
 */
public interface ReadingContract {
    /**
     * Reading Model <br>
     * For storing data and communicating with database.
     */
    interface Model {
        /**
         * Fetches the current progress of the book from the server.
         * @param user Representation of user that is reading the book.
         * @param bookID Integer representing the book being read.
         * @param view The reading's view.
         */
        void fetchProgress(User user, int bookID, View view);

        /**
         * Saves the current progress of the book to the server.
         * @param user Representation of user that is reading the book.
         * @param bookID Integer representing the book being read.
         * @param progress The page progress of the book.
         */
        void saveProgress(User user, int bookID, int progress);

        /**
         * Gets the ID of the book being read.
         * @return Integer representing the book ID.
         */
        int getBookId();

        /**
         * Gets the link to the reading page.
         * @return String representing the reading URL.
         */
        String getUrl();
    }

    /**
     * Reading View <br>
     * For displaying data via the UI. Is the fragment of the screen.
     */
    interface View {
        /**
         * Gets the current page progress.
         * @return Integer representing the current page progress.
         */
        int getProgress();

        /**
         * Gets the percentage of the book read.
         * @return Integer representing the book progress percentage.
         */
        double getProgressPercentage();

        /**
         * Jumps the reading to a certain page progress.
         * @param progress Integer representing page progress to be jumped to.
         */
        void jumpToProgress(int progress);
    }

    /**
     * Reading Presenter <br>
     * For handling UI logic.
     */
    interface Presenter {
        /**
         * Handles logic for when the book's reading page is loaded.
         * @param user Representation of user that is reading the book.
         */
        void onPageLoad(User user);

        /**
         * Handles logic for when the user exits the book reading page.
         * @param user Representation of user that is reading the book.
         */
        void onEscape(User user);
    }
}
