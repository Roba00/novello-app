package com.yn_1.novello_app.library;

import android.content.Context;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.book.Book;

import java.util.List;

/**
 * Controller for Library's MVP Design Pattern.
 */
public interface LibraryContract {
    /**
     * Library Model <br>
     * For storing data and communicating with database.
     */
    interface Model {
        /**
         * COLLECTION_COUNT: Number of collection {e.g.: Reading, Wishlist, etc.}
         */
        int COLLECTION_COUNT = 4;

        /**
         * BOOK_SIZE: The dimensions of a book
         */
        int[] BOOK_SIZE = {175*3, 280*3};

        /**
         * Fetches all books in the user library from the database.
         * @param user Representation of user to obtain library information from.
         * @param view The view of the library screen.
         * @param presenter The presenter of the library screen.
         */
        void fetchAllBooks(User user, LibraryContract.View view, LibraryContract.Presenter presenter);

        /**
         * Fetches the cover image of the book from the database, and assigns it to the book.
         * @param book Representation of book to be processed.
         * @param imageURL String representing the URL to fetch an image from
         * @param button ImageButton representing the button to assign the fetched image to.
         * @param presenter The presenter of the library screen.
         */
        void assignImageToBook(Book book, String imageURL, ImageButton button, LibraryContract.View presenter);

        /**
         * Returns the user book collection.
         * @return List representing the user's book collection
         */
        List<Book> getUserBookCollection();

        /**
         * Add image buttons to collection.
         * @param book Representation of book to be added to collection
         * @param button ImageButton representing the image button to be added.
         */
        void addBookButton(Book book, ImageButton button);

        /**
         * Gets the book from the book's image button representation.
         * @param button ImageButton representing the book to be obtained.
         */
        Book getBookButton(ImageButton button);
    }

    /**
     * Library View <br>
     * For displaying data via the UI. Is the fragment of the screen.
     */
    interface View {
        /**
         * Starts the initial presenter code.
         */
        void startPresenter();

        /**
         * Displays all the books on the library screen.
         * @param books Representations of all books to be displayed.
         */
        void displayAllBooks(List<Book> books);

        /**
         * Transitions to the book screen fragment for displaying a specific book.
         * @param book Representation of book to be shown in the new screen.
         */
        void displayBook(Book book);

        /**
         * Transitions to the book reading fragment for reading a book online.
         * @param book Representation of book to be read.
         */
        void readBook(Book book);

        /**
         * Transitions to the post review fragment for adding a review and rating to the book.
         * @param book Representation of book to be reviewed and rated.
         */
        void reviewBook(Book book);

        /**
         * Refreshes the screen.
         */
        void refreshScreen();
    }

    /**
     * Library Presenter <br>
     * For handling UI logic.
     */
    interface Presenter {

        /**
         * Handles initialization logic before the library fragment is displayed.
         * @param user Representation of user to obtain library information from.
         */
        void beforeViewCreated(User user);

        /**
         * Handles initialization logic when the library fragment is displayed.
         * @param user Representation of user to obtain library information from.
         * @param context Context of the library screen.
         */
        void onViewCreated(User user, Context context);

        /**
         * Handles logic when a book is tapped
         * @param book Representation of book that was clicked.
         */
        void onBookTapped(Book book);

        /**
         * Creates an image button for the book.
         * @param context
         */
        void createBookButtons(Context context);

        /**
         * Creates pop-menu to display options of different actions for the book.
         * @param menu The current menu.
         * @param v The view context.
         * @param menuInfo Information of the menu.
         */
        void createBookMenu(ContextMenu menu, android.view.View v, ContextMenu.ContextMenuInfo menuInfo);

        /**
         * Handles logic for book menu item selection
         * @param user Representation of user that holds the current library.
         * @param item The menu item that was clicked.
         */
        void onBookMenuItemSelected(User user, MenuItem item);
    }
}
