package com.yn_1.novello_app.library;

import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.account.User;
import com.yn_1.novello_app.book.Book;
import com.yn_1.novello_app.volley_requests.ImageRequester;
import com.yn_1.novello_app.volley_requests.JsonArrayRequester;
import com.yn_1.novello_app.volley_requests.VolleyCommand;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Library Model <br>
 * For storing data and communicating with database.
 *
 * @author  Roba Abbajabal
 */
public class LibraryModel implements LibraryContract.Model {
    private List<Book> bookCollection;
    private int bookCount;

    // Collection of book buttons
    private Map<ImageButton, Book> bookButtons;

    private int finishedCollectionCounter;
    /**
     * {@inheritDoc}
     */
    @Override
    public void fetchAllBooks(User user, LibraryContract.View view, LibraryContract.Presenter presenter) {
        bookButtons = null;
        bookCount = 0;
        finishedCollectionCounter = 0;
        bookCollection = new ArrayList<>();
        bookButtons = new HashMap<>();

        for (int categoryIndex = 1; categoryIndex < LibraryCategory.values().length; categoryIndex++) {

            // Index 2 is cart, which we should skip
            if (categoryIndex == 2)
               continue;

            final int finalCategoryIndex = categoryIndex;

            JsonArrayRequester req = new JsonArrayRequester();
            req.getRequest("bookData/" + user.getUserId() + "/" + categoryIndex,
                    null, new VolleyCommand<JSONArray>() {
                        @Override
                        public void execute(JSONArray data) {
                            for (int i = 0; i < data.length(); i++) {
                                try {
                                    JSONObject object = data.getJSONObject(i);
                                    int bookID = object.getJSONObject("id").getInt("bookId");
                                    JSONObject book = object.getJSONObject("book");
                                    String title = book.getString("title");
                                    String author = book.getString("author");
                                    String genre = book.getString("genre");
                                    int publicationYear = book.getInt("publicationYear");
                                    String isbn = book.getString("isbn");
                                    double rating = book.getInt("overallRating");
                                    double price = book.getDouble("msrp");
                                    String description = book.getString("description");
                                    String readingUrl = book.getString("readingUrl");
                                    String imageUrl = book.getString("imageUrl");
                                    Book newBook = new Book(bookID, title, author, genre, publicationYear, isbn, rating, price, description, readingUrl, imageUrl);
                                    newBook.setUserCategoryID(LibraryCategory.values()[finalCategoryIndex].getStringFormat());
                                    bookCollection.add(newBook);
                                    bookCount++;
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            finishedCollectionCounter++;
                            if (finishedCollectionCounter == COLLECTION_COUNT) {
                                Log.d("Model", "fetchAllBooks() finished");
                                presenter.createBookButtons(((Fragment) view).getContext());
                            }
                        }

                        @Override
                        public void onError(VolleyError error) {

                        }
                    }, null, null);
        }
    }

    private int finishedImagesCounter;
    /**
     * {@inheritDoc}
     */
    @Override
    public void assignImageToBook(Book book, String imageURL, ImageButton button, LibraryContract.View view) {
        ImageRequester req = new ImageRequester();
        req.getRequest(imageURL, null, new VolleyCommand<Bitmap>() {
            @Override
            public void execute(Bitmap image) {
                Bitmap newImage = Bitmap.createScaledBitmap(image, BOOK_SIZE[0], BOOK_SIZE[1], true);
                button.setImageBitmap(newImage);
                finishedImagesCounter++;
                if (finishedImagesCounter == bookCount) {
                    Log.d("Model", "assignImageToBook() finished");
                    view.startPresenter();
                }
            }

            @Override
            public void onError(VolleyError error) {
                Log.d("Image Load Error", imageURL);
            }
        }, null, null);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Book> getUserBookCollection() {
        return bookCollection;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addBookButton(Book book, ImageButton button) {
        if (bookButtons == null) {
            bookButtons = new HashMap<>();
        }
        bookButtons.put(button, book);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Book getBookButton(ImageButton button) {
        return bookButtons.get(button);
    }
}
