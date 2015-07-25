package com.pum.tomasz.knowyourshare;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;


import com.pum.tomasz.knowyourshare.tabs.TabInfo;
import com.pum.tomasz.knowyourshare.tabs.TabManager;
import com.pum.tomasz.knowyourshare.viewpager.MyPagerAdapter;


import java.util.List;


public class MainActivity extends FragmentActivity implements TabManager.TabChangeListener,
        MyPagerAdapter.ViewChangeListener {


    private TabManager tabManager = null;
    private PagerAdapter mPagerAdapter = null;

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
        mPagerAdapter  = new MyPagerAdapter(this,(ViewPager) super.findViewById(R.id.main_panel),tabInfoList);

        //((MyPagerAdapter)mPagerAdapter).getViewPager().setAdapter(this.mPagerAdapter);
    }


    // Event that comes form TabManager, ViewPager needs to be updated
    @Override
    public void onTabSelected(int position) {
        if(mPagerAdapter!=null){
            Log.d(Utilities.TAG, "Pressed tab with index: " + new Integer(position).toString());
            ((MyPagerAdapter)mPagerAdapter).getViewPager().setCurrentItem(position, true);

            if(position==0)
            {
                Log.d(Utilities.TAG, "Clicked on tab 0, update home fragment");
                TabInfo tabInfo = tabManager.getTabInfoList().get(position);
                ((HomeFragment)tabInfo.getFragment()).update("new text");
            }
        }
    }

    // Event that comes form ViewPagerAdapter, TabManager needs to be updated
    @Override
    public void onViewSelected(int position) {

        // Check current tab manager last tab to prevent over lapping of events
        if(tabManager.getCurrentTabId() != tabManager.getTabInfoList().get(position).getViewId()){
            tabManager.setCurrentTabById(tabManager.getTabInfoList().get(position).getViewId());
        }
    }


    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("tab", tabManager.getCurrentTabId()); //save the tab selected
        super.onSaveInstanceState(outState);
    }


}
