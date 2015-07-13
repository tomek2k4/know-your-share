package com.pum.tomasz.knowyourshare;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

/**
 * @author Adil Soomro
 *
 */
public class TabSample extends TabActivity {
	/** Called when the activity is first created. */

    public final static String TAG ="Tomek";

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

        Log.d(TAG, "Called on create on tabs");

        setTabs() ;
	}


    @Override
    public void onBackPressed() {
        // Called by children
        Log.d(TAG, "Back called");

        Log.d("Tomek", "Called from " + this.getParent().getLocalClassName());

        View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);




    }

	private void setTabs()
	{
		addTab("Home", R.drawable.tab_home, ArrowsActivity.class);
		addTab("Search", R.drawable.tab_search, OptionsActivity.class);
		addTab("Home", R.drawable.tab_home, ArrowsActivity.class);
	}
	
	private void addTab(String labelId, int drawableId, Class<?> c)
	{
		TabHost tabHost = getTabHost();
		Intent intent = new Intent(this, c);
		TabHost.TabSpec spec = tabHost.newTabSpec("tab" + labelId);	
		
		View tabIndicator = LayoutInflater.from(this).inflate(R.layout.tab_indicator, getTabWidget(), false);
		TextView title = (TextView) tabIndicator.findViewById(R.id.title);
		title.setText(labelId);
		ImageView icon = (ImageView) tabIndicator.findViewById(R.id.icon);
		icon.setImageResource(drawableId);
		
		spec.setIndicator(tabIndicator);
		spec.setContent(intent);
		tabHost.addTab(spec);
	}
}