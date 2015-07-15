package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;

public class MainActivity extends Activity {


    private class TabInfo {
        private String tag;
        private Class<?> clss;
        private Bundle args;
        private Fragment fragment;
        TabInfo(String tag, Class<?> clazz, Bundle args) {
            this.tag = tag;
            this.clss = clazz;
            this.args = args;
        }

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialiseTabHost(savedInstanceState);



//        if (savedInstanceState != null) {
//            mTabHost.setCurrentTabByTag(savedInstanceState.getString("tab")); //set the tab as per the saved state
//        }
    }

    private void initialiseTabHost(Bundle savedInstanceState) {

        FragmentTransaction ft = getFragmentManager().beginTransaction();


        SettingsFragment fragment = new SettingsFragment();

        ft.attach(fragment);

        //fragment.setArguments(arguments);
        ft.add(R.id.main_panel, fragment);

        ft.commit();

    }


}
