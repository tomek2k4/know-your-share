package com.pum.tomasz.knowyourshare;



import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;


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
    private Stack<Integer> fragmentTabsStack = null;

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

        //Create Fragments Stack push when new Fragment created, pop when back selected
        fragmentTabsStack = new Stack<>();
        fragmentTabsStack.push(0);
        Log.d(Utilities.TAG,"Added to stack:0");

    }

    private void initialisePaging() {
        List<TabInfo> tabInfoList = tabManager.getTabInfoList();
        mPagerAdapter  = new MyPagerAdapter(this,(ViewPager) super.findViewById(R.id.main_panel),tabInfoList);

        //((MyPagerAdapter)mPagerAdapter).getViewPager().setAdapter(this.mPagerAdapter);
    }


    // Event that comes form TabManager, ViewPager needs to be updated
    @Override
    public void onTabSelected(int position) {
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
            //add tab position to tab stack
            if(position != TabsTagEnum.HOME.getValue()){
                //save tab position only if different than home tab
                fragmentTabsStack.push(position);
                Log.d(Utilities.TAG, "Added to stack:"+new Integer(position));
            }else {
                Log.d(Utilities.TAG, "Cleared stack:"+new Integer(position));
                fragmentTabsStack.clear();
                fragmentTabsStack.push(TabsTagEnum.HOME.getValue());
            }
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
            super.onBackPressed();
        }else{
            try {
                previousPosition = fragmentTabsStack.pop();
            }catch (EmptyStackException e){
                Log.e(Utilities.TAG,"Empty fragment stack "+e.getStackTrace());
                previousPosition = TabsTagEnum.HOME.getValue();
            }
            ((MyPagerAdapter)mPagerAdapter).getViewPager().setCurrentItem(previousPosition, true);
        }

    }
}
