package com.yn_1.novello_app.review;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import com.yn_1.novello_app.NavBarActivity;
import com.yn_1.novello_app.*;

/**
 * (Posting) Review View <br>
 * For displaying data via the UI. Is the fragment of the screen.
 *
 * @author Roba Abbajabal
 */
public class ReviewFragment extends Fragment implements ReviewContract.View {

    ReviewContract.Presenter presenter;

    // Declare components
    RatingBar ratingBar;
    TextView reviewText;
    Button postReviewButton;

    /**
     * Creates a new fragment instance, using specific arguments to be added to the bundle.
     *
     * @return A new instance of fragment ReviewFragment.
     */
    public static ReviewFragment newInstance() {
        ReviewFragment fragment = new ReviewFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Get the book ID from the bundle
        int bookID = ReviewFragmentArgs.fromBundle(getArguments()).getBookID();

        // Instantiate the presenter
        presenter = new ReviewPresenter(this, new ReviewModel(bookID));

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_review, container, false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Find components
        ratingBar = view.findViewById(R.id.postReviewRatingBar);
        reviewText = view.findViewById(R.id.postReviewText);
        postReviewButton = view.findViewById(R.id.postReviewButton);

        // Set the on click listener to a method in the presenter
        postReviewButton.setOnClickListener(v ->
                presenter.onPostButtonPressed(((NavBarActivity)getActivity()).getUser(),
                ratingBar.getRating(), reviewText.getText().toString())
        );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void navigateToBookScreen(int bookID) {
        Handler t = new Handler();
        t.postDelayed(new Runnable() {
            @Override
            public void run() {
                ReviewFragmentDirections.ActionReviewFragmentToBookFragment action =
                    ReviewFragmentDirections.actionReviewFragmentToBookFragment();
                action.setBookID(bookID);
                ((NavBarActivity)getActivity()).getController().navigate(action);
            }
        }, 500);
    }
}