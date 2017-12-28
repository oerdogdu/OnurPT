package com.eon.atoi.onurpt.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.activities.PhotosActivity;
import com.eon.atoi.onurpt.activities.ProgramActivity;

/**
 * Created by Atoi on 2.12.2017.
 */

public class ProfileFragment extends Fragment {

    public static ProfileFragment newInstance()
    {
        ProfileFragment profileFragment = new ProfileFragment();
        return profileFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView programTv = (TextView)view.findViewById(R.id.programTv);

        programTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProgramActivity.class);
                startActivity(intent);
            }
        });

        TextView photosTv = (TextView)view.findViewById(R.id.profile_photos);
        photosTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PhotosActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
