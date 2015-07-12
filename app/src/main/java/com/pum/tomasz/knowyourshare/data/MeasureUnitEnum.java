package com.pum.tomasz.knowyourshare.data;

/**
 * Created by tomasz on 11.07.2015.
 */
public enum MeasureUnitEnum {
    MASS("Mass"),
    VOLUME("Volume");


    MeasureUnitEnum(String unitName) {
        this.unitName = unitName;
    }

    private final String unitName;   // in kilograms



}
