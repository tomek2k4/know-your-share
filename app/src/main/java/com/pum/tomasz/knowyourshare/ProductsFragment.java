package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.pum.tomasz.knowyourshare.data.Product;
import com.pum.tomasz.knowyourshare.data.ProductsListConfigurationEnum;
import com.pum.tomasz.knowyourshare.recyclerview.MyRecyclerViewAdapter;

import java.util.LinkedList;
import java.util.List;



/**
 * Created by Tomasz Maslon on 14.07.2015.
 */
public class ProductsFragment extends Fragment implements View.OnClickListener {

    private Activity activity = null;

    List<Product> data = new LinkedList<Product>();
    private ProductsListConfigurationEnum productsListConfiguration;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View addButton;

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
        View rootView = (FrameLayout) inflater.inflate(R.layout.fragment_products, container, false);

        initializeProductsLayoutComponents(rootView);

        return rootView;

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Utilities.TAG, "ProductsFragment onCreate called");

        Bundle arguments = getArguments();
        if (arguments != null) {
            //Set initial fields values
            productsListConfiguration = ProductsListConfigurationEnum.valueOf(
                    arguments.getString(BundleKeyEnum.PRODUCTS_LIST_CONFIGURATION.name(),
                            ProductsListConfigurationEnum.ALL_PRODUCTS.name()));
        } else {
            productsListConfiguration = ProductsListConfigurationEnum.ALL_PRODUCTS;
        }

        setCurrentData(productsListConfiguration);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(Utilities.TAG, "ProductsFragment onAttach called");
    }

    @Override
    public void onClick(View v) {
        Log.d(Utilities.TAG,"Clicked on add button");

        Intent i = new Intent(getActivity(), ProductAddActivity.class);
        startActivity(i);
    }

    public void updateListConfiguration(ProductsListConfigurationEnum plc){

        //Check if plc is different than current configuration
        if (plc != productsListConfiguration) {

            productsListConfiguration = plc;
            setCurrentData(plc);
            //create new Recycler Adapter
            mAdapter = new MyRecyclerViewAdapter(data);
            mRecyclerView.swapAdapter(mAdapter,false);
        }
    }

    private void setCurrentData(ProductsListConfigurationEnum productsListConfiguration) {
        data = ((MainActivity) getActivity()).getProductDatabaseFacade().getList(productsListConfiguration);
    }

    private void initializeProductsLayoutComponents(View rootView) {

        // Make this {@link Fragment} listen for changes in both FABs.
        addButton = (View) rootView.findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        //InitializelList
        initializeRecyclerView(rootView);

    }

    private void initializeRecyclerView(View rootView) {

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        // specify an adapter
        mAdapter = new MyRecyclerViewAdapter(data);
        mRecyclerView.setAdapter(mAdapter);

    }

    public ProductsListConfigurationEnum getCurrentListConfiguratio() {
        return productsListConfiguration;
    }
}