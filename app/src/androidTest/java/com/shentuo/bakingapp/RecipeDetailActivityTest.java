package com.shentuo.bakingapp;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.shentuo.bakingapp.ui.RecipeDetailActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

/**
 * Created by ShentuoZhan on 18/5/17.
 */
@RunWith(AndroidJUnit4.class)
public class RecipeDetailActivityTest {

    @Rule
    public ActivityTestRule<RecipeDetailActivity> mActivityRule = new ActivityTestRule<>(RecipeDetailActivity.class);

    @Test
    public void stepListDisplayed() throws Exception {
        // Check that the view displayed.
        onView(withId(R.id.rv_steps))
                .check(matches(isDisplayed()));
    }
}
