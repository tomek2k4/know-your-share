package com.pum.tomasz.knowyourshare;


import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;


import com.pum.tomasz.knowyourshare.data.Product;
import com.pum.tomasz.knowyourshare.data.ProductDatabaseFacade;
import com.pum.tomasz.knowyourshare.data.ProductDbOpenHelper;
import com.pum.tomasz.knowyourshare.data.ProductsListConfigurationEnum;
import com.pum.tomasz.knowyourshare.tabs.TabInfo;
import com.pum.tomasz.knowyourshare.tabs.TabManager;
import com.pum.tomasz.knowyourshare.tabs.TabsTagEnum;
import com.pum.tomasz.knowyourshare.viewpager.MyPagerAdapter;

import java.util.EmptyStackException;
import java.util.List;


public class MainActivity extends FragmentActivity implements TabManager.TabChangeListener,
        MyPagerAdapter.ViewChangeListener, HomeFragment.OnHomeFragmentButtonClickListener{

    private TabManager tabManager = null;
    private PagerAdapter mPagerAdapter = null;
    private boolean doubleBackToExitPressedOnce = false;

    private ProductDbOpenHelper dbOpenHelper = null;
    private ProductDatabaseFacade dbHelper = null;
    private SQLiteDatabase database = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(Utilities.TAG, "MainActivity on Create called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Open database
        if (dbOpenHelper == null) {
            dbOpenHelper = new ProductDbOpenHelper(this);
            database = dbOpenHelper.getWritableDatabase();
        }
        if (dbHelper == null) {
            dbHelper = new ProductDatabaseFacade(database);
        }

        List<Product> p = dbHelper.getList(ProductsListConfigurationEnum.ALL_PRODUCTS);
        Log.d(Utilities.TAG, "Number of products: " + p.size());

        List<Product> tp = dbHelper.getList(ProductsListConfigurationEnum.TODAY_PRODUCTS);
        Log.d(Utilities.TAG, "Number of today's products: " + tp.size());

        List<Product> fp = dbHelper.getList(ProductsListConfigurationEnum.FREQUENTLY_BOUGHT_PRODUCTS);
        Log.d(Utilities.TAG, "Number of frequently bought products: " + fp.size());

        Bundle fragmentsInitialArgs = new Bundle();
        //Pass initial information to Fragments
        fragmentsInitialArgs.putInt(BundleKeyEnum.NUMBER_OF_PRODUCTS.name(), p.size());
        fragmentsInitialArgs.putInt(BundleKeyEnum.NUMBER_OF_TODAY_PRODUCTS.name(), tp.size());
        fragmentsInitialArgs.putInt(BundleKeyEnum.NUMBER_OF_FREQUENTLY_BOUGHT_PRODUCTS.name(), fp.size());
        fragmentsInitialArgs.putString(BundleKeyEnum.PRODUCTS_LIST_CONFIGURATION.name(),
                ProductsListConfigurationEnum.ALL_PRODUCTS.name());
        if(savedInstanceState!=null){
            String savedProducListConfigurationString =
                    savedInstanceState.getString(BundleKeyEnum.PRODUCTS_LIST_CONFIGURATION.name(),
                            ProductsListConfigurationEnum.ALL_PRODUCTS.name());
            fragmentsInitialArgs.putString(BundleKeyEnum.PRODUCTS_LIST_CONFIGURATION.name(),savedProducListConfigurationString);
        }

        tabManager = new TabManager(this);
        tabManager.initialiseTabManager(fragmentsInitialArgs);
        if (savedInstanceState != null) {
            tabManager.setCurrentTabById(savedInstanceState.getInt(BundleKeyEnum.LAST_KNOWN_TAB.name())); //set the tab as per the saved state
        }

        //initialsie the pager
        this.initialisePaging();

    }

    private void initialisePaging() {
        List<TabInfo> tabInfoList = tabManager.getTabInfoList();
        mPagerAdapter  = new MyPagerAdapter(this,(ViewPager) super.findViewById(R.id.main_panel),tabInfoList);
    }


    // Event that comes form TabManager, ViewPager needs to be updated
    @Override
    public void onTabSelected(int position,boolean isStackTraced) {

        if(isStackTraced){
            //Add to stack previous position
            tabManager.getFragmentTabsStack().push(tabManager.getLastTabPosition());
            Log.d(Utilities.TAG, "Added to stack:" + tabManager.getLastTabPosition());
        }

        if(mPagerAdapter!=null){
            Log.d(Utilities.TAG, "Pressed tab with index: " + new Integer(position).toString());
            ((MyPagerAdapter)mPagerAdapter).getViewPager().setCurrentItem(position, true);

            if(position==0)
            {
                Log.d(Utilities.TAG, "Clicked on tab 0, update home fragment");
                TabInfo tabInfo = tabManager.getTabInfoList().get(position);
            }
        }
    }

    // Event that comes form ViewPagerAdapter, TabManager needs to be updated
    @Override
    public void onViewSelected(int position) {
        // Check current tab manager last tab to prevent over lapping of events
        if(tabManager.getCurrentTabId() != tabManager.getTabInfoList().get(position).getViewId()){
            tabManager.setCurrentTabById(tabManager.getTabInfoList().get(position).getViewId());
        }
    }

    @Override
    public void onHomeFragmentButtonClick(int id) {
        ProductsFragment pf;

        switch (id){
            case R.id.all_products_button_layout:
                Log.d(Utilities.TAG,"Clicked on show all products button");
                pf =(ProductsFragment)
                        tabManager.getTabInfoList().get(TabsTagEnum.PRODUCTS.getValue()).getFragment();
                pf.updateListConfiguration(ProductsListConfigurationEnum.ALL_PRODUCTS);
                ((MyPagerAdapter)mPagerAdapter).getViewPager().setCurrentItem(TabsTagEnum.PRODUCTS.getValue(), true);
                break;
            case R.id.today_products_button_layout:
                Log.d(Utilities.TAG, "Clicked on show today's products button");
                pf =(ProductsFragment)
                        tabManager.getTabInfoList().get(TabsTagEnum.PRODUCTS.getValue()).getFragment();
                pf.updateListConfiguration(ProductsListConfigurationEnum.TODAY_PRODUCTS);

                ((MyPagerAdapter)mPagerAdapter).getViewPager().setCurrentItem(TabsTagEnum.PRODUCTS.getValue(), true);
                break;
            case R.id.frequently_bought_products_button_layout:
                Log.d(Utilities.TAG,"Clicked on show chepsts first products button");
                pf =(ProductsFragment)
                        tabManager.getTabInfoList().get(TabsTagEnum.PRODUCTS.getValue()).getFragment();
                pf.updateListConfiguration(ProductsListConfigurationEnum.FREQUENTLY_BOUGHT_PRODUCTS);

                ((MyPagerAdapter)mPagerAdapter).getViewPager().setCurrentItem(TabsTagEnum.PRODUCTS.getValue(), true);
                break;
            case R.id.add_product_home_button_layout:
                Log.d(Utilities.TAG,"Clicked on add new product button");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(Utilities.TAG,"Clicked onBackPressed()");
        int previousPosition;
        int currentPosition = ((MyPagerAdapter)mPagerAdapter).getViewPager().getCurrentItem();

        if(currentPosition == TabsTagEnum.HOME.getValue()){
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }else{
            try {
                tabManager.getFragmentTabsStack().pop();
                previousPosition = tabManager.getFragmentTabsStack().pop();
                Log.d(Utilities.TAG,"Poped stack value: "+previousPosition);
            }catch (EmptyStackException e){
                Log.e(Utilities.TAG,"Empty fragment stack "+e.getStackTrace());
                previousPosition = TabsTagEnum.HOME.getValue();
            }
            Log.d(Utilities.TAG,"Jump to tab: "+previousPosition);
            ((MyPagerAdapter)mPagerAdapter).getViewPager().setCurrentItem(previousPosition, true);
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BundleKeyEnum.LAST_KNOWN_TAB.name(), tabManager.getCurrentTabId()); //save the tab selected

        //Save current configuration of products list
        ProductsFragment pf = (ProductsFragment)
                tabManager.getTabInfoList().get(TabsTagEnum.PRODUCTS.getValue()).getFragment();
        if (pf != null){
            outState.putString(BundleKeyEnum.PRODUCTS_LIST_CONFIGURATION.name(),pf.getCurrentListConfiguratio().name());
        }

        ((MyPagerAdapter)mPagerAdapter).removeAllFragments();
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(dbHelper!=null){
            dbHelper.dispose();
        }
    }

    public ProductDatabaseFacade getProductDatabaseFacade() {
        return dbHelper;
    }
}
