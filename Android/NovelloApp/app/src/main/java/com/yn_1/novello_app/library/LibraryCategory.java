package com.yn_1.novello_app.library;

import android.util.Log;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.volley_requests.JsonObjectRequester;
import com.yn_1.novello_app.volley_requests.VolleyCommand;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Enumerated type representation of the five types of library categories. <br> <br>
 *
 * Five Library Categories for Books: <br>
 * 0. None: Existing book that is not in a user category. <br>
 * 1. Wishlist: The category that contains books that the user wishes to purchase. <br>
 * 2. Cart: The category that contains the books the user is about to purchase. <br>
 * 3. Backlog: The category that contains books the user hasn't read, or will read again. <br>
 * 4. Currently Reading: The category that contains books that the user is currently reading. <br>
 * 5. Read The category containing books that the user has finished,
 *  and do not intend to read again in near time. <br>
 *
 * This class is also holds information on which categories books in libraries can be moved,
 *  as well as operations for moving books between categories in the server.
 *
 * @author Roba Abbajabal
 */
public enum LibraryCategory {
    NONE (0, null, new boolean[]{false, true, true, false, false, false}),
    WISHLIST (1, "wishlist", new boolean[]{true, false, true, false, false, false}),
    CART (2, "cart", new boolean[]{true, true, false, true, false, false}),
    BACKLOG (3, "backlog", new boolean[]{false, false, false, false, true, false}),
    CURRENTLY_READING (4, "currentlyReading", new boolean[]{false, false, false, true, false, true}),
    READ (5, "read", new boolean[]{false, false, false, true, true, false});

    private int categoryIndex;
    private final String stringFormat;
    private final boolean[] possibleTransactions;

    /**
     * Constructor of a library category
     * @param categoryIndex Integer representing the category index.
     * @param stringFormat String representation of the category.
     * @param possibleTransactions Boolean array representation of which categories the book in
     *                             the current category can be moved to.
     */
    LibraryCategory(int categoryIndex, String stringFormat, boolean[] possibleTransactions) {
        this.categoryIndex = categoryIndex;
        this.stringFormat = stringFormat;
        this.possibleTransactions = possibleTransactions;
    }

    /**
     * Gets the category index.
     * @return Integer representing the category index.
     */
    public int getCategoryIndex() {
        return categoryIndex;
    }

    /**
     * Gets the string format of the category.
     * @return String representation of the category.
     */
    public String getStringFormat() {
        return stringFormat;
    }

    /**
     * Gets the possible category transactions.
     * @return Boolean array representation of which categories the book in
     * the current category can be moved to.
     */
    public boolean[] getPossibleTransactions() {
        return possibleTransactions;
    }

    /**
     * Moves book between user categories. Gets the book and calls the helper method.
     * @param newCategory The new category to be moved to.
     * @param userID Integer representing the user that holds the library.
     * @param bookID Integer representing the book to be moved.
     */
    public void performTransaction(LibraryCategory newCategory, int userID, int bookID) {

        // Check if current category can move to new category
        if (possibleTransactions[newCategory.getCategoryIndex()]) {
            // Get the book.
            JsonObjectRequester bookReq = new JsonObjectRequester();
            bookReq.getRequest("book/" + bookID, null, new VolleyCommand<JSONObject>() {
                @Override
                public void execute(JSONObject book) {
                    // Call a helper method to move the book to a new category.
                    putInCategory(newCategory, userID, bookID);
                }

                @Override
                public void onError(VolleyError error) {
                    Log.d("Transaction", "Could not obtain Book at book ID " + bookID);
                }
            }, null, null);
        }
        else
        {
            Log.d("Transaction", "Invalid category transaction.");
        }
    }

    /**
     * Helper method for putting book in a new category (or removing books from the library.
     * @param newCategory The new category to be moved to.
     *      * @param userID Integer representing the user that holds the library.
     *      * @param bookID Integer representing the book to be moved.
     */
    private void putInCategory(LibraryCategory newCategory, int userID, int bookID) {
        JsonObjectRequester categoryReq = new JsonObjectRequester();
        String pathUrl = "bookData";
        JSONObject object = new JSONObject();

        // If NONE: DELETE book
        if (newCategory == NONE)
        {
            try {
                object.put("bookId", bookID);
                object.put("userId", userID);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            categoryReq.deleteRequest(pathUrl, object, new VolleyCommand<JSONObject>() {
                @Override
                public void execute(JSONObject data) {
                    Log.d("Transaction", "Transaction successfully completed.");
                }

                @Override
                public void onError(VolleyError error) {
                    Log.d("Transaction", "Unable to PUT book in user category.");
                }
            }, null, null);
        }
        // Else: PUT book in new category
        else {
            // userID + "/library/" + newCategory.getCategoryIndex();
            try {
                object.put("bookId", bookID);
                object.put("userId", userID);
                object.put("category", newCategory.categoryIndex);
            } catch (JSONException e) {
                e.printStackTrace();
                Log.d("Transaction", "JSON object error.");
            }

            categoryReq.putRequest(pathUrl, object, new VolleyCommand<JSONObject>() {
                @Override
                public void execute(JSONObject data) {
                    Log.d("Transaction", "Transaction successfully completed.");
                }

                @Override
                public void onError(VolleyError error) {
                    Log.d("Transaction", "Unable to PUT book in user category.");
                }
            }, null, null);
        }
    }
}
