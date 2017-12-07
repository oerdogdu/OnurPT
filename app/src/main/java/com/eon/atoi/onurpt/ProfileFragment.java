package com.eon.atoi.onurpt;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.vansuita.materialabout.builder.AboutBuilder;
import com.vansuita.materialabout.views.AboutView;

/**
 * Created by Atoi on 2.12.2017.
 */

public class ProfileFragment extends Fragment {

    private String title;
    private int page;

    public static ProfileFragment newInstance(int page, String title)
    {
        ProfileFragment profileFragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        profileFragment.setArguments(args);
        return profileFragment;
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
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        TextView programTv = (TextView)view.findViewById(R.id.programTv);
        programTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProgramActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

}
