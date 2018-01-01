package com.eon.atoi.onurpt.fragments;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.activities.CalendarActivity;
import com.eon.atoi.onurpt.activities.FaqActivity;
import com.eon.atoi.onurpt.activities.FoodActivity;
import com.eon.atoi.onurpt.activities.MusicActivity;
import com.eon.atoi.onurpt.activities.WorkoutListActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Atoi on 27.12.2017.
 */

public class MainFragment extends Fragment
{
        public static MainFragment newInstance()
        {
            MainFragment mainFragment = new MainFragment();
            return mainFragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        CircleImageView buttonAppointment, buttonFood, buttonWorkout, buttonFAQ,
                    buttonMusic;

        buttonAppointment = (CircleImageView)view.findViewById(R.id.buttonAppointment);
        buttonFood = (CircleImageView)view.findViewById(R.id.buttonFood);
        buttonWorkout = (CircleImageView)view.findViewById(R.id.buttonWorkout);
        buttonFAQ = (CircleImageView)view.findViewById(R.id.buttonFAQ);
        buttonMusic = (CircleImageView)view.findViewById(R.id.buttonMusic);

        buttonFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FoodActivity.class);
                startActivity(intent);
            }
        });

        buttonWorkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WorkoutListActivity.class);
                startActivity(intent);
            }
        });

        buttonAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CalendarActivity.class);
                startActivity(intent);
            }
        });

        buttonFAQ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FaqActivity.class);
                startActivity(intent);
            }
        });

        buttonMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MusicActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
