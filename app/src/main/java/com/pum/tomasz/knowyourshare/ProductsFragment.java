package com.pum.tomasz.knowyourshare;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ActionMode;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.pum.tomasz.knowyourshare.data.Product;
import com.pum.tomasz.knowyourshare.data.ProductsListConfigurationEnum;
import com.pum.tomasz.knowyourshare.floatingactionbutton.MyFloatingActionButton;
import com.pum.tomasz.knowyourshare.recyclerview.DividerItemDecoration;
import com.pum.tomasz.knowyourshare.recyclerview.MyRecyclerViewAdapter;
import com.pum.tomasz.knowyourshare.share.ShareProvider;
import com.pum.tomasz.knowyourshare.share.ShareTypeEnum;

import java.util.LinkedList;
import java.util.List;



/**
 * Created by Tomasz Maslon on 14.07.2015.
 */
public class ProductsFragment extends Fragment implements View.OnClickListener,
        RecyclerView.OnItemTouchListener,
        MyRecyclerViewAdapter.OnProductItemClickListener {

    private Activity activity = null;

    List<Product> data = new LinkedList<Product>();
    private ProductsListConfigurationEnum productsListConfiguration;


    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private View addButton = null;

    GestureDetectorCompat gestureDetector;
    public ActionMode actionMode;
    private int longTouchIndex =-1;

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
        if(v!=null){
            if(v.getId() == R.id.add_button){
                if(!isAddButtonShare()){
                    Log.d(Utilities.TAG, "Clicked on add button");
                    Intent i = new Intent(getActivity(), ProductAddActivity.class);
                    startActivity(i);
                }else {
                    Log.d(Utilities.TAG, "Clicked on share button");
                    //Check if we have a selected items
                    if(((MyRecyclerViewAdapter)mAdapter).getSelectedItemCount() != 0){
                        ShareProvider shareProvider = new ShareProvider(getActivity());
                        shareProvider.setShareType(ShareTypeEnum.ANY);
                        shareProvider.sendMessage(((MyRecyclerViewAdapter)mAdapter).getSelectedProducts());
                    }else{
                        Toast.makeText(getActivity(),getResources().getString(R.string.no_items_selected_toast_string), Toast.LENGTH_SHORT).show();
                    }
                    actionMode.finish();
                }
            }
        }
    }

    @Override
    public void onProductItemClick(int position) {
        Log.d(Utilities.TAG,"Clicked on item: "+position+" in ProductsFragment");
        if (actionMode != null) {
            if(position!=longTouchIndex){
                myToggleSelection(position);
            }
            longTouchIndex = -1;
            return;
        }
    }

    public void updateListConfiguration(ProductsListConfigurationEnum plc){

        //Check if plc is different than current configuration
        if (plc != productsListConfiguration) {

            productsListConfiguration = plc;
            setCurrentData(plc);
            //create new Recycler Adapter
            mAdapter = new MyRecyclerViewAdapter(data,this);
            mRecyclerView.swapAdapter(mAdapter,false);

            displayEmptyListPrompt(getView());
        }
    }

    private void displayEmptyListPrompt(View rootView) {
        LinearLayout emptyListTextView = (LinearLayout) rootView.findViewById(R.id.no_products_info);

        if(data.size() != 0){
            emptyListTextView.setVisibility(View.INVISIBLE);
        }else {
            emptyListTextView.setVisibility(View.VISIBLE);
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

        displayEmptyListPrompt(rootView);

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
        mAdapter = new MyRecyclerViewAdapter(data,this);
        mRecyclerView.setAdapter(mAdapter);

        RecyclerView.ItemDecoration itemDecoration =
                new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        mRecyclerView.addItemDecoration(itemDecoration);

        // onClickDetection is done in this Activity's onItemTouchListener
        // with the help of a GestureDetector;
        // Tip by Ian Lake on G+ in a comment to this post:
        // https://plus.google.com/+LucasRocha/posts/37U8GWtYxDE
        mRecyclerView.addOnItemTouchListener(this);
        gestureDetector =
                new GestureDetectorCompat(getActivity(), new RecyclerViewDemoOnGestureListener());

    }

    public ProductsListConfigurationEnum getCurrentListConfiguratio() {
        return productsListConfiguration;
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        gestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {

    }

    public void setAddButtonToShare(boolean addButtonToShare) {
        if(addButton!=null){
            if(addButton instanceof MyFloatingActionButton){
                ((MyFloatingActionButton) addButton).setChecked(addButtonToShare);
            }else{
                ((ImageButton)addButton).setSelected(addButtonToShare);
            }
        }
    }


    public boolean isAddButtonShare() {
        if(addButton!=null){
            if(addButton instanceof MyFloatingActionButton){
                return ((MyFloatingActionButton) addButton).isChecked();
            }else{
                return ((ImageButton)addButton).isSelected();
            }
        }
        return false;
    }

    public void cloceActionMode() {
        if(actionMode!=null){
            actionMode.finish();
        }
    }


    private class RecyclerViewDemoOnGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            onClick(view);
            return super.onSingleTapConfirmed(e);
        }

        public void onLongPress(MotionEvent e) {
            View view = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (actionMode != null) {
                return;
            }
            // Start the CAB using the ActionMode.Callback defined above
            actionMode = getActivity().startActionMode(mCallback);
            int idx = mRecyclerView.getChildPosition(view);
            myToggleSelection(idx);
            longTouchIndex = idx;
            super.onLongPress(e);
        }
    }

    private void myToggleSelection(int idx) {
        ((MyRecyclerViewAdapter)mAdapter).toggleSelection(idx);
        String title = getString(R.string.selected_count, ((MyRecyclerViewAdapter)mAdapter).getSelectedItemCount());
        actionMode.setTitle(title);

    }


    private ActionMode.Callback mCallback = new ActionMode.Callback()
    {
        @Override
        public boolean onPrepareActionMode( ActionMode mode, Menu menu )
        {
            return false;
        }
        @Override
        public void onDestroyActionMode( ActionMode mode )
        {
            actionMode = null;
            ((MyRecyclerViewAdapter)mAdapter).clearSelections();
            //fab.setVisibility(View.VISIBLE);

            setAddButtonToShare(false);

            longTouchIndex = -1;
        }

        @Override
        public boolean onCreateActionMode( ActionMode mode, Menu menu )
        {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.menu_cab_fragment_products, menu);
            //fab.setVisibility(View.GONE);

            setAddButtonToShare(true);

            return true;
        }
        @Override
        public boolean onActionItemClicked( ActionMode mode, MenuItem item )
        {
            switch (item.getItemId()) {
                case R.id.menu_delete:
                    List<Integer> selectedItemPositions = ((MyRecyclerViewAdapter)mAdapter).getSelectedItems();
                    int currPos;
                    for (int i = selectedItemPositions.size() - 1; i >= 0; i--) {
                        currPos = selectedItemPositions.get(i);
                        ((MainActivity)getActivity()).getProductDatabaseFacade().delete(data.get(currPos));
                        ((MyRecyclerViewAdapter) mAdapter).removeData(currPos);
                        Log.d(Utilities.TAG,"Remove item "+currPos);
                    }
                    actionMode.finish();
                    displayEmptyListPrompt(getView());
                    return true;
                default:
                    return false;
            }
        }
    };




}