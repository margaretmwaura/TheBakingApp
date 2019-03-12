package com.example.admin.baking;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.RemoteViews;

import com.google.gson.Gson;

import static android.content.Context.MODE_PRIVATE;

/**
 * Implementation of App Widget functionality.
 */

public class BakingAppWidget extends AppWidgetProvider
{

    private static RemoteViews views;
//    This method is for updating a single widget

//    Call this method in the method that is being called in the static method that is being called in the activity
    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId,Bundle info) {


        // Construct the RemoteViews object
        views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);


        setTheUiData(info,context);
//        This is where I will set the information of the views
//        views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

//    This is the method that will be called in the details activity
    public static void wiringUpTheWidget(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds,Bundle info)
    {
        for (int appWidgetId : appWidgetIds)
        {
            updateAppWidget(context, appWidgetManager, appWidgetId,info);
        }
    }
    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds)
    {
        // There may be multiple widgets active, so update all of them

    }

    @Override
    public void onEnabled(Context context)
    {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context)
    {
        // Enter relevant functionality for when the last widget is disabled
    }

//    This method will be called in the updateAppWidget method
//    Will create a method that will be populating the widget view with the current step and ingredient data
    public static void setTheUiData( Bundle info,Context context)
    {

//     Retrieve all the data in the bundle
        Recipe recipe = info.getParcelable("widgetRecipe");

//        Saving the recipe in a shared preference
        SharedPreferences mPrefs = context.getSharedPreferences("RecipeFile",Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = mPrefs.edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe); // myObject - instance of MyObject
        prefsEditor.putString("MyObject", json);
        prefsEditor.commit();
//End of the storing
        String recipeName = recipe.getName();
        String imageUrl = recipe.getImageUrl();
        int ingredientsCount = recipe.getIngredients().size();
        int stepsCount = recipe.getSteps().size();
        views.setTextViewText(R.id.appwidget_recipe_text,recipeName);
        views.setTextViewText(R.id.appwidget_ingredients_count,String.valueOf(ingredientsCount));
        views.setTextViewText(R.id.appwidget_steps_count,String.valueOf(stepsCount));

//
        //        Retrieving the value of the shared preference
        Gson gsonretrieved = new Gson();
        String jsonretrieved = mPrefs.getString("MyObject", "");
        Recipe obj = gsonretrieved.fromJson(jsonretrieved, Recipe.class);

//        Starting up the intent that will be used to open up the intent
        Intent appIntent = new Intent(context,Details.class);
        appIntent.putExtra("Recipe",obj);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context,0,appIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        views.setOnClickPendingIntent(R.id.containerLayout,appPendingIntent);

//        End of the code that launches the details activity
    }
}

