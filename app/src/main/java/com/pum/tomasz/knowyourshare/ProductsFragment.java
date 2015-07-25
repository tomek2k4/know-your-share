package com.pum.tomasz.knowyourshare;


import android.annotation.TargetApi;
import android.app.Activity;

import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by tomasz on 14.07.2015.
 */
public class ProductsFragment extends Fragment{ //extends ListFragment {

    private Activity activity = null;
    private ListView listView = null;
    private Button addButton = null;
    private TextView textView = null;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (container == null) {
            // We have different layouts, and in one of them this
            // fragment's containing frame doesn't exist.  The fragment
            // may still be created from its saved state, but there is
            // no reason to try to create its view hierarchy because it
            // won't be displayed.  Note this is not needed -- we could
            // just run the code below, where we would create and return
            // the view hierarchy; it would just never be used.
            return null;
        }
        View rootView = (FrameLayout)inflater.inflate(R.layout.fragment_products, container, false);


        TextView productsTextView = (TextView) rootView.findViewById(R.id.text_on_products);
        productsTextView.setText("nowy tekst dla produktow");

        addButton = (Button) rootView.findViewById(R.id.add_button);

        return rootView;

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        setListAdapter(new ArrayAdapter<String>(activity,
//                R.layout.products_list_textview, getResources().getStringArray(
//                R.array.city_names)));


        if(addButton!=null){
          addButton.setText("-");
        }

        Log.d(Utilities.TAG, "ProductsFragment onCreate called");


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.d(Utilities.TAG, "ProductsFragment onAttach called");


    }
}
