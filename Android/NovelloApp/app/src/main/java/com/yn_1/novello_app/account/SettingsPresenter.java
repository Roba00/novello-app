package com.yn_1.novello_app.account;

public class SettingsPresenter {

    SettingsView view;
    SettingsModel model;

    /**
     * Constructor
     * @param view: settings view
     */
    public SettingsPresenter(SettingsView view) {

        this.view = view;
        this.model = new SettingsModel(this);

    }

}
