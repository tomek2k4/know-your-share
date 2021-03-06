package com.pum.tomasz.knowyourshare;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by tomasz on 13.07.2015.
 */
public class Utilities {

    public static final SimpleDateFormat DATEFMT = new SimpleDateFormat(
            "yyyy-MM-dd");

    public static final DecimalFormat DOUBLE_CUT_ZERO_FMT = new DecimalFormat("0.#");

    public final static String TAG = "Tomek";

    public static String convertDateToString(Date date){
        return (date == null) ? null : DATEFMT.format(date);
    }
}
