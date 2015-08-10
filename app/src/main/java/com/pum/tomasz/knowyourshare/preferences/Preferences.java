package com.pum.tomasz.knowyourshare.preferences;

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

}
