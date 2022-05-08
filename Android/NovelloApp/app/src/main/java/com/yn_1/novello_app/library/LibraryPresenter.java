package com.yn_1.novello_app.library;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.book.Book;

/**
 * Library Presenter <br>
 * For handling UI logic.
 *
 * @author Roba Abbajabal
 */
public class LibraryPresenter implements LibraryContract.Presenter {

    // Model and View accessible from Presenter
    private LibraryContract.Model model;
    private LibraryContract.View view;

    private Book currentSelectedBook;
    private LibraryCategory currentSelectedCategory;

    /**
     * Constructor that creates a new instance of the library presenter.
     * @param model The library model.
     * @param view The library view.
     */
    public LibraryPresenter(LibraryContract.Model model, LibraryContract.View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeViewCreated(User user) {
        Log.d("Presenter", "beforeViewCreated() called");
        model.fetchAllBooks(user, view, this);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(User user, Context context) {
        view.displayAllBooks(model.getUserBookCollection());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBookTapped(Book book) {
        view.displayBook(book);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createBookButtons(Context context) {
        Log.d("Library", "Book collection size " + model.getUserBookCollection().size());
        for (Book book : model.getUserBookCollection()) {
            ImageButton button = new ImageButton(context);

            LayoutParams params = new LayoutParams(model.BOOK_SIZE[0],
                    model.BOOK_SIZE[1]);
            params.setMargins(15, 0, 15, 0);
            button.setLayoutParams(params);
            button.setBackgroundColor(Color.YELLOW);

            model.assignImageToBook(book, book.getImageURL(), button, view);

            // Click listener
            button.setOnClickListener(v -> {
                onBookTapped(book);
            });
            // Long click listener
            ((Fragment)view).registerForContextMenu(button);

            model.addBookButton(book, button);
            book.setImageButton(button);
            Log.d("button", "Book button created for " + book.getTitle());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void createBookMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        currentSelectedBook = model.getBookButton((ImageButton)v);
        HorizontalScrollView parent = (HorizontalScrollView) ((ImageButton)v).getParent().getParent();
        menu.setHeaderTitle("Book Options");
        menu.add(0, v.getId(), 0, "Go To Book");

        final String start = "app:id/";
        final String id = parent.toString().substring(parent.toString().lastIndexOf(" ")+1);
        Log.d("Menu", id);
        switch (id)
        {
            case start+"wishlist}":
                currentSelectedCategory = LibraryCategory.WISHLIST;
                menu.add(0, v.getId(), 1, "Remove");
                menu.add(0, v.getId(), 2, "Add to Cart");
                break;
            case start+"backlog}":
                currentSelectedCategory = LibraryCategory.BACKLOG;
                menu.add(0, v.getId(), 1, "Move to Currently Reading");
                break;
            case start+"currentlyReading}":
                currentSelectedCategory = LibraryCategory.CURRENTLY_READING;
                menu.add(0, v.getId(), 1, "Read Book");
                menu.add(0, v.getId(), 2, "Move to Backlog");
                menu.add(0, v.getId(), 3, "Move to Read");
                break;
            case start+"read}":
                currentSelectedCategory = LibraryCategory.READ;
                menu.add(0, v.getId(), 1, "Rate/Review");
                menu.add(0, v.getId(), 2, "Move to Backlog");
                menu.add(0, v.getId(), 3, "Move to Currently Reading");
                break;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onBookMenuItemSelected(User user, MenuItem item) {
        switch (item.getTitle().toString())
        {
            case "Go To Book":
                view.displayBook(currentSelectedBook);
                break;
            case "Read Book":
                view.readBook(currentSelectedBook);
                break;
            case "Rate/Review":
                view.reviewBook(currentSelectedBook);
                break;
            case "Remove":
                currentSelectedCategory.performTransaction(LibraryCategory.NONE,
                        user.getUserId(), currentSelectedBook.getBookID());
                view.refreshScreen();
                break;
            case "Add to Wishlist":
                currentSelectedCategory.performTransaction(LibraryCategory.WISHLIST,
                        user.getUserId(), currentSelectedBook.getBookID());
                view.refreshScreen();
                break;
            case "Add to Cart":
                currentSelectedCategory.performTransaction(LibraryCategory.CART,
                        user.getUserId(), currentSelectedBook.getBookID());
                view.refreshScreen();
                break;
            case "Move to Backlog":
                currentSelectedCategory.performTransaction(LibraryCategory.BACKLOG,
                        user.getUserId(), currentSelectedBook.getBookID());
                view.refreshScreen();
                break;
            case "Move to Currently Reading":
                currentSelectedCategory.performTransaction(LibraryCategory.CURRENTLY_READING,
                        user.getUserId(), currentSelectedBook.getBookID());
                view.refreshScreen();
                break;
            case "Move to Read":
                currentSelectedCategory.performTransaction(LibraryCategory.READ,
                        user.getUserId(), currentSelectedBook.getBookID());
                view.refreshScreen();
                break;
        }
    }
}
