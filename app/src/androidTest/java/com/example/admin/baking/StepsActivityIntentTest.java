package com.example.admin.baking;


import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;

import static android.support.test.espresso.intent.Intents.intended;

import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;

import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static org.hamcrest.Matchers.not;


@RunWith(AndroidJUnit4.class)
@LargeTest
public class StepsActivityIntentTest
{
    CountingIdlingResource countingIdlingResource;

    @Rule
    public IntentsTestRule<MainActivity> mActivityTestRule
            = new IntentsTestRule<>(MainActivity.class);
    @Before
    public void registerIdlingResource()
    {

        countingIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }
    @Before
    public void stubAllExternalIntents() {
        // By default Espresso Intents does not stub any Intents. Stubbing needs to be setup before
        // every test run. In this case all external Intents will be blocked.
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));
    }

    @Test
    public void clickOnRecyclerViewItem_opensStepsActivity()
    {
        onView(withId(R.id.recyclerView_recipes)).perform(RecyclerViewActions.actionOnItemAtPosition(2,click()));

        intended
                (
                hasComponent(TheStepsActivity.class.getName())
        );

    }
    @After
    public void unregisterIdlingResource() {
        if (countingIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(countingIdlingResource);
        }
    }

}
