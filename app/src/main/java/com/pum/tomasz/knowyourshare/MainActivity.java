package com.pum.tomasz.knowyourshare;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;


import com.pum.tomasz.knowyourshare.tabs.TabInfo;
import com.pum.tomasz.knowyourshare.tabs.TabManager;
import com.pum.tomasz.knowyourshare.viewpager.MyPagerAdapter;

import java.util.List;
import java.util.Vector;

public class MainActivity extends FragmentActivity implements TabManager.TabChangeListener {


    private TabManager tabManager = null;
    private PagerAdapter mPagerAdapter;
    private ViewPager mViewPager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(Utilities.TAG, "MainActivity on Create called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabManager = new TabManager(this);
        tabManager.initialiseTabManager(savedInstanceState);
        if (savedInstanceState != null) {
            tabManager.setCurrentTabById(savedInstanceState.getInt("tab")); //set the tab as per the saved state
        }

        //initialsie the pager
        this.initialisePaging();

    }


    private void initialisePaging() {
        List<TabInfo> tabInfoList = tabManager.getTabInfoList();
        this.mPagerAdapter  = new MyPagerAdapter(super.getSupportFragmentManager(),this, tabInfoList);

        mViewPager = (ViewPager)super.findViewById(R.id.main_panel);
        mViewPager.setAdapter(this.mPagerAdapter);

    }


    @Override
    public void onTabSelected(int position) {
        if(mViewPager!=null){
            Log.d(Utilities.TAG,"Pressed tab with index: "+new Integer(position).toString());
            mViewPager.setCurrentItem(position, true);
        }
    }


    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("tab", tabManager.getCurrentTabId()); //save the tab selected
        super.onSaveInstanceState(outState);
    }

}
