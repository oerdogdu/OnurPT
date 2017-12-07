package com.eon.atoi.onurpt;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * Created by Atoi on 2.12.2017.
 */

@SuppressWarnings("deprecation")
public class ContactFragment extends Fragment {
    private String title;
    private int page;

    public static ContactFragment newInstance(int page, String title)
    {
        ContactFragment contactFragment = new ContactFragment();
        Bundle args = new Bundle();
        args.putInt("someInt", page);
        args.putString("someTitle", title);
        contactFragment.setArguments(args);
        return contactFragment;
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
        View view = inflater.inflate(R.layout.contact_fragment, container, false);
        return view;
    }
}
