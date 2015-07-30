package com.pum.tomasz.knowyourshare;



import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.widget.Toast;


import com.pum.tomasz.knowyourshare.tabs.TabInfo;
import com.pum.tomasz.knowyourshare.tabs.TabManager;
import com.pum.tomasz.knowyourshare.tabs.TabsTagEnum;
import com.pum.tomasz.knowyourshare.viewpager.MyPagerAdapter;


import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;


public class MainActivity extends FragmentActivity implements TabManager.TabChangeListener,
        MyPagerAdapter.ViewChangeListener, HomeFragment.OnHomeFragmentButtonClickListener{


    private TabManager tabManager = null;
    private PagerAdapter mPagerAdapter = null;
    private boolean doubleBackToExitPressedOnce = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d(Utilities.TAG, "MainActivity on Create called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabManager = new TabManager(this);
        tabManager.initialiseTabManager(savedInstanceState);
        if (savedInstanceState != null) {
            tabManager.setCurrentTabById(savedInstanceState.getInt("tab")); //set the tab as per the saved state
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
                ((HomeFragment)tabInfo.getFragment()).update("new text");
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


    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt("tab", tabManager.getCurrentTabId()); //save the tab selected
        ((MyPagerAdapter)mPagerAdapter).removeAllFragments();
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onHomeFragmentButtonClick(int id) {
        switch (id){
            case R.id.all_products_button_layout:
                Log.d(Utilities.TAG,"Clicked on show all products button");
                ((MyPagerAdapter)mPagerAdapter).getViewPager().setCurrentItem(TabsTagEnum.PRODUCTS.getValue(), true);
                break;
            case R.id.today_products_button_layout:
                Log.d(Utilities.TAG,"Clicked on show today's products button");
                ((MyPagerAdapter)mPagerAdapter).getViewPager().setCurrentItem(TabsTagEnum.PRODUCTS.getValue(), true);
                break;
            case R.id.cheapest_products_button_layout:
                Log.d(Utilities.TAG,"Clicked on show chepsts first products button");
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
}
