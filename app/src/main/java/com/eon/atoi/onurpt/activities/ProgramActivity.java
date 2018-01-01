package com.eon.atoi.onurpt.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.eon.atoi.onurpt.POJOs.Event;
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
    private String hour, min;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.program_activity);

        FloatingActionButton fabCalendar = (FloatingActionButton)findViewById(R.id.fabCalendar);
        fabCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventsToCalendar();
            }
        });

        programList = (ListView)findViewById(R.id.program_list);
        programList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(), WorkoutDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("workoutName", list.get(position).getWorkoutName());
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }

    private void addEventsToCalendar() {
        final Dialog dialog = new Dialog(ProgramActivity.this);
        dialog.setContentView(R.layout.adding_calendar);
        dialog.setTitle("Adding to Program");

        Button dialogOk = (Button) dialog.findViewById(R.id.btn_ok);
        Button dialogCancel = (Button) dialog.findViewById(R.id.btn_cancel);
        TimePicker timePicker = (TimePicker)dialog.findViewById(R.id.timePicker1);
        timePicker.setIs24HourView(true);

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hour = hourOfDay+"";
                min = minute+"";
                if(hourOfDay < 9) {
                    hour = "0"+hourOfDay;
                }
                else if(minute < 9) {
                    min = "0" + min;
                }
                Log.d("time", hour + min);
            }
        });

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Event event = new Event();
                event.setmName("EventWorkout");
                event.setmStartTime(hour + ":" +  min);
                Log.d("event", event.getmStartTime());
                WorkoutDatabaseHelper workoutDatabaseHelper = WorkoutDatabaseHelper.getInstance(ProgramActivity.this);
                workoutDatabaseHelper.addEvent(event);
                Toast.makeText(ProgramActivity.this, "Added to Calendar", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        prepareList();
        ProgramListAdapter adapter = new ProgramListAdapter(this, list);
        programList.setAdapter(adapter);
    }

    private synchronized void prepareList() {
        WorkoutDatabaseHelper workoutDatabaseHelper = WorkoutDatabaseHelper.getInstance(this);
        list = workoutDatabaseHelper.getAllWorkouts();
    }
}
