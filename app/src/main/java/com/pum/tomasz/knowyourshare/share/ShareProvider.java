package com.pum.tomasz.knowyourshare.share;

import android.content.Context;

/**
 * Created by tmaslon on 2015-08-10.
 */
public class ShareProvider {

    private static Context context = null;

    private static ShareProvider instance = new ShareProvider();

    private ShareProvider() {}

    public static ShareProvider getInstance() {
        return instance;
    }

    public void sendSms(){

    }

    public static void setContext(Context context){
        context = context;
    }

}
