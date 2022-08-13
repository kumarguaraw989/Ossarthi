package com.cabossarthi.osaarthi.mapsession;

import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {
    public static PrefManager mInstance;
    public static Context mCtx;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "BSS";
    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    private static final String PICKUP_LATLNG = "PICUP";
    private static final String PICKUP_LOC_NAME = "PICUP_name";
    private static final String DROP_LATLNG = "DROP";
    private static final String DROP_LOC_NAME = "DROP_NAME";

    public PrefManager(Context context) {
        mCtx = context;
    }

    public static synchronized PrefManager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new PrefManager(context);
        }
        return mInstance;
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        SharedPreferences settings = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
        editor.apply();
    }

    public boolean isFirstTimeLaunch() {
        SharedPreferences settings = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return settings.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public boolean saveDropLocation(String LatLang, String Name) {
        SharedPreferences settings = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(DROP_LATLNG, LatLang);
        editor.putString(DROP_LOC_NAME, Name);
        editor.apply();
        return true;

    }

    public boolean savePicupLocation(String LatLang, String Name) {
        SharedPreferences settings = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(PICKUP_LATLNG, LatLang);
        editor.putString(PICKUP_LOC_NAME, Name);
        editor.apply();
        return true;

    }

    public String getPicupLatLng() {
        SharedPreferences settings = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return settings.getString(PICKUP_LATLNG, null);
    }

    public String getPicupLocName() {
        SharedPreferences settings = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return settings.getString(PICKUP_LOC_NAME, null);
    }

    public String getDropLatLng() {
        SharedPreferences settings = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return settings.getString(DROP_LATLNG, null);
    }

    public String getDropLocName() {
        SharedPreferences settings = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return settings.getString(DROP_LOC_NAME, null);
    }
    public boolean cleanSession(){
        SharedPreferences settings = mCtx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}
