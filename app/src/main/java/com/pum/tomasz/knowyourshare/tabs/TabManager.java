package com.pum.tomasz.knowyourshare.tabs;


import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.pum.tomasz.knowyourshare.HomeFragment;
import com.pum.tomasz.knowyourshare.MainActivity;
import com.pum.tomasz.knowyourshare.ProductsFragment;
import com.pum.tomasz.knowyourshare.R;
import com.pum.tomasz.knowyourshare.SettingsFragment;
import com.pum.tomasz.knowyourshare.Utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;
import java.util.Vector;


/**
 * Created by tomasz on 13.07.2015.
 */
public class TabManager implements View.OnClickListener{

    private HashMap<Integer, TabInfo> mapTabInfo = new HashMap<Integer, TabInfo>();
    private List<TabInfo> tabInfoList = new Vector<TabInfo>();

    private TabInfo mLastTab = null;
    private Stack<Integer> fragmentTabsStack = null;
    private TabChangeListener tabChangeListener;

    private ImageButton  homeImageButton;
    private ImageButton  productsImageButton;
    private ImageButton  settingsImageButton;
    private MainActivity activity;

    public interface TabChangeListener {
        public void onTabSelected(int position,boolean isStackTraced);
    }

    public TabManager(MainActivity activity) {
        this.activity = activity;
        tabChangeListener = (TabChangeListener) activity;
        //Create Fragments Stack push when new Fragment created, pop when back selected
        fragmentTabsStack = new Stack<>();
    }

    public void initialiseTabManager(Bundle args) {

        homeImageButton = (ImageButton) activity.findViewById(R.id.home_button);
        productsImageButton = (ImageButton) activity.findViewById(R.id.products_button);
        settingsImageButton = (ImageButton) activity.findViewById(R.id.settings_button);

        //set click listeners
        homeImageButton.setOnClickListener(this);
        productsImageButton.setOnClickListener(this);
        settingsImageButton.setOnClickListener(this);

        TabInfo tabInfo = null;

        addTab(activity, (tabInfo = new TabInfo(R.id.home_button, TabsTagEnum.HOME.name(), HomeFragment.class, args)));
        this.mapTabInfo.put(tabInfo.getViewId(), tabInfo);
        this.tabInfoList.add(tabInfo);

        addTab(activity, (tabInfo = new TabInfo(R.id.products_button, TabsTagEnum.PRODUCTS.name(), ProductsFragment.class, args)));
        this.mapTabInfo.put(tabInfo.getViewId(), tabInfo);
        this.tabInfoList.add(tabInfo);

        addTab(activity, (tabInfo = new TabInfo(R.id.settings_button, TabsTagEnum.SETTINGS.name(), SettingsFragment.class, args)));
        this.mapTabInfo.put(tabInfo.getViewId(), tabInfo);
        this.tabInfoList.add(tabInfo);

        // Default to first tab
        this.onClick(homeImageButton);

    }

    private static void addTab(MainActivity activity,TabInfo tabInfo) {
        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo.setFragment(activity.getSupportFragmentManager().findFragmentByTag(tabInfo.getTag()));

        if (tabInfo.getFragment() != null && !tabInfo.getFragment().isDetached()) {
            FragmentTransaction ft = activity.getSupportFragmentManager().beginTransaction();
            ft.detach(tabInfo.getFragment());
            ft.commit();
            activity.getFragmentManager().executePendingTransactions();
        }
    }

    @Override
    public void onClick(View v) {
        TabInfo newTab = this.mapTabInfo.get(v.getId());
        if (mLastTab != newTab) {

            // Set graphical element of tab
            blockTab((ImageButton) v);
            Log.d(Utilities.TAG, "Changed to " + newTab.getTag() + " tab");
            if (mLastTab != null) {
                unblockTab((ImageButton) activity.findViewById(mLastTab.getViewId()));
                Log.d(Utilities.TAG, "Previously was on " + mLastTab.getTag() + " tab");
            }

            if(tabInfoList.indexOf(newTab)==TabsTagEnum.HOME.getValue())
            {
                fragmentTabsStack.clear();
            }

            // Update current Tab
            mLastTab = newTab;

            //Inform MainActivity about change of tab
            for (TabInfo tabInfo:tabInfoList){
                if(tabInfo == newTab){
                    blockTab((ImageButton) v);
                    tabChangeListener.onTabSelected(tabInfoList.indexOf(tabInfo),true);
                }
            }



        }
    }


    public List<TabInfo> getTabInfoList() {
        return tabInfoList;
    }

    public int getCurrentTabId() {
        return mLastTab.getViewId();
    }

    public String getCurrentTabTag(){
        return mLastTab.getTag();
    }

    public int getLastTabPosition(){
        int lastTabPosition;

        if(mLastTab!=null){
            lastTabPosition = tabInfoList.indexOf(mLastTab);
        }else{
            lastTabPosition = TabsTagEnum.HOME.getValue();
        }
        return lastTabPosition;
    }


    public void setCurrentTabById(int id){
        this.onClick(activity.findViewById(id));
    }

    public Stack<Integer> getFragmentTabsStack() {
        return fragmentTabsStack;
    }

    private void unblockTab(ImageButton imageButton) {
        imageButton.setSelected(false);
    }

    private static void blockTab(ImageButton imageButton){
        imageButton.setSelected(true);
    }


}
