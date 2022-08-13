package com.cabossarthi.osaarthi.ui.user;

import static com.cabossarthi.osaarthi.OssarthiApplication.device_id;
import static com.cabossarthi.osaarthi.OssarthiApplication.device_name;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.AlertDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cabossarthi.osaarthi.R;
 import com.cabossarthi.osaarthi.model.MobileVerifyModel;
import com.cabossarthi.osaarthi.model.MobileVerifyResponseModel;
import com.cabossarthi.osaarthi.services.ApiClient;
import com.cabossarthi.osaarthi.services.ServiceApi;
import com.cabossarthi.osaarthi.session.SessionManager;
import com.skydoves.elasticviews.ElasticButton;

import java.text.DateFormat;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserOtpVerificationActivity extends AppCompatActivity implements LocationListener {
    ElasticButton otpVerification;
    String type, MobileNo, result, otp;
    TextView manualOtp, mobile_no;
    String status;
    ProgressBar loader;
    String firebase_user_id, first_name;
    String id = "";
    final String TAG = "GPS";
    private final static int ALL_PERMISSIONS_RESULT = 101;
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10;
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1;

     LocationManager locationManager;
    Location loc;
    ArrayList<String> permissions = new ArrayList<>();
    ArrayList<String> permissionsToRequest;
    ArrayList<String> permissionsRejected = new ArrayList<>();
    boolean isGPS = false;
    boolean isNetwork = false;
    boolean canGetLocation = true;
    String latitude="0.0",longitude="0.0";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_otp_verification);
        loader = findViewById(R.id.loader);
        manualOtp = findViewById(R.id.tv_manual_otp);
        mobile_no = findViewById(R.id.tv_mobile_no);
        id = getIntent().getStringExtra("id");
        type = getIntent().getStringExtra("type");
        MobileNo = getIntent().getStringExtra("mobile");
        result = getIntent().getStringExtra("result");
        otp = getIntent().getStringExtra("otp");
        mobile_no.setText("Please check your message and ente verification code we just send you +91-" + MobileNo);
        manualOtp.setText("sms gateway is under maintenance so please enter this otp " + otp);
        otpVerification = findViewById(R.id.btn_verify_otp);
        status = getIntent().getStringExtra("status");
        first_name = getIntent().getStringExtra("first_name");
        Log.e(TAG, "onCreate: sadasdasd"+SessionManager.getInstance(getApplicationContext()).getLattitude());
        locationManager = (LocationManager) getSystemService(Service.LOCATION_SERVICE);
        isGPS = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        isNetwork = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
        permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (!isGPS && !isNetwork) {
            Log.d(TAG, "Connection off");
            showSettingsAlert();
            getLastLocation();
        } else {
            Log.d(TAG, "Connection on");
            // check permissions
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (permissionsToRequest.size() > 0) {
                    requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]),
                            ALL_PERMISSIONS_RESULT);
                    Log.d(TAG, "Permission requests");
                    canGetLocation = false;
                }
            }

            // get location
            getLocation();
        }



        otpVerification.setOnClickListener(v -> {
           functionOtp();
        });
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        updateUI(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {}

    @Override
    public void onProviderEnabled(String s) {
        getLocation();
    }

    @Override
    public void onProviderDisabled(String s) {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    private void getLocation() {
        try {
            if (canGetLocation) {
                Log.d(TAG, "Can get location");
                if (isGPS) {
                    // from GPS
                    Log.d(TAG, "GPS on");
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else if (isNetwork) {
                    // from Network Provider
                    Log.d(TAG, "NETWORK_PROVIDER on");
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (locationManager != null) {
                        loc = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (loc != null)
                            updateUI(loc);
                    }
                } else {
                    loc.setLatitude(0);
                    loc.setLongitude(0);
                    updateUI(loc);
                }
            } else {
                Log.d(TAG, "Can't get location");
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    private void getLastLocation() {
        try {
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, false);
            Location location = locationManager.getLastKnownLocation(provider);
            Log.d(TAG, provider);
            Log.d(TAG, location == null ? "NO LastLocation" : location.toString());
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }
    private ArrayList findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList result = new ArrayList();

        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    private boolean hasPermission(String permission) {
        if (canAskPermission()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
            }
        }
        return true;
    }
    private boolean canAskPermission() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                Log.d(TAG, "onRequestPermissionsResult");
                for (String perms : permissionsToRequest) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms);
                    }
                }

                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            showMessageOKCancel("These permissions are mandatory for",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(permissionsRejected.toArray(
                                                        new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                            }
                                        }
                                    });
                            return;
                        }
                    }
                } else {
                    Log.d(TAG, "No rejected permissions.");
                    canGetLocation = true;
                    getLocation();
                }
                break;
        }
    }
    public void showSettingsAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("GPS is not Enabled!");
        alertDialog.setMessage("Do you want to turn on GPS?");
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        });

        alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        alertDialog.show();
    }
    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(UserOtpVerificationActivity.this)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }
    private void updateUI(Location loc) {
        Log.d(TAG, "updateUI");
        latitude = Double.toString(loc.getLatitude());
        longitude=Double.toString(loc.getLongitude());
     }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }
    private void functionOtp(){
        loader.setVisibility(View.VISIBLE);
        otpVerification.setVisibility(View.GONE);
        MobileVerifyModel mobileVerifyModel = new MobileVerifyModel();
        mobileVerifyModel.setOtp(otp);
        mobileVerifyModel.setPhoneNumber(MobileNo);
        mobileVerifyModel.setBrowser("APP");
        mobileVerifyModel.setDeviceID(Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID));
        mobileVerifyModel.setDeviceName(android.os.Build.MODEL);
        mobileVerifyModel.setFirebaseToken("");
        mobileVerifyModel.setLatitude(latitude);
        mobileVerifyModel.setLongitude(longitude);
        mobileVerifyModel.setFirebaseUserId("");
        mobileVerifyModel.setIpAddress("ip");
        mobileVerifyModel.setRole(type);
        mobileVerifyModel.setDeviceOS("android");
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<MobileVerifyResponseModel> call = api.mobileverify(mobileVerifyModel);
        call.enqueue(new Callback<MobileVerifyResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<MobileVerifyResponseModel> call,
                                   @NonNull Response<MobileVerifyResponseModel> response) {
                if (response.isSuccessful()) {
                    SessionManager.getInstance(getApplicationContext()).saveJwtToken(response.body().getJwtToken());
                    if (status.equals("Exists")) {
                        Log.e(TAG, "onResponse: "+ response.body().getRoles());
                        if (first_name != null && !first_name.isEmpty()) {
                            SessionManager.getInstance(getApplicationContext())
                                    .saveUserLogin(
                                            response.body().getFirstName()
                                                    + " " + response.body().getLastName(),
                                            response.body().getPhoneNumber(),
                                            response.body().getEmail(),
                                            response.body().getId(),
                                            type,
                                            response.body().getFirebaseUserId(),
                                            response.body().getFirebaseToken());
                            Intent intent = new Intent(getApplicationContext(),UserDashboardActivity.class);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        } else {
                            Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                            intent.putExtra("mobileNo", response.body().getPhoneNumber());
                            intent.putExtra("type", type);
                            intent.putExtra("id", id);
                            startActivity(intent);
                        }
                    } else if (status.equals("new")) {
                        Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                        intent.putExtra("mobileNo", response.body().getPhoneNumber());
                        intent.putExtra("type", type);
                        intent.putExtra("id", id);
                        startActivity(intent);
                    }
                } else {
                    loader.setVisibility(View.GONE);
                    otpVerification.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(@NonNull Call<MobileVerifyResponseModel> call, @NonNull Throwable t) {
                call.cancel();
                loader.setVisibility(View.GONE);
                otpVerification.setVisibility(View.VISIBLE);
            }
        });
    }
}