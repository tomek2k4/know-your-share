package com.pum.tomasz.knowyourshare.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.pum.tomasz.knowyourshare.Utilities;

import java.util.List;

/**
 * Created by tmaslon on 2015-07-16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        Log.d(Utilities.TAG, "MyPagerAdapter constr called");
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(Utilities.TAG,"MyPagerAdapter getItem called with position: " + new Integer(position).toString());
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        Log.d(Utilities.TAG,"MyPagerAdapter getCount called");
        return this.fragments.size();
    }
}
