package com.eon.atoi.onurpt.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.Toast;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.utils.WorkoutDatabaseHelper;

import devadvance.circularseekbar.CircularSeekBar;

/**
 * Created by Atoi on 8.12.2017.


 */


public class WorkoutDetailActivity extends AppCompatActivity {

    private Chronometer mChronometer, mChronometer2;
    private CircularSeekBar mCircularSeekBar;
    private Button btnStart, btnPause, btnBreak,
                    btnDone;
    long timeWhenStopped = 0;
    private String workoutName;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_detail);

        Bundle bundle = getIntent().getExtras();
        if(bundle != null)
        {
                workoutName = bundle.getString("workoutName");
        }

        mChronometer = (Chronometer)findViewById(R.id.chronometer2);
        mChronometer.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                String temp = mChronometer.getText().toString();
                String[] split = temp.split(":");
                mCircularSeekBar.setProgress(Integer.valueOf(split[1]));
            }
        });

        mChronometer2 = (Chronometer)findViewById(R.id.chronometer3);

        mCircularSeekBar = (CircularSeekBar)findViewById(R.id.circularSeekBar1);
        mCircularSeekBar.setMax(60);

        btnStart = (Button)findViewById(R.id.btn_start);
        btnBreak = (Button)findViewById(R.id.btn_break);
        btnPause = (Button)findViewById(R.id.btn_pause);
        btnDone = (Button)findViewById(R.id.btn_done);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                mChronometer.start();
                btnStart.setVisibility(View.GONE);
                btnBreak.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.VISIBLE);
                btnDone.setVisibility(View.VISIBLE);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.stop();
                timeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
                btnPause.setVisibility(View.GONE);
                btnBreak.setVisibility(View.GONE);
                btnDone.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
            }
        });

        btnBreak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mChronometer.stop();
                timeWhenStopped = mChronometer.getBase() - SystemClock.elapsedRealtime();
                btnBreak.setVisibility(View.GONE);
                btnStart.setVisibility(View.VISIBLE);
                btnPause.setVisibility(View.GONE);
                btnDone.setVisibility(View.GONE);
            }
        });

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(WorkoutDetailActivity.this,
                        android.R.style.Theme_Material_Dialog_Alert);
                alertDialog.setTitle("Exercise Done!")
                .setMessage("Are you sure you are done?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        WorkoutDatabaseHelper workoutDatabaseHelper = WorkoutDatabaseHelper.getInstance(WorkoutDetailActivity.this);
                        workoutDatabaseHelper.deleteWorkout(workoutName);
                        finish();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setIcon(R.drawable.chest_icon)
                .show();
            }
        });
    }

}