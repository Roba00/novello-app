package com.yn_1.novello_app.old;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.yn_1.novello_app.book.Book;
import com.yn_1.novello_app.Const;
import com.yn_1.novello_app.*;
import com.yn_1.novello_app.volley_requests.VolleyCommand;
import com.yn_1.novello_app.volley_requests.JsonObjectRequester;

import org.json.JSONException;
import org.json.JSONObject;

public class RoundTripActivity extends AppCompatActivity {

    //Book search objects
    EditText stringInput;
    Button submitSearchButton;
    String searchedIsbn;
    TextView selectedBook;
    TextView showSearched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateLibrary();

        //Book search
        stringInput = (EditText) findViewById(R.id.searchBookTitle);
        stringInput.setHint("Input book isbn...");
        showSearched = (TextView) findViewById(R.id.showSearched);
        selectedBook = (TextView) findViewById(R.id.selectedBook);
        submitSearchButton = (Button) findViewById(R.id.submitSearch);
        submitSearchButton.setText("Submit");
        submitSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchedIsbn = stringInput.getText().toString();
                showSearched.setText("Searched for " + searchedIsbn + "!");
                searchLibrary(searchedIsbn);
            }
        });

    }

    /**
     * Populates the library with some books
     */
    private void populateLibrary() {

        JsonObjectRequester titleAddRequester = new JsonObjectRequester();
        JsonObjectCommand command = new JsonObjectCommand();
        //to postman
//        titleAddRequester.postRequest("library/0000000000001", null, command, null, null);
        //to backend
        JSONObject bookJson = new JSONObject();
        try {
            bookJson.put("title", "book title 1");
            bookJson.put("isbn", 0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        titleAddRequester.postRequest("addBooks", bookJson, command, null, null);
    }

    /**
     * Searches for a book in the library
     * @param isbn is the isbn of the book to search for
     * @return a book if one is found or null is not
     */
    private void searchLibrary(String isbn) {

        JsonObjectRequester titleRequester = new JsonObjectRequester();
        JsonObjectCommand command = new JsonObjectCommand();
        //to postman
//        titleRequester.getRequest("library/books/" + isbn, null, command, null, null);
        //to backend
        JSONObject bookJson = new JSONObject();
        try {
            bookJson.put("isbn", isbn);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        //post isbn search being ready
//        titleRequester.getRequest("book", bookJson, command, null, null);
        //pre isbn search being ready: id search
        titleRequester.getRequest( "book/10", bookJson, command, null, null);

    }

    private void searchResult(JsonObjectCommand command) {

        Book book = null;
        if (command.title != null) {
            book = new Book(command.title);
        }
        if (book != null) {
            String text = book.getTitle() + " found in library!";
            selectedBook.setText(text);
        }
        else {
            selectedBook.setText(Const.postmanMockUrl + "isbn: " + searchedIsbn + " not found in library.");
        }

    }

    private class JsonObjectCommand implements VolleyCommand<JSONObject> {

        String title = null;

        @Override
        public void execute(JSONObject data) {
            try {
                title = data.getString("title");
                searchResult(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(VolleyError error) {

        }

    }

}