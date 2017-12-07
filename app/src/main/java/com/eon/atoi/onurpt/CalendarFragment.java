package com.eon.atoi.onurpt;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.alamkanak.weekview.MonthLoader;
import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Atoi on 4.12.2017.
 */

public class CalendarFragment extends Fragment {
    private String title;
    private int page;
    private static final DateFormat sdf = new SimpleDateFormat("HH:mm");
    private final Calendar cal = Calendar.getInstance();
    private WeekView weekView;
    public static String timeString;
    private List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
    Calendar calendar = Calendar.getInstance();
    WeekViewEvent event;

    public static CalendarFragment newInstance(int page, String title)
    {
        CalendarFragment calendarFragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        calendarFragment.setArguments(args);
        return calendarFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.calendar_fragment, container, false);
        weekView = (WeekView) view.findViewById(R.id.weekView);
        weekView.setMonthChangeListener(mMonthChangeListener);
        weekView.setEmptyViewLongPressListener(new WeekView.EmptyViewLongPressListener() {
            @Override
            public void onEmptyViewLongPress(Calendar time) {
                DialogFragment newFragment = MyAlertDialogFragment
                        .newInstance(R.string.appointmentStr);
                timeString = new SimpleDateFormat("HH:mm").format(time.getTime());

                calendar = (Calendar) time.clone();
                newFragment.show(getFragmentManager(), "dialog");
            }
        });


        WeekView.EventClickListener mEventClickListener = new WeekView.EventClickListener() {
            @Override
            public void onEventClick(final WeekViewEvent event, RectF eventRect) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        DialogFragment newFragment = MyAlertDialogFragment
                                .newInstance(R.string.eventStr);
                        newFragment.show(getFragmentManager(), "Event");
                        event.setColor(Color.GREEN);
                        weekView.notifyDatasetChanged();
                    }
                });
            }
        };

        weekView.setOnEventClickListener(mEventClickListener);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        if (!prefs.getBoolean("firstTime", false)) {
            Calendar startTime = calendar.getInstance();
            startTime.set(Calendar.HOUR_OF_DAY, 3);
            startTime.set(Calendar.MINUTE, 0);
            Calendar endTime = (Calendar) startTime.clone();
            endTime.set(Calendar.HOUR_OF_DAY, 4);
            endTime.set(Calendar.MINUTE, 0);

            event = new WeekViewEvent(0, "Workout", startTime, endTime);
            event.setColor(Color.RED);
            events.add(event);

            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }

        return view;
    }

    protected String getEventTitle(Calendar time) {
        return String.format("Event of %02d:%02d %s/%d", time.get(Calendar.HOUR_OF_DAY), time.get(Calendar.MINUTE), time.get(Calendar.MONTH)+1, time.get(Calendar.DAY_OF_MONTH));
    }

    MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
        @Override
        public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {

            Calendar mainCalendar = Calendar.getInstance();
            if (newMonth == mainCalendar.get(Calendar.MONTH))
                return events;
            else {
                return new ArrayList<WeekViewEvent>();
            }
        }
    };

    private boolean eventMatch(WeekViewEvent matchEvent) {
        if(matchEvent.getId() == event.getId())
            return true;
        return false;
    }

    private boolean eventMatches(WeekViewEvent event, int year, int month) {
        return (event.getStartTime().get(Calendar.YEAR) == year && event.getStartTime().get(Calendar.MONTH) == month) ||
                (event.getEndTime().get(Calendar.YEAR) == year && event.getEndTime().get(Calendar.MONTH) == month);
    }

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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                weekView.notifyDatasetChanged();
            }
        });
    }

    public void doNegativeClick() {
        // Do stuff here.
        Log.i("FragmentAlertDialog", "Negative click!");
    }

}
