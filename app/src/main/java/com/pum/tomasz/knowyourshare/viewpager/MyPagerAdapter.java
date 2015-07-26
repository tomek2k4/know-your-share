package com.pum.tomasz.knowyourshare.viewpager;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.pum.tomasz.knowyourshare.HomeFragment;
import com.pum.tomasz.knowyourshare.MainActivity;
import com.pum.tomasz.knowyourshare.Utilities;
import com.pum.tomasz.knowyourshare.tabs.TabInfo;

import java.util.List;

/**
 * Created by tmaslon on 2015-07-16.
 */
public class MyPagerAdapter extends FragmentPagerAdapter implements ViewPager.OnPageChangeListener{

    private List<TabInfo> tabInfoList;
    private MainActivity activity;
    private ViewPager mViewPager = null;
    private ViewChangeListener viewChangeListener = null;

    public void removeAllFragments() {

        if ( tabInfoList != null ) {
            for ( TabInfo tabInfo : tabInfoList ) {
                Fragment fragment = tabInfo.getFragment();
                if(fragment!=null){
                    activity.getSupportFragmentManager().beginTransaction().remove(fragment).commit();
                }
            }
            tabInfoList.clear();
            notifyDataSetChanged();
        }

    }

    public interface ViewChangeListener {
        public void onViewSelected(int position);
    }

    public MyPagerAdapter(MainActivity activity, ViewPager viewPager, List<TabInfo> tabInfoList) {
        super(activity.getSupportFragmentManager());
        Log.d(Utilities.TAG, "MyPagerAdapter constr called");
        this.activity = activity;
        this.mViewPager = viewPager;
        this.tabInfoList = tabInfoList;


        mViewPager.setAdapter(this);

        viewChangeListener = (ViewChangeListener) activity;
        mViewPager.setOnPageChangeListener(this);

    }

    @Override
    public Fragment getItem(int position) {
        Log.d(Utilities.TAG, "MyPagerAdapter getItem called with position: " + new Integer(position).toString());
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
        //Log.d(Utilities.TAG,"MyPagerAdapter getCount called");
        return this.tabInfoList.size();
    }

    public ViewPager getViewPager() {
        return mViewPager;
    }

    public void setViewPager(ViewPager mViewPager) {
        this.mViewPager = mViewPager;
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(Utilities.TAG,"Scrolled to view with id: "+new Integer(position).toString());


        viewChangeListener.onViewSelected(position);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }


    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
