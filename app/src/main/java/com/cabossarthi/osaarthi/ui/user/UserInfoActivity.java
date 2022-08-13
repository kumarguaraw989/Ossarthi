package com.cabossarthi.osaarthi.ui.user;

import static com.cabossarthi.osaarthi.services.Urls.BASE_URL;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.cabossarthi.osaarthi.R;
import com.cabossarthi.osaarthi.model.RegistationModel;
import com.cabossarthi.osaarthi.model.RegistrationResponseModel;
import com.cabossarthi.osaarthi.services.ApiClient;
import com.cabossarthi.osaarthi.services.ApiClient2;
import com.cabossarthi.osaarthi.services.ServiceApi;
import com.cabossarthi.osaarthi.session.SessionManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.skydoves.elasticviews.ElasticButton;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserInfoActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener {
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference storeDefaultDatabaseReference;
    EditText first_name, last_name, email, password, cnfPassword;
    String mobileNo, type;
    ElasticButton signup;
    ProgressBar loader;
    protected LocationManager locationManager;
    LocationListener locationListener;
    private String android_id, device_name;
    String id = "";
    SessionManager sessionManager;
    @RequiresApi(api = Build.VERSION_CODES.O)
    double lat=0.0, lng=0.0;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        device_name = android.os.Build.MODEL;
        id = getIntent().getStringExtra("id");
        android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        mobileNo = getIntent().getStringExtra("mobileNo").trim();
        type = getIntent().getStringExtra("type").trim();
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        first_name = findViewById(R.id.et_f_name);
        last_name = findViewById(R.id.et_last_name);
        loader = findViewById(R.id.loader);
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);
        cnfPassword = findViewById(R.id.et_confirm_password);
        signup = findViewById(R.id.btn_submit);
        callAllPermissions();

        //locations code
        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {

            }
        };


        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);


        signup.setOnClickListener(v -> {
            if (TextUtils.isEmpty(first_name.getText().toString().trim())) {
                first_name.setError("field can't be empty");
                first_name.requestFocus();
            }
            if (TextUtils.isEmpty(last_name.getText().toString().trim())) {
                last_name.setError("field can't be empty");
                last_name.requestFocus();
            } else if (email.getText().toString().length() == 0) {
                email.setError("field can't be empty");
                email.requestFocus();
            } else if (password.getText().toString().length() == 0 && password.getText().toString().length()<8) {
                password.setError("password is not valid");
                password.requestFocus();
            } else if (cnfPassword.getText().toString().length() == 0 && !cnfPassword.getText().toString().equals(password.getText().toString())) {
                cnfPassword.setError("confirm password is not valid");
                cnfPassword.requestFocus();
            } else {
                if (isNetworkConnected()){
                    callSignupApi(first_name.getText().toString().trim(), email.getText().toString().trim(),
                            password.getText().toString().trim(),
                            cnfPassword.getText().toString().trim());
                }else{
                    Toasty.error(UserInfoActivity.this, "no internet is there...", Toast.LENGTH_SHORT, true).show();

                }

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void callAllPermissions() {
        Dexter.withContext(this)
                .withPermissions(
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE,
                        Manifest.permission.ANSWER_PHONE_CALLS
                ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                checkLocationStatus();
            }

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }
        }).check();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onLocationChanged(@NonNull Location location) {
        {
            double latitude = location.getLatitude();
            double longitude = location.getLongitude();
            try {
                Geocoder geocoder;
                List<Address> addresses;
                geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                addresses = geocoder.getFromLocation(latitude, longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                String pincode = addresses.get(0).getPostalCode();
                String locationss = addresses.get(0).getAddressLine(0);
                lat =  latitude;
                lng = longitude;
                Log.e("TAG", ": " + locationss);

                if (pincode != null) {
                    locationManager.removeUpdates(this);
                }

            } catch (Exception exception) {
                Log.e("TAG", "onLocationChanged: " + exception.getLocalizedMessage());
            }
        }
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

    @Override
    public void onMapClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }

    protected void createLocationRequest() {
        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(5000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    /* ActivityResultLauncher<String[]> locationPermissionRequest =
             registerForActivityResult(new ActivityResultContracts
                             .RequestMultiplePermissions(), result -> {
                         Boolean fineLocationGranted = result.getOrDefault(
                                 Manifest.permission.ACCESS_FINE_LOCATION, false);
                 Boolean coarseLocationGranted = null;
                 if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                     coarseLocationGranted = result.getOrDefault(
                             Manifest.permission.ACCESS_COARSE_LOCATION, false);
                 }

                     }
             );*/
    private void buildAlertMessageNoGps() {
        try {
            final AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(final DialogInterface dialog, final int id) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                            finish();
                            System.exit(0);
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = builder.create();
            alert.show();
        } catch (Exception exception) {
        }
    }

    @SuppressLint("MissingPermission")
    void checkLocationStatus() {
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, this);
        Dexter.withActivity(this)
                .withPermissions(
                        /*Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA,*/
                        Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        // check if all permissions are granted
                        if (report.areAllPermissionsGranted()) {
                            // do you work now
                            //Toast.makeText(Startscreen.this, " Granted "+pincode+" PIN_CODE ", Toast.LENGTH_SHORT).show();

                        }

                        // check for permanent denial of any permission
                        if (report.isAnyPermissionPermanentlyDenied()) {
                            // permission is denied permenantly, navigate user to app settings
                            //Toast.makeText(Startscreen.this, " DENIED ", Toast.LENGTH_SHORT).show();
                            checkLocationStatus();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                })
                .onSameThread()
                .check();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void callSignupApi(String Name, String Email, String Password, String cnfPassword) {
        loader.setVisibility(View.VISIBLE);
        signup.setVisibility(View.GONE);
        //gooogle firebase login
        mAuth.createUserWithEmailAndPassword(Email, Password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String deviceToken = SessionManager.getInstance(getApplicationContext()).getREGiD();

                        // get and link storage
                        String current_userID = mAuth.getCurrentUser().getUid();
                        if (!current_userID.isEmpty()) {
//                            model.setFirbaseId(current_userID);
                        }
                        storeDefaultDatabaseReference = FirebaseDatabase.getInstance().getReference().child("users").child(current_userID);
                        storeDefaultDatabaseReference.child("user_name").setValue(Name);
                        storeDefaultDatabaseReference.child("verified").setValue("true");
                        storeDefaultDatabaseReference.child("search_name").setValue(Name.toLowerCase());
                        storeDefaultDatabaseReference.child("user_mobile").setValue(mobileNo.trim().toLowerCase());
                        storeDefaultDatabaseReference.child("user_email").setValue(Email.toLowerCase());
                        storeDefaultDatabaseReference.child("user_nickname").setValue(Email.trim().toLowerCase());
                        storeDefaultDatabaseReference.child("user_pass").setValue(cnfPassword.trim());
                        storeDefaultDatabaseReference.child("type").setValue(type.trim());
                        storeDefaultDatabaseReference.child("user_gender").setValue("");
                        storeDefaultDatabaseReference.child("user_profession").setValue("");
                        storeDefaultDatabaseReference.child("created_at").setValue(ServerValue.TIMESTAMP);
                        storeDefaultDatabaseReference.child("active_now").setValue(ServerValue.TIMESTAMP);
                        storeDefaultDatabaseReference.child("user_status").setValue("Hi, I'm new ossarthi user");
                        storeDefaultDatabaseReference.child("user_image").setValue("default_image"); // Original image
                        storeDefaultDatabaseReference.child("device_token").setValue(deviceToken);
                        storeDefaultDatabaseReference.child("user_thumb_image").setValue("default_image");
                        storeDefaultDatabaseReference.child("latitude").setValue(lat);
                        storeDefaultDatabaseReference.child("longitude").setValue(lng)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        // SENDING VERIFICATION EMAIL TO THE REGISTERED USER'S EMAIL
                                        user = mAuth.getCurrentUser();
                                        if (user != null) {
                                            mAuth.signOut();
                                            RegistationModel registationModel = new RegistationModel();
                                            registationModel.setId(id);
                                            registationModel.setFirstName(first_name.getText().toString());
                                            registationModel.setLastName(last_name.getText().toString());
                                            registationModel.setEmail(email.getText().toString());
                                            registationModel.setPassword(password.getText().toString());
                                            registationModel.setConfirmPassword(cnfPassword);
                                            registationModel.setFirebaseToken(SessionManager.getInstance(getApplicationContext()).getFirebaseToken());
                                            registationModel.setFirebaseUserId(current_userID);
                                            ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
//                                            ServiceApi serviceApi =  ApiClient2.getClient().create(ServiceApi.class);
                                            Call<RegistrationResponseModel> call = api.registration(registationModel);
                                            call.enqueue(new Callback<RegistrationResponseModel>() {
                                                @Override
                                                public void onResponse(@NonNull Call<RegistrationResponseModel> call, @NonNull Response<RegistrationResponseModel> response) {
                                                    if (response.isSuccessful()) {
                                                        assert response.body() != null;
                                                        if (!response.body().getId().isEmpty()) {
                                                            SessionManager.getInstance(getApplicationContext()).saveUserLogin(response.body().getFirstName() + " " + response.body().getLastName(),
                                                                    response.body().getPhoneNumber(), response.body().getEmail(), response.body().getId(), response.body().getRoles().get(0), response.body().getFirebaseUserId(), response.body().getFirebaseToken());
                                                            startActivity(new Intent(getApplicationContext(), UserDashboardActivity.class));
                                                            finish();
                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onFailure(@NonNull Call<RegistrationResponseModel> call, Throwable t) {
                                                    call.cancel();
                                                }
                                            });
                                        }

                                    } else {
                                        signup.setVisibility(View.VISIBLE);
                                        loader.setVisibility(View.GONE);
                                        Log.d("fire2", task.getException().getMessage());
                                        Toasty.info(getApplicationContext(), task.getException().getMessage().toString().trim(), Toasty.LENGTH_SHORT).show();
                                    }
                                });

                    } else {
                        signup.setVisibility(View.VISIBLE);
                        loader.setVisibility(View.GONE);
                        Log.d("fire", task.getException().getMessage());
                        Toasty.info(getApplicationContext(), task.getException().getMessage().toString().trim(), Toasty.LENGTH_SHORT).show();
                    }
                });
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}