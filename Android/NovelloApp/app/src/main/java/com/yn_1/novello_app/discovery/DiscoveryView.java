package com.yn_1.novello_app.discovery;

import android.os.Bundle;
import android.os.Handler;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.*;
import com.yn_1.novello_app.book.Book;
import com.yn_1.novello_app.library.LibraryCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * View for the discovery screen
 */
public class DiscoveryView extends Fragment {

    DiscoveryPresenter presenter;

    // TableLayout tableLayout;
    RecyclerView recyclerView;

    ArrayList<Pair<Book, Double>> sortedRecommendations = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        //todo: get to cart from nav bar
        //todo: leave cart through nav bar
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        int userID = ((NavBarActivity)getActivity()).getUser().getUserId();
        presenter = new DiscoveryPresenter(this, userID);

        return inflater.inflate(R.layout.fragment_discovery_view, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // tableLayout = view.findViewById(R.id.discoveryTableLayout);
        recyclerView = view.findViewById(R.id.discoveryRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    /**
     * Displays recommended books
     * @param sortedRecommendations is a sorted ArrayList of books by recommendation rating
     */
    public void showRecommendedBooks(ArrayList<Pair<Book, Double>> sortedRecommendations) {
        int count = 1;

        ArrayList<Book> books = new ArrayList<>();
        for (Pair<Book, Double> pair : sortedRecommendations) {
            // Maxim's old code, before Roba's design improvements
            /*TableRow tableRow = new TableRow(getContext());
            TextView textView = new TextView(getContext());
            Book book = pair.first;
            String text = count + ".\n" + book.getTitle() + " by " + book.getAuthor()
                    + "\nGenre: " + book.getGenre() + "\nRating: " + book.getRating();
            textView.setText(text);
            tableRow.addView(textView);
            TableRow.LayoutParams tableRowParams = new TableRow.LayoutParams();
            tableRowParams.setMargins(5, 5, 5, 5);
            tableRow.setLayoutParams(tableRowParams);
            tableLayout.addView(tableRow);*/
            books.add(pair.first);
            count++;
        }

        recyclerView.setAdapter(new DiscoveryRecyclerViewAdapter(books,
            // Code for viewing the book's screen
            bookId -> {
                ((NavBarActivity) getActivity()).
                        getController().navigate
                        (DiscoveryViewDirections.actionDiscoverViewToBookFragment(bookId));
            },
            // Code for moving to wishlist
            bookId -> {
                LibraryCategory currentCategory = LibraryCategory.NONE;
                currentCategory.performTransaction(LibraryCategory.WISHLIST,
                        ((NavBarActivity)getActivity()).getUser().getUserId(),
                        bookId);
                refreshScreen();
            }));
    }

    private void refreshScreen() {
        Handler t = new Handler();
        t.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((NavBarActivity)getActivity()).getController().popBackStack();
                ((NavBarActivity)getActivity()).getController().navigate(R.id.discoverView);
            }
        }, 500);
    }
}
