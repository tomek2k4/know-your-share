package com.pum.tomasz.knowyourshare.data;

import com.pum.tomasz.knowyourshare.Utilities;

import java.text.ParseException;
import java.util.Date;

/**
 * Created by tomasz on 11.07.2015.
 */
public class Product {

    //Mandatory fields
    private long id;
    private String name;
    private Date buyDate;
    private Date endOfUsageDate;
    private double size;
    private MeasureUnit unit;

    //optional fields
    private Date expirationDate;

    public Product() {
    }

    public Product(long id) {
        this.id = id;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBuyDate() {
        return buyDate;
    }

    public void setBuyDate(String buyDateStr) {
        try {
            this.buyDate = Utilities.DATEFMT.parse(buyDateStr);
        } catch (ParseException e) {
        }
    }

    public Date getEndOfUsageDate() {
        return endOfUsageDate;
    }

    public void setEndOfUsageDate(Date endOfUsageDate) {
        this.endOfUsageDate = endOfUsageDate;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getMeasureUnitString() {
        return unit.getMeasureUnitTypeEnum().name();
    }

    public void setMeasureUnitTypeEnum(MeasureUnitTypeEnum measureUnitTypeEnum) {
        this.unit.setMeasureUnitTypeEnum(measureUnitTypeEnum);
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }
}
