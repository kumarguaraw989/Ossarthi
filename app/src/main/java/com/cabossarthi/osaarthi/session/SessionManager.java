package com.cabossarthi.osaarthi.session;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
/*
    {
        "id":"af2f598d-c78e-4be5-8115-8ca4d6a99ec4",
            "firstName":"raj",
            "result":true,
            "username":"7777777777",
            "description":"raj Registered Sussfulley",
            "phoneNumber":"7777777777",
            "email":"raj@gmail.com",
            "lastName":"raj",
            "roles":[
        "Customer"
   ],
        "firebaseToken":"",
            "firebaseUserId":"Jmh4iRUkGjcrBj0clibX0U8iyAf1"
    }*/


    private static SessionManager sessionManger;
    private static Context mCtx;
    private static final String SESSION_NAME = "Aabra ka dabra";
    private static final String NAME = "name";
    private static final String USERNAME = "username";
    private static final String ROLES = "roles";
    private static final String MOBILE = "mobile";
    private static final String RESULT = "result";
    private static final String Description = "description";
    private static final String EMAIL = "email";
    private static final String TOKEN = "token";
    private static final String REGiD = "REGiD";
    private static final String Lattitude = "";
    private static final String Longitude =  "";
    private static final String ID = "ID";
    private static final String JWTTOKEN = "JwtToken";
    private static final String PHOTO = "photo";
    private static final String FirebaseToken = "firebaseToken";
    private static final String FirebaseUserId = "firebaseUserId";
    private static final String TotalPrice = "price";

    public SessionManager(Context context) {
        mCtx = context;
    }

    public static synchronized SessionManager getInstance(Context context) {
        if (sessionManger == null) {
            sessionManger = new SessionManager(context);
        }
        return sessionManger;
    }

    public boolean saveUserLogin(String name,
                                 String mobile,
                                 String email,
                                 String id,
                                 String roles,
                                 String firebaseUserId,
                                 String firebaseToken) {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor = settings.edit();
        editor.putString(NAME, name);
        editor.putString(ROLES, roles);
        editor.putString(MOBILE, mobile);
        editor.putString(EMAIL, email);
        editor.putString(ID, id);
        editor.putString(FirebaseToken, firebaseToken);
        editor.putString(FirebaseUserId, firebaseUserId);
        editor.apply();
        return true;
    }

    public boolean saveRegId(String regId) {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor = settings.edit();
        editor.putString(REGiD, regId);
        editor.apply();
        return true;
    }

    public boolean saveJwtToken(String jwtToken) {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor = settings.edit();
        editor.putString(JWTTOKEN, jwtToken);
        editor.apply();
        return true;
    }

    public boolean saveTotalPrice(String price) {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor = settings.edit();
        editor.putString(TotalPrice,price);
        editor.apply();
        return true;
    }

    public boolean saveCurrentLocation(String lattitude,String longitude) {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor = settings.edit();
        editor.putString(Lattitude,lattitude);
        editor.putString(Longitude,longitude);
        editor.apply();
        return true;
    }

    public String getREGiD() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(REGiD, "");
    }

    public String getLattitude() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(Lattitude, "");
    }

    public String getLongitude() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(Longitude, "");
    }

    public String getJWTTOKEN() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(JWTTOKEN, "");
    }

    public String getTotalPrice() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(TotalPrice, "");
    }

    public String getNAME() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(NAME, "");
    }

    public String getMOBILE() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(MOBILE, "");
    }

    public   String getUSERNAME() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(USERNAME, "");
    }

    public   String getID() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(ID, "");
    }

    public   String getRESULT() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(RESULT, "");
    }

    public   String getPHOTO() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(PHOTO, "");
    }

    public   String getROLES() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(ROLES, "");
    }

    public String getEMAIL() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(EMAIL, "");
    }


    public String getPhoto() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(PHOTO, "");
    }

    public String getTOKEN() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(TOKEN, "");
    }

    public String getFirebaseToken() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(FirebaseToken, "");
    }

    public String getFirebaseUserId() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(FirebaseUserId, "");
    }

    public String getDescription() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(Description, "");
    }

    public String getResult() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        return settings.getString(RESULT, "");
    }

    public boolean logout() {
        SharedPreferences settings = mCtx.getSharedPreferences(SESSION_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.clear();
        editor.apply();
        return true;
    }
}

