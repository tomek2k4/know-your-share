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
        Log.d(TabSample.TAG, "Called onCreate in OptionsActivity");
    }

    @Override
    public void onBackPressed() {
        Log.d(TabSample.TAG,"Called Back in options" );
        this.getParent().onBackPressed();
    }


    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TabSample.TAG, "Called onPause in OptionsActivity");
    }

}
