package com.yn_1.novello_app.account;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RatingBar;

import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.yn_1.novello_app.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ReviewsTest {

    private static final int SIMULATED_DELAY_MS = 1000;

    @Rule
    public ActivityTestRule<LoginActivity> mActivityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void reviewsTest() {
        // Input username
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.inputUsername),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("IChangedMyName"), closeSoftKeyboard());

        // Input Password
        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.inputPassword),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("5760"), closeSoftKeyboard());

        // Login
        ViewInteraction materialButton = onView(
                allOf(withId(R.id.login), withText("Login"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                3),
                        isDisplayed()));
        materialButton.perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Go to Library
        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.libraryFragment), withContentDescription("Library"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_nav),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Open pop-up of read book
        ViewInteraction imageButton = onView(
                childAtPosition(
                        childAtPosition(
                                withId(R.id.read),
                                0),
                        0));
        imageButton.perform(scrollTo(), longClick());

        // Open rating/review page
        ViewInteraction materialTextView = onView(
                allOf(withId(android.R.id.title), withText("Rate/Review"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        // Enter review comment
        ViewInteraction ratingBar = onView(
                allOf(withId(R.id.postReviewRatingBar),
                        isDisplayed()));
        ratingBar.perform((ViewAction) new SetRating(), closeSoftKeyboard());

        // Enter review comment
        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.postReviewText),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textInputLayout),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("Espresso Test Review!"), closeSoftKeyboard());

        // Submit review
        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.postReviewButton), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.nav_host_fragment),
                                        0),
                                5),
                        isDisplayed()));
        materialButton2.perform(click());

        try {
            Thread.sleep(SIMULATED_DELAY_MS);
        } catch (InterruptedException e) {
        }

        ViewInteraction tableLayout = onView(
                allOf(withId(R.id.reviewsTable),
                        withParent(withParent(IsInstanceOf.<View>instanceOf(android.widget.ScrollView.class))),
                        isDisplayed()));
        tableLayout.check(matches(isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withText("IChangedMyName"),
                        withParent(withParent(withId(R.id.reviewsTable))),
                        isDisplayed()));
        textView.check(matches(withText("IChangedMyName")));

        ViewInteraction textView2 = onView(
                allOf(withText("3.5"),
                        withParent(withParent(withId(R.id.reviewsTable))),
                        isDisplayed()));
        textView2.check(matches(withText("3.5")));

        ViewInteraction textView3 = onView(
                allOf(withText("Espresso Test Review!"),
                        withParent(withParent(withId(R.id.reviewsTable))),
                        isDisplayed()));
        textView3.check(matches(withText("Espresso Test Review!")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }

    public final class SetRating implements ViewAction {

        @Override
        public Matcher<View> getConstraints() {
            Matcher <View> isRatingBarConstraint = ViewMatchers.isAssignableFrom(RatingBar.class);
            return isRatingBarConstraint;
        }

        @Override
        public String getDescription() {
            return "Custom view action to set rating.";
        }

        @Override
        public void perform(UiController uiController, View view) {
            RatingBar ratingBar = (RatingBar) view;
            ratingBar.setRating(3.5f);
        }
    }
}
