package com.pum.tomasz.knowyourshare;


import android.graphics.drawable.Drawable;
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
public class HomeFragment extends Fragment implements View.OnClickListener{

    private int numberOfAllProducts = -1;
    private int numberOfTodayProducts = -1;
    private int numberOfFrequentlyBoughtProducts = -1;

    private OnHomeFragmentButtonClickListener homeFragmentButtonClickListener = null;

    interface OnHomeFragmentButtonClickListener{
        public void onHomeFragmentButtonClick(int id);
    }


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

        // Initialize All components when all initial fields values updated
        initializeHomeLayoutComponents(rootView);

        return rootView;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Utilities.TAG, "HomeFragment on Create called");
        homeFragmentButtonClickListener = (OnHomeFragmentButtonClickListener)getActivity();

        Bundle arguments = getArguments();
        if (arguments != null)
        {
            //Set initial fields values
            update(arguments);
        } else {
            // no arguments supplied...
        }

    }

    private void initializeHomeLayoutComponents(View rootView) {
        initializeLayoutButton(rootView.findViewById(R.id.all_products_button_layout),
                getResources().getDrawable(R.drawable.search_selected),getString(R.string.show_all_products_string) ,numberOfAllProducts);

        initializeLayoutButton(rootView.findViewById(R.id.today_products_button_layout),
                getResources().getDrawable(R.drawable.today_sign),getString(R.string.show_today_products_string) ,numberOfTodayProducts);

        initializeLayoutButton(rootView.findViewById(R.id.frequently_bought_products_button_layout),
                getResources().getDrawable(R.drawable.star_big_on),getString(R.string.show_frequently_bought_products_string),numberOfFrequentlyBoughtProducts);

        initializeLayoutButton(rootView.findViewById(R.id.add_product_home_button_layout),
                getResources().getDrawable(R.drawable.add_product_home),getString(R.string.add_new_product_string) ,-1);

        initializeLayoutButton(rootView.findViewById(R.id.test_action_mode_home_button_layout),
                getResources().getDrawable(R.drawable.add_product_home),getString(R.string.test_action_mode_button) ,-1);

    }


    private void initializeLayoutButton(View buttonLayout, Drawable drawable, String title, Integer elements){

        // Draw all button components
        ImageView homeImageLeft = (ImageView)buttonLayout.findViewById(R.id.image_home_button);
        homeImageLeft.setImageDrawable(drawable);


        TextView homeTextView = (TextView) buttonLayout.findViewById(R.id.name_home_button);
        homeTextView.setText(title);

        TextView homeElementsRight = (TextView) buttonLayout.findViewById(R.id.text_elements_home_button);

        if(elements == -1){
            homeElementsRight.setText("");
        }else{
            homeElementsRight.setText(elements.toString());
        }

        buttonLayout.setOnClickListener(this);

    }

    public void update(Bundle arguments){
        numberOfAllProducts = arguments.getInt(BundleKeyEnum.NUMBER_OF_PRODUCTS.name(),0);
        numberOfTodayProducts = arguments.getInt(BundleKeyEnum.NUMBER_OF_TODAY_PRODUCTS.name(),0);
        numberOfFrequentlyBoughtProducts = arguments.getInt(BundleKeyEnum.NUMBER_OF_FREQUENTLY_BOUGHT_PRODUCTS.name(),0);

        View view = getView();
        if(view!=null){
            initializeHomeLayoutComponents(view);
        }
    }

    @Override
    public void onClick(View v) {

        if(homeFragmentButtonClickListener!=null){
            homeFragmentButtonClickListener.onHomeFragmentButtonClick(v.getId());
        }

    }



}
