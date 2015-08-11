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
    private MeasureUnit unit = null;
    //optional fields
    private Date expirationDate;
    private double price;

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
        if(MeasureUnit.isImperialMeasureSystem()){
            double trasformSize = size;
            switch (unit.getMeasureUnitTypeEnum()){
                case MASS:
                    trasformSize = MeasureUnit.KG_TO_POUNDS_MULTIPLIER * size;
                    break;
                case VOLUME:
                    trasformSize = MeasureUnit.LITER_TO_PINT_MULTIPLIER * size;
                    break;
                case LENGTH:
                    trasformSize = MeasureUnit.METER_TO_FOOT_MULTIPLIER * size;
                    break;
                case QUANTITY:
                    trasformSize = size;
                    break;
            }
            return trasformSize;
        }else{
            return size;
        }
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getMeasureUnitTypeString() {
        return unit.getMeasureUnitTypeEnum().name();
    }

    public void setMeasureUnitTypeEnum(MeasureUnitTypeEnum measureUnitTypeEnum) {
        if(unit==null){
            unit = new MeasureUnit(measureUnitTypeEnum);
        }else{
            this.unit.setMeasureUnitTypeEnum(measureUnitTypeEnum);
        }
    }

    public String getMeasureUnitString(){
        return unit.getUnitsName();
    }

    public Date getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {
        this.expirationDate = expirationDate;
    }

    public void setPrice(double price) {
        this.price = price;
    }


}
