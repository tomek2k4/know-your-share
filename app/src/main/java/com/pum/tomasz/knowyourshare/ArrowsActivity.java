package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;


/**
 * @author Adil Soomro
 *
 */
public class ArrowsActivity extends Activity {
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.arrowspage);

        Log.d(TabSample.TAG,"Called onCreate in ArrowsActivity");
    }

    @Override
    public void onBackPressed() {
        this.getParent().onBackPressed();

        Log.d(TabSample.TAG,"Called Back in ArrowsActivity" );

    }



    @Override
    protected void onPause() {
        super.onPause();

        Log.d(TabSample.TAG, "Called onPause in ArrowsActivity");
    }
}