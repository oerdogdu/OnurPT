package com.eon.atoi.onurpt.activities;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

import com.eon.atoi.onurpt.R;
import com.eon.atoi.onurpt.fragments.ContactFragment;
import com.eon.atoi.onurpt.fragments.MainFragment;
import com.eon.atoi.onurpt.fragments.ProfileFragment;

public class MainActivity extends FragmentActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,   WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_main:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.details_fragment, MainFragment.newInstance(), "mainFragment")
                                .commit();
                        break;

                    case R.id.me:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.details_fragment, ProfileFragment.newInstance(), "profileFragment")
                                .commit();
                        break;

                    case R.id.contact:
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.details_fragment, ContactFragment.newInstance(), "contactFragment")
                                .commit();
                        break;
                }
                return true;
            }
        });
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.details_fragment, MainFragment.newInstance(), "mainFragment")
                .commit();
    }
}
