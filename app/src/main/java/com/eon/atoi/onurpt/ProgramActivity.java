package com.eon.atoi.onurpt;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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

        programList = (ListView)findViewById(R.id.programList);
        prepareList();
        ProgramListAdapter adapter = new ProgramListAdapter(this, list);
        programList.setAdapter(adapter);
    }

    private synchronized void prepareList() {
        WorkoutDatabaseHelper workoutDatabaseHelper = WorkoutDatabaseHelper.getInstance(this);
        list = workoutDatabaseHelper.getAllWorkouts();
    }
}
