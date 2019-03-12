package com.example.admin.baking;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingRegistry;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.rule.ActivityTestRule;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.contrib.RecyclerViewActions.scrollToPosition;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra;
import static android.support.test.espresso.matcher.ViewMatchers.hasDescendant;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.example.admin.baking.TestUtils.atPosition;


import android.support.test.runner.AndroidJUnit4;

import com.android21buttons.fragmenttestrule.FragmentTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityDisplayTest
{

//    This is what am checking if it exists in the textViews
    private String nutellaRecipeName = "Nutella Pie";
    private String browniesRecipeName = "Brownies";
    private String yellowCakeRecipeName = "Yellow Cake";
    private String cheesesCakeRecipeName = "Cheesecake";

    private CountingIdlingResource countingIdlingResource;


    //Setting the launchActivity to true lets the activity to be launched
//    This rule is important since the fragment is within the activity hence the test on the fragment cannot occur
//    when the activity has not been launched
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule
            = new ActivityTestRule<>(MainActivity.class,true,true);

//    This is meant to launch the activity where the fragment is found

//    Idling resources for background resources that is the retrofit call


    @Before
    public void registerIdlingResource()
    {

        countingIdlingResource = mActivityTestRule.getActivity().getIdlingResource();
        IdlingRegistry.getInstance().register(countingIdlingResource);
    }





    @Test
    public void checkTheTextViewData() throws Exception
    {
//This test checks the content of the recyclerView
//        Used has descendants since the viewHolder uses a viewGroup
        onView(withId(R.id.recyclerView_recipes))
                .check(matches(atPosition(0, hasDescendant(withText(nutellaRecipeName)))));
        onView(withId(R.id.recyclerView_recipes))
                .check(matches(atPosition(1, hasDescendant(withText(browniesRecipeName)))));

        onView(withId(R.id.recyclerView_recipes))
                .perform(scrollToPosition(2))
                .check(matches(atPosition(2, hasDescendant(withText(yellowCakeRecipeName)))));
        onView(withId(R.id.recyclerView_recipes))
                .perform(scrollToPosition(3))
                .check(matches(atPosition(3, hasDescendant(withText(cheesesCakeRecipeName)))));


    }


    @After
    public void unregisterIdlingResource() {
        if (countingIdlingResource != null) {
            IdlingRegistry.getInstance().unregister(countingIdlingResource);
        }
    }

}
