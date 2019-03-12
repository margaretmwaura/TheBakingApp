package com.example.admin.baking;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface getData
{
    @GET("topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe>> getAllRecipe();


    @GET("topher/2017/May/59121517_baking/{recipe_id}/steps")
    Call<List<Step>> getAllRecipeStep(@Path("recipe_id") int id);


}
