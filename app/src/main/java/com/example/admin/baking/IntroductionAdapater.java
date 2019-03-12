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

public class IntroductionAdapater extends RecyclerView.Adapter<IntroductionAdapater.IntroductionViewHolder>
{

    private Context context;
    private List<Step> mSteps;
    private OnItemClickListener onItemClickListener;



    public IntroductionAdapater(Context context )
    {
        this.context = context;
        mSteps = new ArrayList<>();

    }
    @NonNull
    @Override
    public IntroductionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.activity_titles,parent,shouldAttachToParentImmediately);
        IntroductionViewHolder viewHolder = new IntroductionViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull IntroductionViewHolder holder, int position)
    {

        Step currentStep = mSteps.get(position);
        String introduction = currentStep.getShortDescription();
        holder.ingredientsIntroduction.setText(introduction);
    }

    @Override
    public int getItemCount() {
        return mSteps.size();
    }

    public void setmSteps(List<Step> Steps)
    {

        if(Steps!= null)
        {
            mSteps.clear();
            mSteps.addAll(Steps);
            notifyDataSetChanged();
            Log.d("IntroductionAdapter", "The method has been called and this are the number of steps " + mSteps.size());
        }
    }
    public void setOnItemClickListener(OnItemClickListener onItemClickListener)
    {
        this.onItemClickListener = onItemClickListener;
    }

    class IntroductionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        TextView ingredientsIntroduction;
        public IntroductionViewHolder(View itemView)
        {
            super(itemView);
            ingredientsIntroduction = (TextView)itemView.findViewById(R.id.recipe_introduction_titles);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            onItemClickListener.onClick(v,getAdapterPosition());

        }
    }
}
