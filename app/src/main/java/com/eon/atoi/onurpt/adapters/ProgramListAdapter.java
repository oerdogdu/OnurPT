package com.eon.atoi.onurpt.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.POJOs.Workout;
import com.eon.atoi.onurpt.utils.WorkoutDatabaseHelper;

import java.util.ArrayList;

/**
 * Created by Atoi on 7.12.2017.
 */

public class ProgramListAdapter extends BaseAdapter {

    private Activity activity;
    private static LayoutInflater inflater = null;
    private ArrayList<Workout> workoutProgram = new ArrayList<>();

    public ProgramListAdapter(Activity activity, ArrayList<Workout> workoutProgram) {
        this.activity = activity;
        this.workoutProgram = workoutProgram;
        Log.d("size", workoutProgram.size()+"");
        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public int getCount() {
        return workoutProgram.size();
    }

    public Workout getItem(int position) {
        return (Workout) workoutProgram.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.program_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.program_title);
        ImageView thumbnail = (ImageView) vi.findViewById(R.id.program_image);
        TextView set = (TextView)vi.findViewById(R.id.program_set);
        TextView reps = (TextView)vi.findViewById(R.id.program_reps);
        FloatingActionButton fabDelete = (FloatingActionButton)vi.findViewById(R.id.fabDelete);
        FloatingActionButton fabCalendar = (FloatingActionButton)vi.findViewById(R.id.fabCalendar);

        final Workout workout = getItem(position);

        fabDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WorkoutDatabaseHelper workoutDatabaseHelper = WorkoutDatabaseHelper.getInstance(activity);
                workoutDatabaseHelper.deleteWorkout(workout.getWorkoutName());
                workoutProgram.remove(position);
                notifyDataSetChanged();
                Toast.makeText(activity, "Workout removed", Toast.LENGTH_LONG).show();
            }
        });

        title.setText(workout.getWorkoutName());
        set.setText("Set: " + workout.getSet());
        reps.setText("Rep: " + workout.getReps());
        thumbnail.setImageResource(Integer.valueOf(workout.getThumbnail()));

        return vi;
    }
}
