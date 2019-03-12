package com.example.admin.baking;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.List;

public class Details extends AppCompatActivity
{
//This are variables that are important for updating of the widget
    private int ingredientsCount;
    private int stepsCount;
    private String recipeName;
    private String recipeImage;

//    The end of those variables

    private Button previous,next;
    private int count = 0;
    private Recipe recipe;
    private Step step;
    private FragmentManager fragmentManager;
    private List<Ingredient> ingredientList;
    private List<Step> stepList;
//    This is the interface that will be implemented once the buttons for the next and previous have been clicked


    @Override
    protected void onCreate(Bundle savedInstanceState)
        {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Intent i= getIntent();
        recipe = i.getParcelableExtra("Recipe");
        step = i.getParcelableExtra("Steps");
        count = step.getId();

        int id = recipe.getId();

        stepList = recipe.getSteps();
        ingredientList = recipe.getIngredients();

//        Setting of the values relating to the widget
            ingredientsCount = ingredientList.size();
            stepsCount = stepList.size();
            recipeName = recipe.getName();
            recipeImage = recipe.getImageUrl();
//            All the variables have been set

//            Creating the bundle that will have all the data pertaining the widget
            Bundle appWidget = new Bundle();
            appWidget.putParcelable("widgetRecipe",recipe);
//            The bundle is all set

//            Calling the method for updating the widget data
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
            BakingAppWidget.wiringUpTheWidget(this,appWidgetManager,appWidgetIds,appWidget);
            Toast.makeText(this,"The Widget has been updated ",Toast.LENGTH_LONG).show();

//            The end of the method


        previous = (Button) findViewById(R.id.button_previous);
        next = (Button) findViewById(R.id.button_next);
        Toast.makeText(this,"The recipe id " + id, Toast.LENGTH_SHORT).show();
        Log.d("DetailsActivity","This is the id of the recipe " + id);

//        The bundle to be sent to the fragments

        Bundle bundle = new Bundle();
        bundle.putParcelable("TransferRecipe",recipe);
        bundle.putInt("TheCount",count);

//        This is the code for the fragment manager
        fragmentManager = getSupportFragmentManager();

//        This is the stepsFragment code
        StepsFragment stepsFragment = new StepsFragment();
        stepsFragment.setArguments(bundle);
        fragmentManager.beginTransaction()
        .add(R.id.ingredients_steps_fragment,stepsFragment)
                .commit();

////This is the ingredientsFragment code
//        IngredientsFragment ingredientsFragment = new IngredientsFragment();
//        ingredientsFragment.setArguments(bundle);
//        fragmentManager.beginTransaction()
//                .add(R.id.ingredients,ingredientsFragment)
//                .commit();


        previous.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Log.d("Previous","Moving to previous");
                if(count != 0)
                {
                    count = count - 1;
                    Bundle bundleReplace = new Bundle();
                    bundleReplace.putParcelable("TransferRecipe",recipe);
                    bundleReplace.putInt("TheCount",count);

//                    Create a new fragment instance for the steps
                    StepsFragment stepsFragment1 = new StepsFragment();
                    stepsFragment1.setArguments(bundleReplace);
                    fragmentManager.beginTransaction()
                    .replace(R.id.ingredients_steps_fragment,stepsFragment1)
                            .commit();

////                    Create a new fragment instance for the ingredients
//                    IngredientsFragment ingredientsFragment1 = new IngredientsFragment();
//                    ingredientsFragment1.setArguments(bundleReplace);
//                    fragmentManager.beginTransaction()
//                            .replace(R.id.ingredients,ingredientsFragment1)
//                            .commit();



                }
                else
                {
                    Toast.makeText(getApplicationContext(),"That is the end of the recipe",Toast.LENGTH_LONG).show();
                }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
//                if(count <= (ingredientList.size()-1))
//                {
//                    Log.d("Next", "Moving to the next one");
//                    count = count + 1;
//                    Bundle bundleReplace = new Bundle();
//                    bundleReplace.putParcelable("TransferRecipe", recipe);
//                    bundleReplace.putInt("TheCount", count);
//
////                    //                    Create a new fragment instance for the ingredients
////                    IngredientsFragment ingredientsFragment1 = new IngredientsFragment();
////                    ingredientsFragment1.setArguments(bundleReplace);
////                    fragmentManager.beginTransaction()
////                            .replace(R.id.ingredients, ingredientsFragment1)
////                            .commit();
//                }
//                else
//                    {
//                        Toast.makeText(getApplicationContext(),"There is no ingredient for that recipe",Toast.LENGTH_LONG).show();
//                }

                if(count <= (stepList.size() - 1))
                {
                    Log.d("Next", "Moving to the next one");
                    count = count + 1;
                    Bundle bundleReplace = new Bundle();
                    bundleReplace.putParcelable("TransferRecipe", recipe);
                    bundleReplace.putInt("TheCount", count);
//                    Create a new fragment instance for the steps
                    StepsFragment stepsFragment1 = new StepsFragment();
                    stepsFragment1.setArguments(bundleReplace);
                    fragmentManager.beginTransaction()
                            .replace(R.id.ingredients_steps_fragment, stepsFragment1)
                            .commit();

                }
                else
                {
                    Toast.makeText(getApplicationContext(),"That is no step for that recipe ",Toast.LENGTH_LONG).show();
                }

            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();

        if(this.getApplicationContext() != null)
        {

            Log.d("Attaching","The button interface has been attached ");
        }
        else
        {
            Log.d("Attaching","The button has not been attached ");
        }
    }

//    Code for the case of the configuration changes
@Override
public void onConfigurationChanged(Configuration newConfig)
{
    super.onConfigurationChanged(newConfig);
    if(newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE)
    {

            RelativeLayout relativeLayout = findViewById(R.id.layout_buttons);
            relativeLayout.setVisibility(View.GONE);
        Log.d("Landscape","Method has been called ");
    }
    if(newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
    {

        RelativeLayout relativeLayout = findViewById(R.id.layout_buttons);
        relativeLayout.setVisibility(View.VISIBLE);
        Log.d("Portrait","Method has been called ");
    }

}
}
