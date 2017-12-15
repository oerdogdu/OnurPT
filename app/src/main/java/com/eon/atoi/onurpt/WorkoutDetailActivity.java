package com.eon.atoi.onurpt;

import android.app.Activity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.github.omadahealth.circularbarpager.library.CircularBar;
import com.github.omadahealth.circularbarpager.library.CircularBarPager;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.concurrent.TimeUnit;

/**
 * Created by Atoi on 8.12.2017.
 */

public class WorkoutDetailActivity extends AppCompatActivity {

    private static final int INTERVAL = 1000;

    private CircularBarPager mCircularBarPager;
    private CircularBar circularBar;
    private CirclePageIndicator circlePageIndicator;
    private ViewPager viewPager;

    private boolean mIsPlay = false;
    private boolean mIsPause = false;
    private boolean mIsBreak  = true;
    private boolean mIsFirstAppRun = true;

    private Counter mCounter;


    // Create variables to store current data
    private String mCurrentTime ="00:00";
    private int mCurrentWorkout = 0;
    private int mCurrentData = 0;

    private int mStart = 0;
    private int mEnd = 0;
    private int mStep = 0;

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workout_detail);

        mCircularBarPager = (CircularBarPager) findViewById(R.id.circularBarPager);
        viewPager = mCircularBarPager.getViewPager();
        circularBar = mCircularBarPager.getCircularBar();
        circlePageIndicator = mCircularBarPager.getCirclePageIndicator();

        FragmentPagerAdapter fragmentPagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return null;
            }

            @Override
            public int getCount() {
                return 0;
            }
        };

        mCircularBarPager.setViewPagerAdapter(fragmentPagerAdapter);

        circularBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTimer("03:00");
            }
        });
    }

    private void startTimer(String time){

        String[] splitTime = time.split(":");

        int splitMinute = Integer.valueOf(splitTime[0]);
        int splitSecond = Integer.valueOf(splitTime[1]);

        Long mMilisSecond = (long) (((splitMinute * 60) + splitSecond) * 1000);

        int max = (((splitMinute * 60) + splitSecond) * 1000);
        mCircularBarPager.getCircularBar().setMax(max);
        mStep = (int) ((max * INTERVAL) / mMilisSecond);
        mCounter = new Counter(mMilisSecond, INTERVAL);

        mStart = mEnd;
        mEnd = mEnd + mStep;
        mCounter.start();
    }

    public class Counter extends CountDownTimer {

        private long mAlert=4000;
        private int paramAlert=1;
        private String mTimer;
        boolean isRunning = false;

        public Counter(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);

            // Convert time format from millisInFuture to "00:00" format
            mTimer = String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes(millisInFuture),
                    TimeUnit.MILLISECONDS.toSeconds(millisInFuture) -
                            TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisInFuture)));

            // If this is the first time screen display set break time view as start time view

            }

        @Override
        public void onFinish() {
            mStart = 0;
            mEnd = 0;
            isRunning = false;
            }

        @Override
        public void onTick(long millisUntilFinished) {
            // Set current view pager with current workout
            circlePageIndicator.setCurrentItem(mCurrentWorkout);

            isRunning = true;

            mStart = mEnd;
            mEnd = mEnd + mStep;

            circularBar.animateProgress(mStart, mEnd, INTERVAL);

            // Convert time format from millisUntilFinished to "00:00" format
            mTimer = String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished),
                    TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) -
                            TimeUnit.MINUTES.toSeconds(
                                    TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)));

        }

        // Method to pause count down timer
        public String timerPause(){
            return mTimer;
        }

        // Method to check count down timer status
        public Boolean timerCheck(){
            return isRunning;
        }

    }

}