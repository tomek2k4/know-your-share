package com.pum.tomasz.knowyourshare.tabs;

import android.app.Fragment;
import android.os.Bundle;

/**
 * Created by tomasz on 15.07.2015.
 */
public class TabInfo {

    private int viewId;
    private String tag;
    private Class<?> clss;
    private Bundle args;
    private Fragment fragment;

    TabInfo(int viewId,String tag, Class<?> clazz, Bundle args) {
        this.viewId = viewId;
        this.tag = tag;
        this.clss = clazz;
        this.args = args;
    }

    public int getViewId() {
        return viewId;
    }

    public void setViewId(int viewId) {
        this.viewId = viewId;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Class<?> getClss() {
        return clss;
    }

    public void setClss(Class<?> clss) {
        this.clss = clss;
    }

    public Bundle getArgs() {
        return args;
    }

    public void setArgs(Bundle args) {
        this.args = args;
    }

    public Fragment getFragment() {
        return fragment;
    }

    public void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }


}
