package com.eon.atoi.onurpt.adapters;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.POJOs.Workout;
import com.eon.atoi.onurpt.utils.WorkoutDatabaseHelper;

import java.util.List;

/**
 * Created by Atoi on 4.12.2017.
 */

public class CertainListAdapter extends RecyclerView.Adapter<CertainListAdapter.MyViewHolder> implements PopupMenu.OnMenuItemClickListener {

    private Context mContext;
    private List<Workout> absWorkoutList;
    private Workout workoutProgram = null;

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_addworkout:
                showDialog();
                return true;
            default:
                return false;
        }
    }

    private void showDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.workout_adding);
        dialog.setTitle("Adding to Program");

        Button dialogOk = (Button) dialog.findViewById(R.id.btn_ok);
        Button dialogCancel = (Button) dialog.findViewById(R.id.btn_cancel);

        final EditText etSet = (EditText)dialog.findViewById(R.id.input_set);
        final EditText etReps = (EditText)dialog.findViewById(R.id.input_reps);

        dialogCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialogOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String set = etSet.getText().toString();
                String reps = etReps.getText().toString();

                if((set != null && !set.isEmpty()) && (reps != null && !reps.isEmpty()))
                {
                    if(workoutProgram != null){
                        workoutProgram.setSet(set);
                        workoutProgram.setReps(reps);
                        WorkoutDatabaseHelper workoutDatabaseHelper = WorkoutDatabaseHelper.getInstance(mContext);
                        workoutDatabaseHelper.addWorkout(workoutProgram);
                        //Toast.makeText(mContext, "Workout Added", Toast.LENGTH_SHORT);


                        List<Workout> w = workoutDatabaseHelper.getAllWorkouts();
                        for (Workout workout : w) {
                            Log.d("elements", workout.getWorkoutName());
                        }
                    }
                    else {
                        Log.d("nullcheck", "null");
                    }
                }
                else
                {
                    Toast.makeText(mContext, "Fill in the areas", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog.show();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title, description;
        public ImageView thumbnail, plusSign;

        public MyViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            thumbnail = (ImageView) view.findViewById(R.id.thumbnail);
            plusSign = (ImageView) view.findViewById(R.id.plus_item);
        }
    }

    public CertainListAdapter(Context mContext, List<Workout> absWorkoutList) {
        this.mContext = mContext;
        this.absWorkoutList = absWorkoutList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.abs_exercise_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        Workout workout = absWorkoutList.get(position);
        holder.title.setText(workout.getWorkoutName());
        holder.thumbnail.setImageResource(Integer.valueOf(workout.getThumbnail()));

        holder.plusSign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddPopup(v);
                workoutProgram = absWorkoutList.get(holder.getAdapterPosition());
            }
        });

        holder.thumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.exercise_detail);
                dialog.setTitle("Exercise Detail");

                Button dialogButton = (Button) dialog.findViewById(R.id.btn_ok);
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }

    public void showAddPopup(View v) {
        PopupMenu popup = new PopupMenu(mContext, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.addworkout, popup.getMenu());
        popup.show();
    }

        @Override
        public int getItemCount () {
            return absWorkoutList.size();
        }
}

