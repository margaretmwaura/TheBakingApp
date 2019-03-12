package com.example.admin.baking;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.List;

public class TheStepsActivity extends AppCompatActivity implements IngredientsFragment.onHolderClickListener
{
    private int ingredientsCount;
    private int stepsCount;
    private int count = 0;
    private Recipe recipe;
    private FragmentManager fragmentManager;
    private List<Ingredient> ingredientList;
    private List<Step> stepList;
    private boolean isTabletMode;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_the_steps_activity);


//Now this is the part that is important inorder for us to get the clicked item details
        Intent i= getIntent();
        recipe = i.getParcelableExtra("Recipe");
        int recipeId = i.getIntExtra("RecipeId",0);
        Log.d("StepsActivity","This is the id of the recipe " + recipeId);
//            Creating the bundle that will have all the data pertaining the ingredientsFragment

//        This is the code is for the up navigation bar

         getSupportActionBar().setDisplayHomeAsUpEnabled(true);
         getSupportActionBar().setHomeButtonEnabled(true);


//         This is the end of the navigation bar


//        This is the code bit that we need to check  if the device is in tablet mode

        if(findViewById(R.id.tablet_mode) != null)
        {
            isTabletMode = true;

            Bundle detailsBundle = new Bundle();
            detailsBundle.putParcelable("detailRecipe",recipe);

//        Finished instantiaiting the bundle

            //        This is the code for the fragment manager
            fragmentManager = getSupportFragmentManager();
//        instantiating and setting the ingredientsFragmnent

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setArguments(detailsBundle);
            fragmentManager.beginTransaction()
                    .add(R.id.steps_fragment,ingredientsFragment)
                    .commit();

//            That is for the first fragment
//            This is for the second fragment

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

        }

        else
            {
            Bundle detailsBundle = new Bundle();
            detailsBundle.putParcelable("detailRecipe", recipe);

//        Finished instantiaiting the bundle

            //        This is the code for the fragment manager
            fragmentManager = getSupportFragmentManager();
//        instantiating and setting the ingredientsFragmnent

            IngredientsFragment ingredientsFragment = new IngredientsFragment();
            ingredientsFragment.setArguments(detailsBundle);
            fragmentManager.beginTransaction()
                    .add(R.id.steps_fragment, ingredientsFragment)
                    .commit();
        }



    }



    @Override
    public void holderClicked(Step step,Recipe recipe)
    {
        if(!isTabletMode)
        {
            if (step != null)
            {
                int id = step.getId();
                Toast.makeText(this, "This is the clicked position this is in the activity " + id, Toast.LENGTH_SHORT).show();
                Log.d("MainActivity ", "This is the id of the step " + id);
                Intent intent = new Intent(this, Details.class);
                intent.putExtra("Steps", step);
                intent.putExtra("Recipe", recipe);
                startActivity(intent);
            }
        }
        else
        {
//            Replace the existing fragment
            count = step.getId();

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

        }
    }
}
