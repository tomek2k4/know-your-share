package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


/**
 * @author Adil Soomro
 *
 */
public class OptionsActivity extends Activity {
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.optionspage);
    }

    @Override
    public void onBackPressed() {
//        this.getParent().onBackPressed();

        Log.d(TabSample.TAG,"Called Back in options" );

    }
}
