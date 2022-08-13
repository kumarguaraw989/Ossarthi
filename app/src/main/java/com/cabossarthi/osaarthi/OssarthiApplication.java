package com.cabossarthi.osaarthi;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.text.format.Formatter;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.cabossarthi.osaarthi.session.SessionManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.IOException;
import java.util.List;

public class OssarthiApplication extends MultiDexApplication {
    public static String JWTTOKEN;
    public static OssarthiApplication instance;
    public static final String TAG = "PosiApplication";
    private static Context mContext = null;
//    private static SharedPreferences mSharedPreferences;
    String notificationChannelID = "TestChannel";
    Context context = getAppContext().getApplicationContext();
     public static String device_name = android.os.Build.MODEL;
    public static String device_os = "android";
    public static String device_id = Settings.Secure.getString(getAppContext().getContentResolver(),
            Settings.Secure.ANDROID_ID);
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
//        mSharedPreferences = getSharedPreferences(getString(R.string.app_name), MODE_PRIVATE);
        instance = this;
        createNotificationChannel();
         FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
          FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(new OnCompleteListener<String>() {
                    @Override
                    public void onComplete(@NonNull Task<String> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        } else {
                            String token = task.getResult();
                            Log.e(TAG, "onComplete: "+token);
                            SessionManager.getInstance(getAppContext()).saveRegId(token);
                        }
                    }
                });
          JWTTOKEN=SessionManager.getInstance(mContext).getJWTTOKEN();
     }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    public static OssarthiApplication getInstance() {
        return instance;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

    public static String getRestartIntent() {
        return "com.cabossarthi.osaarthi.restart";
    }

    public static Context getAppContext() {
        return mContext;
    }


}