package com.eon.atoi.onurpt.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.adapters.FoodListAdapter;

import java.util.ArrayList;

/**
 * Created by Atoi on 28.12.2017.
 */

public class FoodActivity extends Activity{

    private RecyclerView recyclerView;
    private boolean mHorizontal;
    private ArrayList<Integer> imageIds = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.food_activity);

        LinearLayoutManager layoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);

        RecyclerView foodList = (RecyclerView) findViewById(R.id.recycler_view);
        foodList.setLayoutManager(layoutManager);

        prepareData();

        FoodListAdapter foodListAdapter = new FoodListAdapter(imageIds);
        foodList.setAdapter(foodListAdapter);
    }

    private void prepareData() {
        imageIds.add(R.drawable.breakfast_img);
        imageIds.add(R.drawable.lunch_img);
        imageIds.add(R.drawable.dinner_img);
    }
}
