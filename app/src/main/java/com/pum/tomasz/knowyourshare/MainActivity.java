package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.util.Log;

import com.pum.tomasz.knowyourshare.tabs.TabManager;

public class MainActivity extends Activity {


    TabManager tabManager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(Utilities.TAG,"MainActivity on Create called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabManager = new TabManager(this);

        tabManager.initialiseTabManager(savedInstanceState);

        if (savedInstanceState != null) {
            tabManager.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
        }
    }


    /** (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onSaveInstanceState(android.os.Bundle)
     */
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("tab", tabManager.getCurrentTabTag()); //save the tab selected
        super.onSaveInstanceState(outState);
    }


}
