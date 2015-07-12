package com.pum.tomasz.knowyourshare.data;

import java.util.Date;

/**
 * Created by tomasz on 11.07.2015.
 */
public class Product {

    //Mandatory fields
    private long id;
    private String name;
    private Date buyDate;
    private Date endDate;
    private double size;
    private MeasureUnitEnum unit;

    //optional fields
    private Date expirationDate;

}
