package com.yn_1.novello_app.account;

import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner;
import androidx.test.rule.ActivityTestRule;

import com.yn_1.novello_app.R;
import com.yn_1.novello_app.account.CreateAccountActivity;

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
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;

import android.os.Looper;


//Mock the RequestServerForService class
@RunWith(AndroidJUnit4ClassRunner.class)
@LargeTest  //large execution time
public class CreateAccountActivityTest {

    private static final int SIMULATED_DELAY_MS = 500;

    @Rule   //needed to launch the activity
    public ActivityTestRule<CreateAccountActivity> activityRule = new ActivityTestRule<>(CreateAccountActivity.class);

    /**
     * Tests failed create account
     */
    @Test
    public void createAccountFailure() {
        onView(ViewMatchers.withId(R.id.username)).perform(typeText("NewUsername"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("NewPassword"), closeSoftKeyboard());
        onView(withId(R.id.confirmPassword)).perform(typeText("NotNewPassword"), closeSoftKeyboard());
        onView(withId(R.id.createAccount)).perform(click());
        onView(withId(R.id.createAccount)).check(matches(withText("Create Account")));
    }

    /**
     * Tests successful create account
     */
    @Test
    public void createAccountSuccess() {
        onView(withId(R.id.username)).perform(typeText("NewUsername"), closeSoftKeyboard());
        onView(withId(R.id.password)).perform(typeText("NewPassword"), closeSoftKeyboard());
        onView(withId(R.id.confirmPassword)).perform(typeText("NewPassword"), closeSoftKeyboard());
        onView(withId(R.id.createAccount)).perform(click());
        onView(withId(R.id.login)).check(matches(withText("Login")));
    }

}