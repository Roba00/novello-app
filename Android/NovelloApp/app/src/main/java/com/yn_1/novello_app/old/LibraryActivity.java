package com.yn_1.novello_app.old;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.book.Book;
import com.yn_1.novello_app.CustomRadioButtonGroup;
import com.yn_1.novello_app.*;
import com.yn_1.novello_app.volley_requests.VolleyCommand;
import com.yn_1.novello_app.volley_requests.JsonArrayRequester;
import com.yn_1.novello_app.volley_requests.JsonObjectRequester;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LibraryActivity extends AppCompatActivity {

    List<Book> bookCollection = new ArrayList<>();
    TableLayout table;
    CustomRadioButtonGroup radioButtonGroup = new CustomRadioButtonGroup();
    Button removeButton;
    Button changeRating;
    Button addButton;
    Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postman_experiments);
        table = findViewById(R.id.PostmanTable);
        retrieveBooks();

        removeButton = findViewById(R.id.removeBook);
        changeRating = findViewById(R.id.changeRating);
        addButton = findViewById(R.id.addBook);
        searchButton = findViewById(R.id.searchActivityButton);

        removeButton.setOnClickListener(removeButtonListener);
        addButton.setOnClickListener(switchActivity);
        searchButton.setOnClickListener(switchActivity);
        changeRating.setOnClickListener(changeRatingListener);
    }

    /**
     * Method for retrieving all books from user's library
     * @author Roba Abbajabal
     */
    public void retrieveBooks() {
        bookCollection = new ArrayList<>();
        table.removeViews(1, table.getChildCount()-1);

        JsonArrayRequester req = new JsonArrayRequester();
        req.getRequest("books", null, new VolleyCommand<JSONArray>()
        {
            @Override
            public void execute(JSONArray data) {
                for (int i = 0; i < data.length(); i++)
                {
                    try {
                        JSONObject book = data.getJSONObject(i);
                        int bookID = book.getInt("bookID");
                        String isbn  = book.getString("isbn");
                        String title = book.getString("title");
                        String author = book.getString("author");
                        String genre = book.getString("genre");
                        int rating = book.getInt("rating");
                        double price = book.getDouble("price");
                        putBookOnTable(bookID, title, author, genre, isbn, rating, price);
                        Log.d("Retrieve books", "Works");
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(VolleyError error) {

            }
        }, null, null);
    }

    /**
     * Dynamically creates a row to put a book on
     * @param title
     * @param author
     * @param rating
     * @author Roba Abbajabal
     */
    public void putBookOnTable(int bookID, String title, String author, String genre, String isbn, int rating, double price) {

        Book book = new Book(bookID, title, author, genre, -1, isbn, rating, -1, null, null, null);
        bookCollection.add(book);
        TableRow bookRow = new TableRow(this);
        bookRow.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT));
        bookRow.setBackgroundColor(Color.WHITE);

        RadioButton selectButton = new RadioButton(this);
        selectButton.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        selectButton.setPadding(0, 10,0,10);
        selectButton.setChecked(false);
        // Credit for ColorStateList: https://stackoverflow.com/a/29551017/10818632
        ColorStateList temp = new ColorStateList(new int[][]{new int[]{-android.R.attr.state_enabled},
                new int[]{android.R.attr.state_enabled}}, new int[]{Color.BLACK, Color.BLUE});
        selectButton.setButtonTintList(temp);
        selectButton.setText(null);
        bookRow.addView(selectButton);
        radioButtonGroup.addButtonToGroup(selectButton);
        selectButton.setOnClickListener(radioButtonGroup.listener);

        TextView titleText = new TextView(this);
        titleText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));
        titleText.setPadding(10, 10,10,10);
        titleText.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
        titleText.setText(book.getTitle());
        titleText.setTextColor(Color.BLACK);
        titleText.setTextSize(14);
        bookRow.addView(titleText);

        TextView authorText = new TextView(this);
        authorText.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 4));
        authorText.setPadding(10, 10,10,10);
        authorText.setGravity(TextView.TEXT_ALIGNMENT_CENTER);
        authorText.setText(book.getAuthor());
        authorText.setTextColor(Color.BLACK);
        authorText.setTextSize(14);
        bookRow.addView(authorText);

        RatingBar ratingBar = new RatingBar(this);
        ratingBar.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT));
        ratingBar.setPadding(0, 0,0,0);
        ratingBar.setRating(rating);
        ratingBar.setScaleX(.3f);
        ratingBar.setScaleY(.3f);
        ratingBar.setIsIndicator(true);
        bookRow.addView(ratingBar);

        table.addView(bookRow);
    }

    /**
     * Listener for changing a rating
     * @author Roba Abbajabal
     */
    public View.OnClickListener changeRatingListener = v -> {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Input new rating:");
        final EditText input = new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                RadioButton chosenButton = null;
                for (RadioButton button : radioButtonGroup.getButtonCollection()) {
                    if (button.isChecked()) {
                        chosenButton = button;
                    }
                }
                if (chosenButton == null) {
                    return;
                }

                // Gets the title, removes book at title (index 1 should be where the text view is located)
                String theTitle = ((TextView) ((ViewGroup) chosenButton.getParent()).getChildAt(1)).getText().toString();

                for (Book book : bookCollection) {
                    if (book.getTitle() == theTitle) {
                        int newRating = Integer.parseInt(input.getText().toString());
                        JSONObject bookToRate = new JSONObject();
                        try {
                            bookToRate.put("rating", newRating);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        JsonObjectRequester requester = new JsonObjectRequester();
                        Log.d("ID", "book/"+book.getBookID());
                        requester.putRequest("book/"+book.getBookID(), bookToRate, new VolleyCommand<JSONObject>() {
                            @Override
                            public void execute(JSONObject data) {
                                retrieveBooks();
                            }

                            @Override
                            public void onError(VolleyError error) {
                                Log.e(requester.TAG, "Error on delete: Book not found.");
                            }
                        }, null, null);
                        retrieveBooks();
                    }
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    };

    /**
     * Helper method for removing a book
     * @param book
     * @author Roba Abbajabal
     */
    private void deleteBookFromLibrary(Book book) {
        JsonObjectRequester requester = new JsonObjectRequester();
        requester.deleteRequest("book/"+book.getBookID(), null, new VolleyCommand<JSONObject>() {
            @Override
            public void execute(JSONObject data) { }

            @Override
            public void onError(VolleyError error) {
                Log.e(requester.TAG, "Error on delete: Book not found.");
            }
        }, null, null);
    }

    /**
     * Listener for removing a book
     * @author Roba Abbajabal
     */
    public View.OnClickListener removeButtonListener = v -> {
        RadioButton chosenButton = null;
        for (RadioButton button : radioButtonGroup.getButtonCollection()) {
            if (button.isChecked()) {
                chosenButton = button;
            }
        }
        if (chosenButton == null) {
            return;
        }

        // Gets the title, removes book at title (index 1 should be where the text view is located)
        String theTitle = ((TextView)((ViewGroup)chosenButton.getParent()).getChildAt(1)).getText().toString();

        for (Book book : bookCollection) {
            if (book.getTitle() == theTitle) {
                deleteBookFromLibrary(book);
                retrieveBooks();
            }
        }
        // table.removeView((View)chosenButton.getParent()); //Directly removes row
    };

    /**
     * Listener for switching to Maxim's activity
     * @author Roba Abbajabal
     */
    public View.OnClickListener switchActivity = v -> {
        Intent intent = new Intent(this, RoundTripActivity.class);
        startActivity(intent);
    };
}
