package com.eon.atoi.onurpt.POJOs;

import android.graphics.Color;

import com.alamkanak.weekview.WeekViewEvent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Atoi on 1.01.2018.
 */

public class Event {
    private String eventName;
    private int mDayOfMonth;
    private String mStartTime;

    public String getmName() {
        return eventName;
    }

    public void setmName(String eventName) {
        this.eventName = eventName;
    }

    public int getmDayOfMonth() {
        return mDayOfMonth;
    }

    public void setmDayOfMonth(int mDayOfMonth) {
        this.mDayOfMonth = mDayOfMonth;
    }

    public String getmStartTime() {
        return mStartTime;
    }

    public void setmStartTime(String mStartTime) {
        this.mStartTime = mStartTime;
    }

}
