package com.example.admin.baking;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder>
{

    private Context context;
    private List<Recipe> mrecipe;
    private OnItemClickListener onItemClickListener;



    public RecipeAdapter(Context context )
    {
        this.context = context;
        mrecipe = new ArrayList<>();

    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.activity_recipe,parent,shouldAttachToParentImmediately);
        RecipeViewHolder recipeViewHolder = new RecipeViewHolder(view);
        return recipeViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position)
    {
       Recipe recipe = this.mrecipe.get(position);
        String name = recipe.getName();
        holder.recipeName.setText(name);
       String image = recipe.getImageUrl();

       if(image != null)
       {
           String image1 = image.trim();
           Picasso.Builder builder = new Picasso.Builder(context);
           builder.listener(new Picasso.Listener() {
               @Override
               public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                   exception.printStackTrace();
                   Log.d("Picasso Error", "The error " + exception);
               }
           });
           builder.build().load(image1).fit().into(holder.recipeImage);
       }
       else
       {
           holder.recipeImage.setImageResource(R.drawable.ic_cake_black_24dp);
       }

    }

    @Override
    public int getItemCount()
    {
        return this.mrecipe.size();
    }

    public void setRecipeList(List<Recipe> recipes)
    {
        mrecipe.clear();
        mrecipe.addAll(recipes);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView recipeName;
        ImageView recipeImage;
        public RecipeViewHolder(View itemView)
        {
            super(itemView);

            recipeName = (TextView) itemView.findViewById(R.id.recipe_name);
            recipeImage = (ImageView) itemView.findViewById(R.id.recipe_image);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            onItemClickListener.onClick(v,getAdapterPosition());
        }
    }
}
