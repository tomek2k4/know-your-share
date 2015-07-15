package com.pum.tomasz.knowyourshare.tabs;


import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.pum.tomasz.knowyourshare.HomeFragment;
import com.pum.tomasz.knowyourshare.MainActivity;
import com.pum.tomasz.knowyourshare.ProductsFragment;
import com.pum.tomasz.knowyourshare.R;
import com.pum.tomasz.knowyourshare.SettingsFragment;
import com.pum.tomasz.knowyourshare.Utilities;

import java.util.HashMap;


/**
 * Created by tomasz on 13.07.2015.
 */
public class TabManager implements View.OnClickListener{

    private HashMap<Integer, TabInfo> mapTabInfo = new HashMap<Integer, TabInfo>();
    private TabInfo mLastTab = null;

    private ImageButton  homeImageButton;
    private ImageButton  productsImageButton;
    private ImageButton  settingsImageButton;
    private MainActivity activity;

    public TabManager(MainActivity activity) {
        this.activity = activity;


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

        addTab( activity,(tabInfo = new TabInfo(R.id.products_button,TabsTagEnum.PRODUCTS.name(), ProductsFragment.class,args)) );
        this.mapTabInfo.put(tabInfo.getViewId(), tabInfo);

        addTab( activity,(tabInfo = new TabInfo(R.id.settings_button,TabsTagEnum.SETTINGS.name(), SettingsFragment.class,args)) );
        this.mapTabInfo.put(tabInfo.getViewId(), tabInfo);

        // Default to first tab
        this.onClick(homeImageButton);

    }

    private static void addTab(MainActivity activity,TabInfo tabInfo) {
        // Check to see if we already have a fragment for this tab, probably
        // from a previously saved state.  If so, deactivate it, because our
        // initial state is that a tab isn't shown.
        tabInfo.setFragment(activity.getFragmentManager().findFragmentByTag(tabInfo.getTag()));

        if (tabInfo.getFragment() != null && !tabInfo.getFragment().isDetached()) {
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            ft.detach(tabInfo.getFragment());
            ft.commit();
            activity.getFragmentManager().executePendingTransactions();
        }
    }

    @Override
    public void onClick(View v) {

        TabInfo newTab = this.mapTabInfo.get(v.getId());
        if (mLastTab != newTab) {
            blockTab((ImageButton) v);
            Log.d(Utilities.TAG, "Changed to " + newTab.getTag() + " tab");
            FragmentTransaction ft = activity.getFragmentManager().beginTransaction();
            if (mLastTab != null) {
                unblockTab((ImageButton) activity.findViewById(mLastTab.getViewId()));
                Log.d(Utilities.TAG, "Previously was on " + mLastTab.getTag() + " tab");
                if (mLastTab.getFragment() != null) {
                    ft.detach(mLastTab.getFragment());
                }
            }
            if (newTab != null) {
                if (newTab.getFragment() == null) {
                    newTab.setFragment(Fragment.instantiate(activity,
                            newTab.getClss().getName(), newTab.getArgs()));
                    ft.add(R.id.main_panel, newTab.getFragment(), newTab.getTag());
                } else {
                    ft.attach(newTab.getFragment());
                }
            }
            mLastTab = newTab;
            ft.commit();
            activity.getFragmentManager().executePendingTransactions();
        }
    }


    public String getCurrentTabTag(){

        return mLastTab.getTag();
    }

    public void setCurrentTabByTag(String tag){
        mLastTab.setTag(tag);
    }

    private void unblockTab(ImageButton imageButton) {
        imageButton.setSelected(false);
    }


    private static void blockTab(ImageButton imageButton){
        imageButton.setSelected(true);
    }

}
