package com.yn_1.novello_app;

import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.yn_1.novello_app.account.CreateAccountActivity;
import com.yn_1.novello_app.account.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.core.StringEndsWith.endsWith;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.os.Looper;


//Mock the RequestServerForService class
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest  //large execution time
public class CreateAccountTest {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule   //needed to launch the activity
    public ActivityTestRule<CreateAccountActivity> activityRule = new ActivityTestRule<>(CreateAccountActivity.class);

    /**
     * Tests account creation
     */
    @Test
    public void accountCreation() {

        //account creation failed
        Looper.prepare();
        ((CreateAccountActivity)activityRule.getActivity()).accountCreationResult(false);
        onView(withText("Account not created for an unknown reason!")).check(matches(isDisplayed()));

        //account creation succeeded
        ((CreateAccountActivity)activityRule.getActivity()).accountCreationResult(true);
        intended(hasComponent(LoginActivity.class.getName()));


    }

}