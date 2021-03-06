package com.eon.atoi.onurpt.activities;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.POJOs.Workout;
import com.eon.atoi.onurpt.adapters.CertainListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Atoi on 2.12.2017.
 */

public class CertainExerciseActivity extends Activity {

    private RecyclerView myRecycleView;
    private CertainListAdapter certainListAdapter;
    private List<Workout> workoutList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.abs_exercise);

        myRecycleView = (RecyclerView)findViewById(R.id.recycler_abs);
        workoutList = new ArrayList<>();
        certainListAdapter = new CertainListAdapter(CertainExerciseActivity.this, workoutList);
        myRecycleView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        myRecycleView.setItemAnimator(new DefaultItemAnimator());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 2);
        myRecycleView.setLayoutManager(mLayoutManager);
        myRecycleView.setAdapter(certainListAdapter);

        Bundle b = getIntent().getExtras();

        if(b.getString("type").equalsIgnoreCase("abs")) {
            PrepareListAbs();
            certainListAdapter.notifyDataSetChanged();
        }
        else if(b.getString("type").equalsIgnoreCase("shoulder"))
        {
            PrepareShoulder();
            certainListAdapter.notifyDataSetChanged();
        }
        else if(b.getString("type").equalsIgnoreCase("leg"))
        {
            PrepareLeg();
            certainListAdapter.notifyDataSetChanged();
        }
        else if(b.getString("type").equalsIgnoreCase("cardio"))
        {
            PrepareCardio();
            certainListAdapter.notifyDataSetChanged();
        }
        else if(b.getString("type").equalsIgnoreCase("biceps"))
        {
            PrepareBiceps();
            certainListAdapter.notifyDataSetChanged();
        }
        else if(b.getString("type").equalsIgnoreCase("chest"))
        {
            PrepareChest();
            certainListAdapter.notifyDataSetChanged();
        }
    }

    private void PrepareChest() {
        workoutList.add(new Workout("Crunches", String.valueOf(R.drawable.chest)));
        workoutList.add(new Workout("Sit Up", String.valueOf(R.drawable.sit_up_02)));
        workoutList.add(new Workout("Plank", String.valueOf(R.drawable.plank)));
        workoutList.add(new Workout("Side Lunges", String.valueOf(R.drawable.side_lunges)));
        workoutList.add(new Workout("Side Plank", String.valueOf(R.drawable.side_plank)));
        workoutList.add(new Workout("Sit Up2", String.valueOf(R.drawable.sit_up_01)));
    }

    private void PrepareBiceps() {
        workoutList.add(new Workout("Curl", String.valueOf(R.drawable.altarnate_bicep_curl)));
        workoutList.add(new Workout("Sit Up", String.valueOf(R.drawable.arnold_press)));
        workoutList.add(new Workout("Plank", String.valueOf(R.drawable.plank)));
        workoutList.add(new Workout("Side Lunges", String.valueOf(R.drawable.side_lunges)));
        workoutList.add(new Workout("Side Plank", String.valueOf(R.drawable.side_plank)));
        workoutList.add(new Workout("Sit Up2", String.valueOf(R.drawable.sit_up_01)));
    }

    private void PrepareCardio() {
        workoutList.add(new Workout("Curl", String.valueOf(R.drawable.altarnate_bicep_curl)));
        workoutList.add(new Workout("Sit Up", String.valueOf(R.drawable.arnold_press)));
        workoutList.add(new Workout("Plank", String.valueOf(R.drawable.plank)));
        workoutList.add(new Workout("Side Lunges", String.valueOf(R.drawable.side_lunges)));
        workoutList.add(new Workout("Side Plank", String.valueOf(R.drawable.side_plank)));
        workoutList.add(new Workout("Sit Up2", String.valueOf(R.drawable.sit_up_01)));

    }

    private void PrepareLeg() {
        workoutList.add(new Workout("Curl", String.valueOf(R.drawable.altarnate_bicep_curl)));
        workoutList.add(new Workout("Sit Up", String.valueOf(R.drawable.arnold_press)));
        workoutList.add(new Workout("Plank", String.valueOf(R.drawable.plank)));
        workoutList.add(new Workout("Side Lunges", String.valueOf(R.drawable.side_lunges)));
        workoutList.add(new Workout("Side Plank", String.valueOf(R.drawable.side_plank)));
        workoutList.add(new Workout("Sit Up2", String.valueOf(R.drawable.sit_up_01)));
    }

    private void PrepareShoulder() {
        workoutList.add(new Workout("Crunches", String.valueOf(R.drawable.chest)));
        workoutList.add(new Workout("Sit Up", String.valueOf(R.drawable.sit_up_02)));
        workoutList.add(new Workout("Plank", String.valueOf(R.drawable.plank)));
        workoutList.add(new Workout("Side Lunges", String.valueOf(R.drawable.side_lunges)));
        workoutList.add(new Workout("Side Plank", String.valueOf(R.drawable.side_plank)));
        workoutList.add(new Workout("Sit Up2", String.valueOf(R.drawable.sit_up_01)));

    }

    private void PrepareListAbs() {
        workoutList.add(new Workout("Crunches", String.valueOf(R.drawable.chest)));
        workoutList.add(new Workout("Sit Up", String.valueOf(R.drawable.sit_up_02)));
        workoutList.add(new Workout("Plank", String.valueOf(R.drawable.plank)));
        workoutList.add(new Workout("Side Lunges", String.valueOf(R.drawable.side_lunges)));
        workoutList.add(new Workout("Side Plank", String.valueOf(R.drawable.side_plank)));
        workoutList.add(new Workout("Sit Up2", String.valueOf(R.drawable.sit_up_01)));
    }


    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;


        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }


        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }
}
