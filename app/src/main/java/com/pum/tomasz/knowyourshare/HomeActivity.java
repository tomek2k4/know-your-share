package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;


public class HomeActivity extends Activity {

    private ImageButton homeImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initializeTab();
    }

    private void initializeTab(){
        homeImageButton = (ImageButton)findViewById(R.id.home_button);
        homeImageButton.setPressed(true);
    }


    @Override
    protected void onResume() {
        super.onResume();
        initializeTab();
    }
}
