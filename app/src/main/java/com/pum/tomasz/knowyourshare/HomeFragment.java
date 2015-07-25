package com.pum.tomasz.knowyourshare;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by tomasz on 15.07.2015.
 */
public class HomeFragment extends Fragment {

    public static final String TEXT_TITLE_ID = "city_id";
    private String homeTextTitle;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
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

        View rootView = (LinearLayout)inflater.inflate(R.layout.fragment_home, container, false);

        if (homeTextTitle != null) {
            TextView homeTextView = (TextView) rootView.findViewById(R.id.home_text_title);
            homeTextView.setText(homeTextTitle);
        }

        return rootView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Utilities.TAG, "HomeFragment on Create called");

        Bundle arguments = getArguments();
        if (arguments != null)
        {
            if (arguments.containsKey(TEXT_TITLE_ID)) {
                homeTextTitle = getArguments().getString(TEXT_TITLE_ID);
                Log.d(Utilities.TAG,"There is new argument");
            }
        } else {
            // no arguments supplied...
        }

    }

    public void update(String newText){
        TextView homeTextView = (TextView) getView().findViewById(R.id.home_text_title);
        homeTextView.setText(newText);
    }
}
