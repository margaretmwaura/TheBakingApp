package com.example.admin.baking;

import android.content.Intent;
import android.os.Parcelable;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements OnItemClickListener
{
    public  CountingIdlingResource idlingResource = new CountingIdlingResource("LOADER");
    LinearLayoutManager linearLayoutManager;
    List<Recipe> recipes;
    RecyclerView recyclerView;
    RecipeAdapter recipeAdapter;
    private static Retrofit retrofit = null;
    public static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/";


    public  CountingIdlingResource getIdlingResource()
    {
        return idlingResource;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView_recipes);
        recipeAdapter = new RecipeAdapter(this);


        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(recipeAdapter);
        recipeAdapter.setOnItemClickListener(this);
        getRecipeData();


//        This is for restoring the state of the recyclerView
        if (savedInstanceState != null) {

//            int scrollPosition = savedInstanceState.getInt("BUNDLE_RECYCLER_LAYOUT");
//            String log = String.valueOf(scrollPosition);
//            recyclerView.scrollToPosition(scrollPosition);
//            Log.d("The gotten position " , log);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("BUNDLE_RECYCLER_LAYOUT");
            recyclerView.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            recipes = savedInstanceState.getParcelableArrayList("INSTANCE_STATE_ENTRIES");
            recipeAdapter.setRecipeList(recipes);


        }

    }

    public void getRecipeData()
    {

        idlingResource.increment();
        if(retrofit == null)
        {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        getData toUse = retrofit.create(getData.class);
        Call<List<Recipe>> call = toUse.getAllRecipe();
        call.enqueue(new Callback<List<Recipe>>()
        {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response)
            {
                recipes = response.body();

                if(recipes != null)
                {
                    recipeAdapter.setRecipeList(recipes);
                    Log.d("GettingRecipes ", "This was a huge success ");
                }
                idlingResource.decrement();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t)
            {
                Log.d("GettingRecipes ","Fail " + t.getMessage());

                idlingResource.decrement();
            }

        });

    }
    @Override
    public void onClick(View view, int position)
    {

        Recipe recipe = recipes.get(position);
         int recipeId = recipe.id;
        int id = recipe.getId();
        Log.d("OnCLick","Method has been called for clicking the fragment");
        Toast.makeText(this, "This is the clicked position this is in the activity " + id, Toast.LENGTH_SHORT).show();
            Log.d("MainActivity ", "This is the id of the recipe " + id);
            Intent intent = new Intent(this, TheStepsActivity.class);
            intent.putExtra("Recipe", recipe);
            intent.putExtra("RecipeId",recipeId);
            startActivity(intent);


//        mclick.holderClicked(recipe);
    }

//    @Override
//    public void holderClicked(Recipe recipe)
//    {
//        if(recipe!= null)
//        {
//            int id = recipe.getId();
//            Toast.makeText(this, "This is the clicked position this is in the activity " + id, Toast.LENGTH_SHORT).show();
//            Log.d("MainActivity ", "This is the id of the recipe " + id);
//            Intent intent = new Intent(this, TheStepsActivity.class);
//            intent.putExtra("Recipe", recipe);
//            startActivity(intent);
//        }
//    }


//    This is the code for saving the state of the recyclerView
         @Override
       public void onSaveInstanceState(Bundle outState) {

//        int position = gridLayoutManager.findFirstVisibleItemPosition();
//        outState.putInt("BUNDLE_RECYCLER_LAYOUT", gridLayoutManager.findFirstVisibleItemPosition());
//        String log = String.valueOf(position);
//        Log.d("The position is " ,log);
        outState.putParcelable("BUNDLE_RECYCLER_LAYOUT", recyclerView.getLayoutManager().onSaveInstanceState());
        outState.putParcelableArrayList("INSTANCE_STATE_ENTRIES", (ArrayList<? extends Parcelable>)recipes);
        super.onSaveInstanceState(outState);

       }
}
