package com.pum.tomasz.knowyourshare.data;

import com.pum.tomasz.knowyourshare.preferences.Preferences;

/**
 * Created by tomasz on 23.07.2015.
 */
public class MeasureUnit {

    public static final double KG_TO_POUNDS_MULTIPLIER = 2.20462;
    public static final double LITER_TO_PINT_MULTIPLIER = 1.75975;
    public static final double METER_TO_FOOT_MULTIPLIER = 3.28084;

    private static boolean imperial = false;
    private MeasureUnitTypeEnum measureUnitTypeEnum;

    public MeasureUnit(MeasureUnitTypeEnum measureUnitTypeEnum) {
        this.imperial = imperial;
        this.measureUnitTypeEnum = measureUnitTypeEnum;
    }

    public String getUnitsName(){
        switch (measureUnitTypeEnum){
            case MASS:
                if(imperial) return "pounds";
                else return "kg";
            case VOLUME:
                if(imperial) return "pint";
                else return "l";
            case LENGTH:
                if(imperial) return "foot";
                else return "m";
            case QUANTITY:
                if(imperial) return "unit";
                else return "unit";
        }

        if(imperial) return "ounce";
        else return "l";
    }

    public MeasureUnitTypeEnum getMeasureUnitTypeEnum() {
        return measureUnitTypeEnum;
    }

    public void setMeasureUnitTypeEnum(MeasureUnitTypeEnum measureUnitTypeEnum) {
        this.measureUnitTypeEnum = measureUnitTypeEnum;
    }

    public static void setImperialMeasureSystem(boolean imp){
        imperial = imp;
    }

    public static boolean isImperialMeasureSystem() {
        return imperial;
    }

}
