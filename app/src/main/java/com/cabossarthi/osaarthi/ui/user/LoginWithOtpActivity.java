package com.cabossarthi.osaarthi.ui.user;
import static com.cabossarthi.osaarthi.OssarthiApplication.device_id;
import static com.cabossarthi.osaarthi.OssarthiApplication.device_name;
import static com.cabossarthi.osaarthi.OssarthiApplication.device_os;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
 import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cabossarthi.osaarthi.R;
import com.cabossarthi.osaarthi.model.MobileRegistrationModel;
import com.cabossarthi.osaarthi.model.MobileVerifyResponseModel;
import com.cabossarthi.osaarthi.services.ApiClient;
import com.cabossarthi.osaarthi.services.ServiceApi;
import com.cabossarthi.osaarthi.session.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.gson.Gson;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class LoginWithOtpActivity extends AppCompatActivity implements  GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener{
    ProgressBar loginProgressBar;
    AppCompatButton login;
    EditText mobileNo;
    String type;
    String otp ;
    FusedLocationProviderClient mFusedLocationClient;
    int PERMISSION_ID = 44;
    public static String latitude,longitude;
    String FirebaseUserId="";
    GoogleApiClient mGoogleApiClient;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_with_otp);
        otp = new DecimalFormat("0000").format(new Random().nextInt(9999));
        type = getIntent().getStringExtra("type");
        loginProgressBar = findViewById(R.id.login_loader);
        login = findViewById(R.id.btn_otp_login);
        mobileNo = findViewById(R.id.et_mobile);
         mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
         try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        Toast.makeText(getApplicationContext(), type, Toast.LENGTH_SHORT).show();

        mobileNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (validateMobile(mobileNo.getText().toString())) {
                    login.setEnabled(true);
                } else {
                    login.setEnabled(false);
                    mobileNo.setError("invalid mobile no");
                    mobileNo.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        login.setOnClickListener(v -> {
            if (mobileNo.getText().toString().length() == 0) {
                mobileNo.setError("field can't be empty");
                mobileNo.requestFocus();
            } else {
                callUserLoginApi(mobileNo.getText().toString().trim());
            }
        });
    }
    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        Toast.makeText(getApplicationContext(), "requestNewLocationData", Toast.LENGTH_SHORT).show();
                        requestNewLocationData();
                    } else {
//                            latitudeTextView.setText(location.getLatitude() + "");
//                            longitTextView.setText(location.getLongitude() + "");
                        latitude= String.valueOf(location.getLatitude());
                        longitude= String.valueOf(location.getLongitude());
//                        Toast.makeText(getApplicationContext(), latitude, Toast.LENGTH_SHORT).show();
                        SessionManager.getInstance(getApplicationContext()).saveCurrentLocation(latitude,longitude);
                        Geocoder geocoder;
                        List<Address> addresses;
                        geocoder = new Geocoder(LoginWithOtpActivity.this, Locale.getDefault());

                        try {
                            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                            // Here 1 represent max location result to returned, by documents it recommended 1 to 5

                         } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }
    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
     LocationRequest mLocationRequest   = LocationRequest.create()
                .setInterval(100)
                .setFastestInterval(3000)
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setMaxWaitTime(100);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

    }
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }
    private void callUserLoginApi(String MobileNo) {
        loginProgressBar.setVisibility(View.VISIBLE);
        login.setVisibility(View.GONE);
        MobileRegistrationModel mobileRegistrationModel = new MobileRegistrationModel();
        mobileRegistrationModel.setPhoneNumber(MobileNo);
        mobileRegistrationModel.setOtp(otp);
        mobileRegistrationModel.setFirebaseUserId("");
        mobileRegistrationModel.setFirebaseToken(SessionManager.getInstance(getApplicationContext()).getFirebaseToken());
        mobileRegistrationModel.setRole(type);
        mobileRegistrationModel.setBrowser("browser");
        mobileRegistrationModel.setDeviceID("device_id");
        mobileRegistrationModel.setDeviceName("device_name");
        mobileRegistrationModel.setDeviceOS("device_os");
        mobileRegistrationModel.setLatitude(latitude);
        mobileRegistrationModel.setLongitude(longitude);
        mobileRegistrationModel.setIpAddress("ip");
        ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
        Call<MobileVerifyResponseModel> call = api.mobileregistration(mobileRegistrationModel);
        call.enqueue(new Callback<MobileVerifyResponseModel>() {
            @Override
            public void onResponse(@NonNull Call<MobileVerifyResponseModel> call, @NonNull Response<MobileVerifyResponseModel> response) {
                if (response.isSuccessful()) {
                    Log.e("TAG", "onResponse: " + new Gson().toJson(response.body().getFirstName()));
                      Intent intent = new Intent(getApplicationContext(), UserOtpVerificationActivity.class);
                      intent.putExtra("mobile",response.body().getPhoneNumber());
                      intent.putExtra("otp",response.body().getOtp());
                      intent.putExtra("status",response.body().getStatus());
                      intent.putExtra("first_name",response.body().getFirstName());
                      intent.putExtra("firebase_user_id",response.body().getFirebaseUserId());
                      intent.putExtra("firebase_token",response.body().getFirebaseToken());
                      intent.putExtra("last_name",response.body().getLastName());
                      intent.putExtra("email",response.body().getEmail());
                      intent.putExtra("id",response.body().getId());
                      intent.putExtra("type",type);
                      startActivity(intent);
                      finish();
                      if (response.body().getStatus().equals("Exists")){
                          Intent intents = new Intent(getApplicationContext(), UserOtpVerificationActivity.class);
                          intents.putExtra("mobile",response.body().getPhoneNumber());
                          intents.putExtra("otp",response.body().getOtp());
                          intents.putExtra("status",response.body().getStatus());
                          intents.putExtra("first_name",response.body().getFirstName());
                          intents.putExtra("firebase_user_id",response.body().getFirebaseUserId());
                          intents.putExtra("firebase_token",response.body().getFirebaseToken());
                          intents.putExtra("last_name",response.body().getLastName());
                          intents.putExtra("email",response.body().getEmail());
                          intents.putExtra("type",type);
                          intents.putExtra("id",response.body().getId());
                          startActivity(intent);
                          finish();
                      }
                }
            }

            @Override
            public void onFailure(@NonNull Call<MobileVerifyResponseModel> call, @NonNull Throwable t) {
                loginProgressBar.setVisibility(View.GONE);
                login.setVisibility(View.VISIBLE);
                call.cancel();
            }
        });

    }

    private boolean validateMobile(String mobileNo) {
        Pattern p = Pattern.compile("[6-9][0-9]{9}");
        Matcher m = p.matcher(mobileNo);
        return m.matches();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}