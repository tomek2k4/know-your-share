package com.pum.tomasz.knowyourshare;


import android.annotation.TargetApi;
import android.app.Activity;
import android.database.Cursor;
import android.graphics.Outline;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.pum.tomasz.knowyourshare.data.Product;
import com.pum.tomasz.knowyourshare.data.ProductsListConfigurationEnum;
import com.pum.tomasz.knowyourshare.floatingactionbutton.MyFloatingActionButton;
import com.pum.tomasz.knowyourshare.recyclerview.MyRecyclerViewAdapter;

import java.util.LinkedList;
import java.util.List;



/**
 * Created by tomasz on 14.07.2015.
 */
public class ProductsFragment extends Fragment implements View.OnClickListener {

    private Activity activity = null;

    //View components
    private ListView listView = null;
    private Button addButton = null;
    private TextView textView = null;

    //list adapter
    private ListAdapter listAdapter = null;

    List<Product> data = new LinkedList<Product>();
    private Cursor listCursor = null;
    private ProductsListConfigurationEnum productsListConfiguration;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

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


    private void initializeProductsLayoutComponents(View rootView) {

        // Make this {@link Fragment} listen for changes in both FABs.
        View addButton = (View) rootView.findViewById(R.id.add_button);
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

//        listView = (ListView) rootView.findViewById(R.id.products_list_view);
//
//        listAdapter = new SimpleCursorAdapter(getActivity(),R.layout.product_row_layout,listCursor,
//                new String[] {"name", "buy_date"},
//                new int[] {R.id.row_product_name_textview,
//                        R.id.row_buy_date_textview}, 0);
//
//        listView.setAdapter(listAdapter);
//
//        //listView.setSelector(R.drawable.list_item_selector);
//        //listView.setSelected(true);

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

        setRecyclerAdapter(productsListConfiguration);
    }

    private void setRecyclerAdapter(ProductsListConfigurationEnum productsListConfiguration) {

        data = ((MainActivity) getActivity()).getProductDatabaseFacade().listAll();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.d(Utilities.TAG, "ProductsFragment onAttach called");


    }


//    @Override
//    public void onCheckedChanged(MyFloatingActionButton fabView, boolean isChecked) {
//        // When a FAB is toggled, log the action.
//        switch (fabView.getId()) {
//            case R.id.fab_1:
//                Log.d(Utilities.TAG, String.format("FAB 1 was %s.", isChecked ? "checked" : "unchecked"));
//                break;
////            case R.id.fab_2:
////                Log.d(Utilities.TAG, String.format("FAB 2 was %s.", isChecked ? "checked" : "unchecked"));
////                break;
//            default:
//                break;
//        }
//    }

    @Override
    public void onClick(View v) {
        Log.d(Utilities.TAG,"Clicked on add button");
    }
}