package com.eon.atoi.onurpt.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.eon.atoi.onurpt.POJOs.Event;
import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.utils.WorkoutDatabaseHelper;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Atoi on 4.12.2017.
 */

public class CalendarActivity extends Activity {
    private String title;
    private int page;
    private static final DateFormat sdf = new SimpleDateFormat("HH:mm");
    private final Calendar cal = Calendar.getInstance();
    private WeekView weekView;
    public static String timeString;
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    Calendar calendar = Calendar.getInstance();
    WeekViewEvent event;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_activity);

        weekView = (WeekView) findViewById(R.id.weekView);
        weekView.setMonthChangeListener(mMonthChangeListener);
        weekView.setEmptyViewLongPressListener(new WeekView.EmptyViewLongPressListener() {
            @Override
            public void onEmptyViewLongPress(Calendar time) {

            }
        });

        WeekView.EventClickListener mEventClickListener = new WeekView.EventClickListener() {
            @Override
            public void onEventClick(final WeekViewEvent event, RectF eventRect) {
                CalendarActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                    }
                });
            }
        };

        weekView.setOnEventClickListener(mEventClickListener);

    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH) + 1, time.get(Calendar.DAY_OF_MONTH));
    }

    MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
        @Override
        public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

            /*Calendar mainCalendar = Calendar.getInstance();
            if (newMonth == mainCalendar.get(Calendar.MONTH))
                return events;
            else {
                return new ArrayList<WeekViewEvent>();
            }
        }*/
            WorkoutDatabaseHelper workoutDatabaseHelper = WorkoutDatabaseHelper.getInstance(CalendarActivity.this);
            ArrayList<Event> eventList = new ArrayList();
            eventList = workoutDatabaseHelper.getAllEvents();
            for (Event event : eventList) {
                Calendar now = Calendar.getInstance();
                Calendar startTime = (Calendar) now.clone();
                String[] temp = event.getmStartTime().split(":");
                Log.d("time", event.getmStartTime());
                startTime.set(Calendar.DAY_OF_MONTH, 1);
                startTime.set(Calendar.MONTH, newMonth-1);
                startTime.set(Calendar.YEAR, newYear);
                startTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(temp[0]));
                startTime.set(Calendar.MINUTE, 0);
                Calendar endTime = (Calendar) startTime.clone();
                endTime.set(Calendar.HOUR_OF_DAY, startTime.get(Calendar.HOUR) + 1);

                WeekViewEvent weekViewEvent = new WeekViewEvent(1, event.getmName(), startTime, endTime);

                events.add(weekViewEvent);
                weekView.notifyDatasetChanged();

            }
            return events;
        }

        ;

        private boolean eventMatch(WeekViewEvent matchEvent) {
            if (matchEvent.getId() == event.getId())
                return true;
            return false;
        }

        private boolean eventMatches(WeekViewEvent event, int year, int month) {
            return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month) ||
                    (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month);
        }

    };
        public static class MyAlertDialogFragment extends DialogFragment {

            public static MyAlertDialogFragment newInstance(int title) {
                MyAlertDialogFragment frag = new MyAlertDialogFragment();
                Bundle args = new Bundle();
                args.putInt("title", title);
                frag.setArguments(args);
                return frag;
            }

            @Override
            public Dialog onCreateDialog(Bundle savedInstanceState) {
                int title = getArguments().getInt("title");

                return new AlertDialog.Builder(getActivity())
                        .setIcon(R.drawable.ic_mr_button_connected_01_light)
                        .setTitle(title)
                        .setMessage(timeString)
                        .setPositiveButton(R.string.OK,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {

                                    }
                                })
                        .setNegativeButton(R.string.Cancel,
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog,
                                                        int whichButton) {

                                    }
                                }).show();
            }
        }


        public void doPositiveClick() {
            Calendar startTime = calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 3);
            startTime.set(Calendar.MINUTE, 0);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, 4);
            endTime.set(Calendar.MINUTE, 0);

            WeekViewEvent event = new WeekViewEvent(0, "Epoca", startTime, endTime);

            event.setColor(Color.BLUE);
            events.add(event);
        }

        public void doNegativeClick() {
            // Do stuff here.
            Log.i("FragmentAlertDialog", "Negative click!");
        }

    };

