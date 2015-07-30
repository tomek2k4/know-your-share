package com.pum.tomasz.knowyourshare.data;

/**
 * Created by tomasz on 30.07.2015.
 */
public enum ProductRecordColumnsEnum {

    id(0),
    name(1),
    buyDate(2),
    endOfUsageDate(3),
    size(4),
    unit(5),
    expirationDate(6),
    price(7);


    private final int column;
    private ProductRecordColumnsEnum(int column) {
        this.column = column;
    }

    public int getIndex() {
        return column;
    }


}
