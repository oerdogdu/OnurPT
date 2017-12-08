package com.eon.atoi.onurpt;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

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

    public View getView(int position, View convertView, ViewGroup parent) {
        View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.program_list_row, null);

        TextView title = (TextView)vi.findViewById(R.id.program_title);
        TextView description = (TextView)vi.findViewById(R.id.program_description);
        ImageView thumbnail = (ImageView) vi.findViewById(R.id.program_image);
        TextView set = (TextView)vi.findViewById(R.id.program_set);
        TextView reps = (TextView)vi.findViewById(R.id.program_reps);

        Workout workout = getItem(position);

        title.setText(workout.getWorkoutName());
        description.setText(workout.getDescription());
        set.setText(workout.getSet());
        reps.setText(workout.getReps());
        thumbnail.setImageResource(Integer.valueOf(workout.getThumbnail()));

        thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return vi;
    }
}
