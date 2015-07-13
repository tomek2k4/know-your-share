package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;


public class HomeActivity extends Activity {

    TabManager tabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Log.d(Utilities.TAG,"HomeActivity onCreate called");

        TabManager.blockTab((ImageButton) findViewById(R.id.home_button));
        tabManager = new TabManager(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        TabManager.blockTab((ImageButton) findViewById(R.id.home_button));

        Log.d(Utilities.TAG, "HomeActivity onResume called");
    }
}
