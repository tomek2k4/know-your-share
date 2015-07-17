package com.pum.tomasz.knowyourshare.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

import com.pum.tomasz.knowyourshare.MainActivity;
import com.pum.tomasz.knowyourshare.Utilities;
import com.pum.tomasz.knowyourshare.tabs.TabInfo;

import java.util.List;

/**
 * Created by tmaslon on 2015-07-16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter {

    private List<TabInfo> tabInfoList;
    private MainActivity activity;

    public MyPagerAdapter(FragmentManager fm,MainActivity activity, List<TabInfo> tabInfoList) {
        super(fm);
        Log.d(Utilities.TAG, "MyPagerAdapter constr called");
        this.tabInfoList = tabInfoList;
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        Log.d(Utilities.TAG,"MyPagerAdapter getItem called with position: " + new Integer(position).toString());

        Fragment fragment = null;

        TabInfo tabInfo = tabInfoList.get(position);
        if (tabInfo.getFragment() == null) {
            fragment = Fragment.instantiate(activity,
                    tabInfo.getClss().getName(), tabInfo.getArgs());
            tabInfo.setFragment(fragment);
        } else {
            fragment = tabInfo.getFragment();
        }

        return fragment;
    }

    @Override
    public int getCount() {
        Log.d(Utilities.TAG,"MyPagerAdapter getCount called");
        return this.tabInfoList.size();
    }
}
