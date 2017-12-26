package com.eon.atoi.onurpt.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.POJOs.Workout;
import com.eon.atoi.onurpt.adapters.ProgramListAdapter;
import com.eon.atoi.onurpt.utils.WorkoutDatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Atoi on 7.12.2017.
 */

public class ProgramActivity extends Activity {

    private ListView programList;
    private ArrayList<Workout> list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_activity);

        programList = (ListView)findViewById(R.id.program_list);
        programList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("click", "clicked");
                Intent intent = new Intent(getBaseContext(), WorkoutDetailActivity.class);
                startActivity(intent);
            }
        });
        prepareList();
        ProgramListAdapter adapter = new ProgramListAdapter(this, list);
        programList.setAdapter(adapter);
    }

    private synchronized void prepareList() {
        WorkoutDatabaseHelper workoutDatabaseHelper = WorkoutDatabaseHelper.getInstance(this);
        list = workoutDatabaseHelper.getAllWorkouts();
    }
}
