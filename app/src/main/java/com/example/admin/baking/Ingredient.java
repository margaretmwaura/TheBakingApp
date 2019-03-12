package com.example.admin.baking;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import javax.xml.transform.Source;

public class Ingredient implements Parcelable
{

    @SerializedName("quantity")
    double quantity;
    @SerializedName("measure")
    String measure;
    @SerializedName("ingredient")
    String ingredients;

    public void setQuantity(double quantity)
    {
        this.quantity = quantity;
    }
    public void setMeasure(String measure)
    {
        this.measure = measure;
    }
    public void setIngredients(String ingredients)
    {
        this.ingredients = ingredients;
    }
    public double getQuantity()
    {
        return this.quantity;
    }
    public String getMeasure()
    {
        return this.measure;
    }
    public String getIngredients()
    {
        return this.ingredients;
    }

    public Ingredient(double quantity, String measure, String ingredients)
    {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredients = ingredients;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
        dest.writeDouble(quantity);
        dest.writeString(measure);
        dest.writeString(ingredients);
    }
    public Ingredient()
    {

    }
    public Ingredient(Parcel in)
    {
        quantity = in.readDouble();
        measure = in.readString();
        ingredients = in.readString();
    }
    public static final Parcelable.Creator<Ingredient> CREATOR = new Parcelable.Creator<Ingredient>()
    {

        @Override
        public Ingredient createFromParcel(Parcel source)
        {

            return new Ingredient(source);
        }

        @Override
        public Ingredient[] newArray(int size)
        {
            return new Ingredient[size];
        }
    };
}
