package com.yn_1.novello_app.review;

import android.util.Log;

import com.yn_1.novello_app.account.User;

/**
 * (Posting) Review Presenter <br>
 * For handling UI logic.
 *
 * @author Roba Abbajabal
 */
public class ReviewPresenter implements ReviewContract.Presenter {

    ReviewContract.View view;
    ReviewContract.Model model;

    /**
     * Constructor that creates a new instance of the library presenter.
     * @param model The review model.
     * @param view The review view.
     */
    public ReviewPresenter (ReviewContract.View view, ReviewContract.Model model) {
        this.view = view;
        this.model = model;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPostButtonPressed(User user, double rating, String review) {
        model.postReview(this, user, rating, review);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onReviewSucceeded() {
        Log.d("Review", "Listener succeeded");
        view.navigateToBookScreen(model.getBookID());
    }
}
