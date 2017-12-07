package com.eon.atoi.onurpt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Atoi on 2.12.2017.
 */

public class WorkoutListFragment extends Fragment {

    private String title;
    private int page;
    private static HashMap<String, Integer> data;

    public static WorkoutListFragment newInstance(int page, String title)
    {
        WorkoutListFragment workoutListFragment = new WorkoutListFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        workoutListFragment.setArguments(args);
        return workoutListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        page = getArguments().getInt("someInt", 0);
        title = getArguments().getString("someTitle");
        data = new HashMap<>();
    }

    private void prepareList() {
        data.put("Shoulder", R.drawable.shoulder_exercise);
        data.put("Chest", R.drawable.chest_exercise);
        data.put("Biceps - Triceps - Arm", R.drawable.shoulder_exercise);
        data.put("Cardio", R.drawable.chest_exercise);
        data.put("Abs", R.drawable.chest_exercise);
        data.put("Leg", R.drawable.shoulder_exercise);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workoutlist_fragment, container, false);
        ListView workoutList = (ListView) view.findViewById(R.id.workoutList);
        prepareList();
        WorkoutListAdapter workoutListAdapter = new WorkoutListAdapter(getActivity(), data);
        workoutList.setAdapter(workoutListAdapter);
        workoutList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position)
                {
                    case 0:
                        Intent intent = new Intent(getActivity(), AbsExerciseActivity.class);
                        startActivity(intent);
                        break;

                    default:
                        break;
                }
            }
        });
        return view;
    }

}
