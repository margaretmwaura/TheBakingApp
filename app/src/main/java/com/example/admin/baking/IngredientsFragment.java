package com.example.admin.baking;

import android.content.Context;
import android.graphics.Paint;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IngredientsFragment extends Fragment implements OnItemClickListener
{
    private Recipe recipe;
    private List<Ingredient> ingredients = new ArrayList<>();
    private List<Step> steps = new ArrayList<>();
    private RecyclerView ingredientsData;
    private RecyclerView stepsTitle;
    private LinearLayoutManager linearLayoutManager;
    private LinearLayoutManager linearLayoutManagerOne;
    private TextView textView, stepsTextView;
    public IngredientsFragment()
    {

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_layout_main, container, false);

        recipe = getArguments().getParcelable("detailRecipe");

//        Log.d("IngredientsFragment ","This is the id of the recipe " + recipe.id);
        ingredientsData = rootView.findViewById(R.id.ingredientRecyclerView);
        stepsTitle = rootView.findViewById(R.id.stepsTiitleRecyclerView);

        linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        linearLayoutManagerOne = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);

        ingredientsData.setLayoutManager(linearLayoutManager);
        stepsTitle.setLayoutManager(linearLayoutManagerOne);


        IngredientsAdapter ingredientsAdapter= new IngredientsAdapter(getActivity().getApplicationContext());
        IntroductionAdapater introductionAdapater = new IntroductionAdapater(getActivity().getApplicationContext());


        ingredientsData.setAdapter(ingredientsAdapter);
        stepsTitle.setAdapter(introductionAdapater);

            if(recipe.getIngredients()!=null && recipe.getSteps()!= null)
            {
                steps = recipe.getSteps();
                ingredients = recipe.getIngredients();
                Log.d("IngredientsFragment ","This is the size of the steps " + recipe.getSteps().size());
                ingredientsAdapter.setmIngredients(recipe.getIngredients());
                introductionAdapater.setmSteps(recipe.getSteps());
            }

        introductionAdapater.setOnItemClickListener(this);

            textView = (TextView) rootView.findViewById(R.id.main_label);
            stepsTextView = (TextView) rootView.findViewById(R.id.steps_label);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        stepsTextView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

//        This is for restoring the state of the recyclerView
        if (savedInstanceState != null) {

//            int scrollPosition = savedInstanceState.getInt("BUNDLE_RECYCLER_LAYOUT");
//            String log = String.valueOf(scrollPosition);
//            recyclerView.scrollToPosition(scrollPosition);
//            Log.d("The gotten position " , log);
            Parcelable savedRecyclerLayoutState = savedInstanceState.getParcelable("BUNDLE_RECYCLER_LAYOUT_INGREDIENTS");
            Parcelable savedRecyclerLayoutStateOne = savedInstanceState.getParcelable("BUNDLE_RECYCLER_LAYOUT_STEPS");
            ingredientsData.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutState);
            stepsTitle.getLayoutManager().onRestoreInstanceState(savedRecyclerLayoutStateOne);
            steps = savedInstanceState.getParcelableArrayList("INSTANCE_STATE_ENTRIES_STEPS");
            ingredients = savedInstanceState.getParcelableArrayList("INSTANCE_STATE_ENTRIES_INGREDIENTS");
            ingredientsAdapter.setmIngredients(ingredients);
            introductionAdapater.setmSteps(steps);


        }
        return rootView;
    }

    public interface onHolderClickListener
    {
        void holderClicked(Step step,Recipe recipe);
    }

    onHolderClickListener mclick;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context != null) {
            try {
                mclick = (onHolderClickListener) context;

                Log.d("Attaching", "Listener attached ");

            } catch (ClassCastException e) {
                Log.d("Attaching ", "Listener not attached " + e.toString());
            }
        }
        else
        {
            Log.d("Attaching " , "The activity is null ");
        }
    }

    @Override
    public void onClick(View view, int position)
    {
        Step step = recipe.getSteps().get(position);
        Log.d("OnCLick","Method has been called for clicking the fragment");
        mclick.holderClicked(step,recipe);
    }

//    This is for saving the state of the recyclerViews when the screen rotates
        @Override
      public void onSaveInstanceState(Bundle outState) {

//        int position = gridLayoutManager.findFirstVisibleItemPosition();
//        outState.putInt("BUNDLE_RECYCLER_LAYOUT", gridLayoutManager.findFirstVisibleItemPosition());
//        String log = String.valueOf(position);
//        Log.d("The position is " ,log);
       outState.putParcelable("BUNDLE_RECYCLER_LAYOUT_INGREDIENTS", ingredientsData.getLayoutManager().onSaveInstanceState());
       outState.putParcelable("BUNDLE_RECYCLER_LAYOUT_STEPS", stepsTitle.getLayoutManager().onSaveInstanceState());
      outState.putParcelableArrayList("INSTANCE_STATE_ENTRIES_STEPS", (ArrayList<? extends Parcelable>) steps);
      outState.putParcelableArrayList("INSTANCE_STATE_ENTRIES_INGREDIENTS", (ArrayList<? extends Parcelable>) ingredients);
      super.onSaveInstanceState(outState);

}
}
