package com.pum.tomasz.knowyourshare;


import android.annotation.TargetApi;
import android.app.Activity;

import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.pum.tomasz.knowyourshare.data.Product;
import com.pum.tomasz.knowyourshare.data.ProductsListConfigurationEnum;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by tomasz on 14.07.2015.
 */
public class ProductsFragment extends Fragment implements View.OnClickListener { //extends ListFragment {

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

        initializeProductsLayoutComponents(rootView);

        return rootView;

    }

    private void initializeProductsLayoutComponents(View rootView) {
        
        addButton = (Button) rootView.findViewById(R.id.add_button);
        addButton.setOnClickListener(this);

        //InitializelList
        initializeListView(rootView);

    }

    private void initializeListView(View rootView) {

        listView = (ListView) rootView.findViewById(R.id.products_list_view);

        listAdapter = new SimpleCursorAdapter(getActivity(),R.layout.product_row_layout,listCursor,
                new String[] {"name", "buy_date"},
                new int[] {R.id.row_product_name_textview,
                        R.id.row_buy_date_textview}, 0);

        listView.setAdapter(listAdapter);

        //listView.setSelector(R.drawable.list_item_selector);
        //listView.setSelected(true);

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(Utilities.TAG, "ProductsFragment onCreate called");

        Bundle arguments = getArguments();
        if (arguments != null)
        {
            //Set initial fields values
            productsListConfiguration = ProductsListConfigurationEnum.valueOf(
                    arguments.getString(BundleKeyEnum.PRODUCTS_LIST_CONFIGURATION.name(),
                            ProductsListConfigurationEnum.ALL_PRODUCTS.name()));
        } else {
            productsListConfiguration = ProductsListConfigurationEnum.ALL_PRODUCTS;
        }

        setListCursor(productsListConfiguration);


    }

    private void setListCursor(ProductsListConfigurationEnum productsListConfiguration) {

        listCursor = ((MainActivity)getActivity()).getProductDatabaseFacade().getCursor(productsListConfiguration);
        getActivity().startManagingCursor(listCursor);

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        Log.d(Utilities.TAG, "ProductsFragment onAttach called");


    }

    @Override
    public void onClick(View v) {
        Log.d(Utilities.TAG,"Clicked on add button");
    }
}
