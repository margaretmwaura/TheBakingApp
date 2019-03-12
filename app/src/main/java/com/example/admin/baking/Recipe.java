package com.example.admin.baking;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements Parcelable
{
        @SerializedName("id")
        int id;
        @SerializedName("name")
        String name;
        @SerializedName("ingredients")
        List<Ingredient> ingredients;
        @SerializedName("steps")
        List<Step> steps;
        @SerializedName("servings")
        int servings;
        @SerializedName("imageUrl")
        String imageUrl;

        private void setId(int id)
        {
            this.id = id;
        }
        private void setName(String name)
        {
            this.name = name;
        }
        private void setIngredients(List<Ingredient> ingredients)
        {
            this.ingredients = ingredients;
        }
        private void setSteps(List<Step> steps)
        {
            this.steps = steps;
        }
        private void setServings(int servings)
        {
            this.servings = servings;
        }
        private void setImageUrl(String imageUrl)
        {
            this.imageUrl = imageUrl;
        }
        public int getId()
        {
            return this.id;
        }
        public String getName()
        {
            return this.name;
        }
        public List<Ingredient> getIngredients()
        {
            return this.ingredients;
        }
        public List<Step> getSteps()
        {
            return this.steps;
        }
        private int getServings()
        {
            return this.servings;
        }
        public String getImageUrl()
        {
            return this.imageUrl;
        }

        public Recipe(int id,String name,List<Ingredient> ingredients,List<Step> steps,int servings,String imageUrl)
        {
            this.id = id;
            this.name = name;
            this.ingredients = ingredients;
            this.steps = steps;
            this.servings = servings;
            this.imageUrl = imageUrl;
        }
        public Recipe(Parcel in)
        {
            id = in.readInt();
            name = in.readString();
            ingredients = in.createTypedArrayList(Ingredient.CREATOR);
            steps = in.createTypedArrayList(Step.CREATOR);
            servings = in.readInt();
            imageUrl = in.readString();

        }
        @Override
        public int describeContents()
        {
            return 0;
        }
        @Override
        public void writeToParcel(Parcel dest, int flags)
        {
            dest.writeInt(id);
            dest.writeString(name);
            dest.writeTypedList(ingredients);
            dest.writeTypedList(steps);
            dest.writeInt(servings);
            dest.writeString(imageUrl);

        }
        public static final Parcelable.Creator<com.example.admin.baking.Recipe> CREATOR = new Parcelable.Creator<com.example.admin.baking.Recipe>()
        {

            @Override
            public com.example.admin.baking.Recipe createFromParcel(Parcel source)
            {
                return new com.example.admin.baking.Recipe(source);
            }

            @Override
            public com.example.admin.baking.Recipe[] newArray(int size) {
                return new com.example.admin.baking.Recipe[size];
            }
        };
}
