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

public class MainActivity extends FragmentActivity {


    private TabManager tabManager = null;
    private PagerAdapter mPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(Utilities.TAG, "MainActivity on Create called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabManager = new TabManager(this);
        tabManager.initialiseTabManager(savedInstanceState);

        //initialsie the pager
        this.initialisePaging();

    }


    private void initialisePaging() {
        List<TabInfo> tabInfoList = tabManager.getTabInfoList();
//        List<Fragment> fragments = new Vector<Fragment>();
//        fragments.add(Fragment.instantiate(this, HomeFragment.class.getName()));
//        fragments.add(Fragment.instantiate(this, ProductsFragment.class.getName()));
//        fragments.add(Fragment.instantiate(this, SettingsFragment.class.getName()));
        this.mPagerAdapter  = new MyPagerAdapter(super.getSupportFragmentManager(),this, tabInfoList);

        //
        ViewPager pager = (ViewPager)super.findViewById(R.id.main_panel);
        pager.setAdapter(this.mPagerAdapter);

    }



}
