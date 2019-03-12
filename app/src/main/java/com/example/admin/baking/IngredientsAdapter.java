package com.example.admin.baking;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.IngredientsViewHolder>
{

    private Context context;
    private List<Ingredient> mIngredients;
    private OnItemClickListener onItemClickListener;



    public IngredientsAdapter(Context context )
    {
        this.context = context;
        mIngredients = new ArrayList<>();

    }
    @NonNull
    @Override
    public IngredientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.activity_ingredients_fragment,parent,shouldAttachToParentImmediately);
        IngredientsViewHolder viewHolder = new IngredientsViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsViewHolder holder, int position)
    {
        Ingredient ingredient = new Ingredient();
        ingredient = mIngredients.get(position);
        String quantity = String.valueOf(ingredient.getQuantity());
        String measure = ingredient.getMeasure();
        String ingredientDetail = ingredient.getIngredients();

        holder.quantity.setText(quantity);
        holder.measure.setText(measure);
        holder.ingredientsView.setText(ingredientDetail);

    }

    @Override
    public int getItemCount() {
        return mIngredients.size();
    }
    public void setmIngredients(List<Ingredient> ingredientList)
    {
        if(ingredientList != null)
        {
            mIngredients.clear();
            mIngredients.addAll(ingredientList);
            notifyDataSetChanged();
            Log.d("IngredientsAdapter", "Method has been called");
        }
    }
    class IngredientsViewHolder extends RecyclerView.ViewHolder
    {

        TextView quantity;
        TextView measure;
        TextView ingredientsView;
        public IngredientsViewHolder(View itemView)
        {
            super(itemView);
            quantity = (TextView) itemView.findViewById(R.id.quantity);
            measure = (TextView) itemView.findViewById(R.id.measure);
            ingredientsView = (TextView) itemView.findViewById(R.id.ingredientsView);
        }
    }
}
