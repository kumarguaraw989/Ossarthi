package com.cabossarthi.osaarthi;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cabossarthi.osaarthi.gmap.SelectDropLocationActivity;
import com.cabossarthi.osaarthi.mapsession.DropLocationPreference;
import com.cabossarthi.osaarthi.mapsession.PickupPreferenceManager;
import com.cabossarthi.osaarthi.session.SessionManager;
import com.cabossarthi.osaarthi.ui.LoginTypeActivity;
import com.cabossarthi.osaarthi.ui.driver.DriverDashboardActivity;
import com.cabossarthi.osaarthi.ui.user.UserDashboardActivity;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;
public class MainActivity extends AppCompatActivity{
    private AppUpdateManager appUpdateManager;
    RelativeLayout mainLayout;
    Context context;
    int LOCATION_PERMISSION_REQUEST_CODE=1;
    public static GoogleApiClient googleApiClient;
    boolean isGPSEnabled = false;
    LocationManager locationManager;
    private Context mContext;
    final static int REQUEST_LOCATION = 199;
    private boolean ispaused = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        DropLocationPreference.getInstance(getApplicationContext()).cleanSession();
        PickupPreferenceManager.getInstance(getApplicationContext()).cleanSession();
        mContext = MainActivity.this;
        mainLayout = findViewById(R.id.rl_login_main);
        //auto update
        appUpdateManager = AppUpdateManagerFactory.create(MainActivity.this);
        com.google.android.play.core.tasks.Task<AppUpdateInfo> appUpdateInfoTask = appUpdateManager.getAppUpdateInfo();
        appUpdateInfoTask.addOnSuccessListener(appUpdateInfo -> {
            if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
                    && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)) {
                try{
                    appUpdateManager.startUpdateFlowForResult(appUpdateInfo, AppUpdateType.IMMEDIATE, MainActivity.this, 1);
                }catch (IntentSender.SendIntentException e) {
                    e.printStackTrace();
                }
            }
        });
        FirebaseApp.initializeApp(this);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w("TAG", "Fetching FCM registration token failed", task.getException());
                    } else {
                        String token = task.getResult();
                        Log.e("TAG", "onComplete: " + token);
                        SessionManager.getInstance(getApplicationContext()).saveRegId(token);
                    }
                });
        FirebaseMessaging.getInstance().subscribeToTopic("weather")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
//                        String msg = getString(R.string.msg_subscribed);
//                        if (!task.isSuccessful()) {
//                            msg = getString(R.string.msg_subscribe_failed);
//                        }
//                        Log.d(TAG, msg);z
//                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });

        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
        } catch (Exception e) {
            e.printStackTrace();
        }
        isGPSEnabled = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        @Override
    protected void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);

        } else if(!isGPSEnabled){
            enableLoc(mContext);
        } else {
            openAct();
        }
    }
    public void openAct() {
        new Handler().postDelayed(() -> {
//            Toast.makeText(getApplicationContext(), SessionManager.getInstance(getApplicationContext()).getROLES(), Toast.LENGTH_SHORT).show();
            if (!SessionManager.getInstance(getApplicationContext()).getID().isEmpty()) {
                if (SessionManager.getInstance(getApplicationContext()).getROLES().equals("Customer")) {
                    startActivity(new Intent(getApplicationContext(), UserDashboardActivity.class));
                    finish();
                }else{
                    startActivity(new Intent(getApplicationContext(), DriverDashboardActivity.class));
                    finish();
                }
            } else{
                startActivity(new Intent(getApplicationContext(), LoginTypeActivity.class));
                finish();
            }
        }, 2000);
    }
    public static void enableLoc(Context cxt) {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(cxt)
                    .addApi(LocationServices.API)
                    .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                        @Override
                        public void onConnected(Bundle bundle) {
                            if (ActivityCompat.checkSelfPermission(cxt, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                                    && ActivityCompat.checkSelfPermission(cxt, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                return;
                            }
                        }
                        @Override
                        public void onConnectionSuspended(int i) {
                            googleApiClient.connect();
                        }
                    })
                    .addOnConnectionFailedListener(connectionResult -> Log.d("Location error", "Location error " + connectionResult.getErrorCode())).build();
            googleApiClient.connect();
            com.google.android.gms.location.LocationRequest locationRequest = com.google.android.gms.location.LocationRequest.create();
            locationRequest.setPriority(com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY);
            locationRequest.setInterval(30 * 1000);
            locationRequest.setFastestInterval(5 * 1000);
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(locationRequest);
            builder.setAlwaysShow(true);
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
            result.setResultCallback(result1 -> {
                final Status status = result1.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
//                        dialogShown = true;

                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(((Activity) cxt), REQUEST_LOCATION);
//                            dialogShown = true;
//                                finish();
                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        // Location settings are not satisfied. However, we have no way to fix the
                        // settings so we won't show the dialog.
//                        dialogShown = false;
                        break;
                }
            });
        }
    }
    @Override
    protected void onPause() {
        super.onPause();
        ispaused = true;
    }

}