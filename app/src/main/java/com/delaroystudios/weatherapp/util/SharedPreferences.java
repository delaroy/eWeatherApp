package com.delaroystudios.weatherapp.util;

import android.content.Context;

public class SharedPreferences {

    private String WEATHR_PREFERENCES = "WEATHER";
    private android.content.SharedPreferences preferences;
    private android.content.SharedPreferences.Editor editor;
    private static Context context;
    public static SharedPreferences instance;

    //----------------------------------------------------------------------------------------------
    // String Constants
    //----------------------------------------------------------------------------------------------
    public static final String CITY = "token";
    public static final String NUM_DAYS = "num_days";
    public static final String DESC = "desc";
    public static final String TEMP = "temp";



    //----------------------------------------------------------------------------------------------
    // Constructor and Setters
    //----------------------------------------------------------------------------------------------
    private SharedPreferences(Context ctx) {
        context = ctx;
        preferences = context.getSharedPreferences(WEATHR_PREFERENCES, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static SharedPreferences getInstance(Context ctx) {
        if (instance == null) {
            instance = new SharedPreferences(ctx);
        }
        return instance;
    }

    public void putStringValue(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public void putIntValue(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public void putBooleanValue (String key, boolean value) {
        editor.putBoolean(key,value);
        editor.commit();
    }

    public void putLongValue (String key, long value) {
        editor.putLong ( key,value );
        editor.commit ();
    }

    //----------------------------------------------------------------------------------------------
    // Getters
    //----------------------------------------------------------------------------------------------
    public String getCity() {
        return preferences.getString(CITY, "Paris");
    }

    public String getNumDays() {
        return preferences.getString(NUM_DAYS, "7");
    }

    public String getDesc() {
        return preferences.getString(DESC, "");
    }

    public String getTemp() {
        return preferences.getString(TEMP, "");
    }
}
