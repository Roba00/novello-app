package com.yn_1.novello_app.library;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.*;
import com.yn_1.novello_app.book.Book;

import java.util.ArrayList;
import java.util.List;

/**
 * Library View <br>
 * For displaying data via the UI. Is the fragment of the screen.
 *
 * @author Roba Abbajabal
 */
public class LibraryFragment extends Fragment implements LibraryContract.View {

    // Presenter accessible from View
    private LibraryContract.Presenter presenter;

    // Fragment components
    List<HorizontalScrollView> categories;
    HorizontalScrollView currentlyReadingView;
    HorizontalScrollView wishlistView;
    HorizontalScrollView readView;
    HorizontalScrollView backlog;

    /**
     * Creates a new fragment instance, using specific arguments to be added to the bundle.
     *
     * @return A new instance of fragment LibraryFragment.
     */
    public static LibraryFragment newInstance() {
        LibraryFragment fragment = new LibraryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            // Set arguments
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Create a new library presenter instance, that holds a library model instance
        presenter = new LibraryPresenter(new LibraryModel(), this);

        // Receives data
        presenter.beforeViewCreated(((NavBarActivity)getActivity()).getUser());

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_library, container, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Get resources
        currentlyReadingView = view.findViewById(R.id.currentlyReading);
        wishlistView = view.findViewById(R.id.wishlist);
        readView = view.findViewById(R.id.read);
        backlog = view.findViewById(R.id.backlog);

        categories = new ArrayList<>();
        categories.add(currentlyReadingView);
        categories.add(wishlistView);
        categories.add(readView);
        categories.add(backlog);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void startPresenter() {
        // Start the presenter
        presenter.onViewCreated(((NavBarActivity)getActivity()).getUser(), getContext());
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public void displayAllBooks(List<Book> books) {
        Log.d("Library", "displayAllBooks() reached");
        for (Book book : books) {
            for (HorizontalScrollView realCategory : categories) {
                Log.d("Library", getView().getResources().getResourceName(realCategory.getId()));
                if (getView().getResources().getResourceName(realCategory.getId()).equals("com.yn_1.novello_app:id/" + book.getUserCategoryId()))
                {
                    ((LinearLayout)realCategory.getChildAt(0)).addView(book.getImageButton());
                    Log.d("Library", book.getTitle() + " added");
                }
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void displayBook(Book book) {
        LibraryFragmentDirections.ActionLibraryFragmentToBookFragment action =
                LibraryFragmentDirections.actionLibraryFragmentToBookFragment();
        action.setBookID(book.getBookID());
        ((NavBarActivity)getActivity()).getController().navigate(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void readBook(Book book) {
        LibraryFragmentDirections.ActionLibraryFragmentToReadingFragment action =
                LibraryFragmentDirections.actionLibraryFragmentToReadingFragment();
        action.setBookID(book.getBookID());
        action.setReadingLink(book.getReadingURL());
        action.setBookTitle(book.getTitle());
        ((NavBarActivity)getActivity()).getController().navigate(action);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void reviewBook(Book book) {
        LibraryFragmentDirections.ActionLibraryFragmentToReviewFragment action =
                LibraryFragmentDirections.actionLibraryFragmentToReviewFragment();
        action.setBookID(book.getBookID());
        ((NavBarActivity)getActivity()).getController().navigate(action);
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public Context getContext() {
        return super.getContext();
    }

    /**
     * {@inheritDoc}
     */
    @Nullable
    @Override
    public View getView() {
        return super.getView();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        presenter.createBookMenu(menu, v, menuInfo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        presenter.onBookMenuItemSelected(((NavBarActivity)getActivity()).getUser(), item);
        return true;
    }

    @Override
    public void refreshScreen() {
        Handler t = new Handler();

        t.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((NavBarActivity)getActivity()).getController().popBackStack();
                ((NavBarActivity)getActivity()).getController().navigate(R.id.libraryFragment);
            }
        }, 500);
    }
}