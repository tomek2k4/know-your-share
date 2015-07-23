package com.pum.tomasz.knowyourshare.data;

/**
 * Created by tomasz on 11.07.2015.
 */
public enum MeasureUnitTypeEnum {
    MASS("Mass"),
    VOLUME("Volume"),
    LENGTH("Length");


    MeasureUnitTypeEnum(String unitName) {
        this.unitName = unitName;
    }

    private final String unitName;



}
