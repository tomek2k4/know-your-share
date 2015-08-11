package com.pum.tomasz.knowyourshare.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.pum.tomasz.knowyourshare.data.MeasureUnit;

import java.util.Locale;

/**
 * Created by tomasz on 10.08.2015.
 */
public class Preferences {
        public static final String PREFERENCES_NAME = "global";
        public static final String KEY_LANGUAGE = "language";
        public static final String KEY_MEASUREMENT_SYSTEM = "system";
        public static final String KEY_PHONE_NUMBER = "phone";


    public enum LanguageEnum {
        ENGLISH(0,"en"),POLISH(1,"pl");

        private final int value;
        private final String code;

        private LanguageEnum(int value,String code) {
            this.value = value;
            this.code = code;
        }

        public int getValue() {
            return value;
        }

        public String getCode(){
            return code;
        }

    }

    public enum MeasurementSystemEnum{
        METRICAL(0),IMPERIAL(1);

        private final int value;
        private MeasurementSystemEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

    }

    public static void initializeLocaleFromPreferences(Context context) {
        Locale locale;
        Configuration config = new Configuration();
        SharedPreferences prefs = context.getApplicationContext()
                .getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
        String languageString = prefs.getString(Preferences.KEY_LANGUAGE, Preferences.LanguageEnum.ENGLISH.name());
        locale = new Locale(Preferences.LanguageEnum.valueOf(languageString).getCode());
        config.locale = locale;
        context.getApplicationContext().getResources().updateConfiguration(config, null);
    }

    public static void initializeMeasurementSystemFromPreferences(Context context){
        SharedPreferences prefs = context.getApplicationContext()
                .getSharedPreferences(Preferences.PREFERENCES_NAME, Context.MODE_WORLD_READABLE);
        String measurementSystemString = prefs.getString(Preferences.KEY_MEASUREMENT_SYSTEM, MeasurementSystemEnum.METRICAL.name());
        if(measurementSystemString.equals(MeasurementSystemEnum.IMPERIAL.name())){
            MeasureUnit.setImperialMeasureSystem(true);
        }else{
            MeasureUnit.setImperialMeasureSystem(false);
        }
    }

}
