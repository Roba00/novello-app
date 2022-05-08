package com.yn_1.novello_app.reading;

import com.yn_1.novello_app.account.User;

/**
 * Reading Presenter <br>
 * For handling UI logic.
 *
 * @author Roba Abbajabal
 */
public class ReadingPresenter implements ReadingContract.Presenter {

    // Model and View accessible from Presenter
    ReadingContract.Model model;
    ReadingContract.View view;

    /**
     * Constructor that creates a new instance of the reading presenter.
     * @param model The reading model.
     * @param view The reading view.
     */
    public ReadingPresenter(ReadingContract.Model model, ReadingContract.View view) {
        this.model = model;
        this.view = view;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onPageLoad(User user) {
        model.fetchProgress(user, model.getBookId(), view);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onEscape(User user) {
        model.saveProgress(user, model.getBookId(), view.getProgress());
    }
}
