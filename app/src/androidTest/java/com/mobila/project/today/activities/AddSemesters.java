package com.mobila.project.today.activities;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import com.mobila.project.today.R;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddSemesters {

    @Rule
    public ActivityTestRule<TodayActivity> mActivityTestRule = new ActivityTestRule<>(TodayActivity.class);

    @Test
    public void todayActivityTest() {
        ViewInteraction appCompatImageButton = onView(
                allOf(withId(R.id.go_foreward_semester),
                        childAtPosition(
                                allOf(withId(R.id.semesters_layout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        ViewInteraction appCompatImageButton2 = onView(
                allOf(withId(R.id.go_foreward_semester),
                        childAtPosition(
                                allOf(withId(R.id.semesters_layout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatImageButton2.perform(click());

        ViewInteraction appCompatImageButton3 = onView(
                allOf(withId(R.id.go_foreward_semester),
                        childAtPosition(
                                allOf(withId(R.id.semesters_layout),
                                        childAtPosition(
                                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                                1)),
                                2),
                        isDisplayed()));
        appCompatImageButton3.perform(click());
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
}
