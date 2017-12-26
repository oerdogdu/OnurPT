package com.eon.atoi.onurpt.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.eon.atoi.onurpt.fragments.CalendarFragment;
import com.eon.atoi.onurpt.fragments.ContactFragment;
import com.eon.atoi.onurpt.fragments.FaqFragment;
import com.eon.atoi.onurpt.fragments.ProfileFragment;
import com.eon.atoi.onurpt.fragments.WorkoutListFragment;

/**
 * Created by Atoi on 2.12.2017.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private static int NUM_ITEMS = 5;

    public MyPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    // Returns total number of pages
    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    // Returns the fragment to display for that page
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return WorkoutListFragment.newInstance(0, "Workouts");
            case 1:
                return ProfileFragment.newInstance(1, "Profile");
            case 2:
                return CalendarFragment.newInstance(2, "Calendar");
            case 3:
                return ContactFragment.newInstance(3, "Contact");
            case 4:
                return FaqFragment.newInstance(4, "FAQ");
            default:
                return null;
        }
    }
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Workouts";
            case 1:
                return "Profile";
            case 2:
                return "Calendar";
            case 3:
                return "Contact";
            case 4:
                return "FAQ";
            default:
                return null;
        }
    }

}

