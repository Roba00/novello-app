package com.yn_1.novello_app.book;

import android.widget.ImageView;

import com.yn_1.novello_app.Const;

/**
 * Book Presenter <br>
 * For handling UI logic.
 *
 * @author Roba Abbajabal
 */
public class BookPresenter implements BookContract.Presenter {

    private BookContract.Model model;
    private BookContract.View view;

    /**
     * Creates a new instance of the presenter for the Book view.
     * @param model The MVP model.
     * @param view The MVP view.
     */
    public BookPresenter(BookContract.Model model, BookContract.View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void beforeViewCreated() {
        model.fetchBook(view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onViewCreated() {
        // Display stuff with view call, getting book from model class
        view.displayComponents(model.getBook());
        view.populateReviewsTable(model.getReviews());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onDisplayBookCover(ImageView coverView) {
        coverView.setImageBitmap(model.getBookCover());
    }
}
