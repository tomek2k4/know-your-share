package com.pum.tomasz.knowyourshare.data;

/**
 * Created by tomasz on 23.07.2015.
 */
public class MeasureUnit {
    private boolean imperial = false;
    private MeasureUnitTypeEnum measureUnitTypeEnum;

    public MeasureUnit(boolean imperial, MeasureUnitTypeEnum measureUnitTypeEnum) {
        this.imperial = imperial;
        this.measureUnitTypeEnum = measureUnitTypeEnum;
    }

    public  MeasureUnit(MeasureUnitTypeEnum measureUnit){
        this(false,measureUnit);
    }

    public String getUnitsName(){
        switch (measureUnitTypeEnum){
            case MASS:
                if(imperial) return "pounds";
                else return "kg";
            case VOLUME:
                if(imperial) return "ounce";
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

    public String getMassUnitsName(){
        if(imperial) return "pounds";
        else return "kg";
    }

    public String getLengthUnitsName(){
        if(imperial) return "foot";
        else return "m";
    }

    public MeasureUnitTypeEnum getMeasureUnitTypeEnum() {
        return measureUnitTypeEnum;
    }

    public void setMeasureUnitTypeEnum(MeasureUnitTypeEnum measureUnitTypeEnum) {
        this.measureUnitTypeEnum = measureUnitTypeEnum;
    }
}
