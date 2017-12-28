package com.eon.atoi.onurpt.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.eon.atoi.onurpt.R;

import java.util.ArrayList;

/**
 * Created by Atoi on 28.12.2017.
 */

public class FoodListAdapter extends RecyclerView.Adapter<FoodListAdapter.ViewHolder>{

    private ArrayList<Integer> imageIds;

    public FoodListAdapter(ArrayList<Integer> imageIds)  {
        this.imageIds = imageIds;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.food_row, parent, false);
        return new FoodListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.img.setImageResource(imageIds.get(position));

    }

    @Override
    public int getItemCount() {
        return imageIds.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView img;

        public ViewHolder(View view) {
            super(view);
            img = (ImageView) view.findViewById(R.id.imgFood);
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();

                    switch(pos)
                    {
                        case 0:

                    }
                }
            });
        }
    }
}
