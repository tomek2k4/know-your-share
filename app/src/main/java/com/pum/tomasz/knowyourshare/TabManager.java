package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;

/**
 * Created by tomasz on 13.07.2015.
 */
public class TabManager implements View.OnClickListener{

    ImageButton homeImageButton;
    ImageButton productsImageButton;
    ImageButton settingsImageButton;
    Activity activity;

    public TabManager(Activity activity) {

        this.activity = activity;
        // Get buttons
        homeImageButton = (ImageButton) activity.findViewById(R.id.home_button);
        productsImageButton = (ImageButton) activity.findViewById(R.id.products_button);
        settingsImageButton = (ImageButton) activity.findViewById(R.id.settings_button);

        //set click listeners
        homeImageButton.setOnClickListener(this);
        productsImageButton.setOnClickListener(this);
        settingsImageButton.setOnClickListener(this);

    }

    public static void blockTab(ImageButton imageButton){
        imageButton.setClickable(false);
        imageButton.setPressed(true);
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.home_button){
            Log.d(Utilities.TAG,"Clicked on home button");
            Intent homeIntent = new Intent(activity.getApplicationContext(),HomeActivity.class);
            activity.startActivity(homeIntent);
        }else if(v.getId() == R.id.products_button){
            Log.d(Utilities.TAG,"Clicked on products button");
            Intent productsIntent = new Intent(activity.getApplicationContext(),ProductsActivity.class);
            activity.startActivity(productsIntent);
        }else if(v.getId() == R.id.settings_button){
            Log.d(Utilities.TAG,"Clicked on settings button");
            Intent settingsIntent = new Intent(activity.getApplicationContext(),SettingsActivity.class);
            activity.startActivity(settingsIntent);
        }
    }
}
