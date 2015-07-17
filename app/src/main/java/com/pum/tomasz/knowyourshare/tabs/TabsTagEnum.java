package com.pum.tomasz.knowyourshare.tabs;

/**
 * Created by tomasz on 15.07.2015.
 */
public enum TabsTagEnum {

    HOME(0),PRODUCTS(1),SETTINGS(2);

    private final int value;
    private TabsTagEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }


}
