package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;


public class ProductsActivity extends Activity {

    TabManager tabManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        Log.d(Utilities.TAG, "ProductsActivity onCreate called");

        TabManager.blockTab((ImageButton) findViewById(R.id.products_button));
        tabManager = new TabManager(this);
    }

    @Override
    protected void onResume() {
        super.onResume();

        TabManager.blockTab((ImageButton) findViewById(R.id.products_button));
        Log.d(Utilities.TAG, "ProductsActivity onResume called");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_products, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
