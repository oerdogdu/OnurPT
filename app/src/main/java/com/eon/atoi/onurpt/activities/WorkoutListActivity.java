package com.eon.atoi.onurpt.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.adapters.WorkoutListAdapter;

import java.util.HashMap;

/**
 * Created by Atoi on 2.12.2017.
 */

public class WorkoutListActivity extends Activity {

    private static HashMap<String, Integer> data;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.workoutlist_activity);
        data = new HashMap<>();
        prepareList();

        ListView workoutList = (ListView)findViewById(R.id.workoutList);

        WorkoutListAdapter workoutListAdapter = new WorkoutListAdapter(this, data);
        workoutList.setAdapter(workoutListAdapter);
        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getBaseContext(), CertainExerciseActivity.class);
                Bundle bundle = new Bundle();

                switch (position) {
                    case 0:
                        bundle.putString("type", "abs");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(getBaseContext(), CertainExerciseActivity.class);
                        bundle.putString("type", "shoulder");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 2:
                        intent = new Intent(getBaseContext(), CertainExerciseActivity.class);
                        bundle.putString("type", "leg");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 3:
                        intent = new Intent(getBaseContext(), CertainExerciseActivity.class);
                        bundle.putString("type", "cardio");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 4:
                        intent = new Intent(getBaseContext(), CertainExerciseActivity.class);
                        bundle.putString("type", "biceps");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    case 5:
                        intent = new Intent(getBaseContext(), CertainExerciseActivity.class);
                        bundle.putString("type", "chest");
                        intent.putExtras(bundle);
                        startActivity(intent);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    private void prepareList() {
        data.put("Shoulder", R.drawable.shoulder_icon);
        data.put("Chest", R.drawable.chest_icon);
        data.put("Biceps - Triceps", R.drawable.biceps_icon);
        data.put("Cardio", R.drawable.cardio_icon);
        data.put("Abs", R.drawable.abs_icon);
        data.put("Leg", R.drawable.leg_icon);
    }
}
