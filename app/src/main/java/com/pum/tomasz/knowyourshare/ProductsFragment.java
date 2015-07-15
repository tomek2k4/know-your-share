package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/**
 * Created by tomasz on 14.07.2015.
 */
public class ProductsFragment extends ListFragment {

    private Activity activity = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  super.onCreateView(inflater,container,savedInstanceState);
        Log.d(Utilities.TAG,"ProductsFragment onCreateView called");

        return view;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setListAdapter(new ArrayAdapter<String>(activity,
                android.R.layout.simple_list_item_activated_1,
                android.R.id.text1, getResources().getStringArray(
                R.array.city_names)));

        Log.d(Utilities.TAG, "ProductsFragment onCreate called");


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.d(Utilities.TAG, "ProductsFragment onAttach called");

        this.activity = activity;
    }
}
