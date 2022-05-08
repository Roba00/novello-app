package com.yn_1.novello_app;

import android.view.View;
import android.widget.RadioButton;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of a custom radio button group,
 * which does not rely on the radio buttons
 * belonging to the same parent.
 *
 * @author Roba Abbajabal
 */
public class CustomRadioButtonGroup {
    private List<RadioButton> buttonCollection;

    /**
     * Constructor for creating an empty custom radio button group.
     */
    public CustomRadioButtonGroup() {
        buttonCollection = new ArrayList<>();
    }

    /**
     * Constructor for creating an custom radio button group.
     * @param buttons The radio buttons to add to the group.
     */
    public CustomRadioButtonGroup(List<RadioButton> buttons) {
        buttonCollection = buttons;
    }

    /**
     * Adds a button to the custom radio button group.
     * @param button The button to be added to the group.
     */
    public void addButtonToGroup(RadioButton button) {
        button.setChecked(false);
        buttonCollection.add(button);
    }

    /**
     * Gets the current collection of radio buttons.
     * @return List representing the current collection of radio buttons.
     */
    public List<RadioButton> getButtonCollection() {
        return buttonCollection;
    }

    /**
     * Handles logic for when a radio button is changed. <br>
     * Performs checks to make sure that only the selected RadioButton
     *  is checked on the page.
     * @param sender The radio button that was checked.
     */
    public void radioButtonCheckChanged(RadioButton sender) {
        if (sender.isChecked())
        {
            for (RadioButton button : buttonCollection)
            {
                if (button != sender)
                {
                    button.setChecked(false);
                }
            }
        }
    }

    /**
     * Listens for when a radio button is checked.
     */
    public View.OnClickListener listener = v -> radioButtonCheckChanged((RadioButton) v);
}
