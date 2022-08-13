package com.cabossarthi.osaarthi.ui.user;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cabossarthi.osaarthi.R;
import com.cabossarthi.osaarthi.adapter.CabListAdapter;
import com.cabossarthi.osaarthi.adapter.VehicleDetailsAdapter;
import com.cabossarthi.osaarthi.gmap.SelectDropLocationActivity;
import com.cabossarthi.osaarthi.gmap.SelectPickUpLocationActivity;
import com.cabossarthi.osaarthi.mapsession.DropLocationPreference;
import com.cabossarthi.osaarthi.mapsession.PickupPreferenceManager;
import com.cabossarthi.osaarthi.mapsession.PrefManager;
import com.cabossarthi.osaarthi.model.BookRidePostModal;
import com.cabossarthi.osaarthi.model.BookRideResponseModal;
import com.cabossarthi.osaarthi.model.CabListModel;
import com.cabossarthi.osaarthi.model.VehicleCatogeryModal;
import com.cabossarthi.osaarthi.model.VehicleCatogeryResponseModal;
import com.cabossarthi.osaarthi.model.VehicleDetailsModal;
import com.cabossarthi.osaarthi.model.VehicleDetailsResponseModal;
import com.cabossarthi.osaarthi.services.ApiClient;
import com.cabossarthi.osaarthi.services.ServiceApi;
import com.cabossarthi.osaarthi.session.SessionManager;
import com.cabossarthi.osaarthi.ui.LoginTypeActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import com.maps.route.extensions.MapExtensionKt;
import com.maps.route.model.TravelMode;
import com.sasank.roundedhorizontalprogress.RoundedHorizontalProgressBar;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticFloatingActionButton;
import com.skydoves.elasticviews.ElasticImageView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserDashboardActivity extends AppCompatActivity implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener, NavigationView.OnNavigationItemSelectedListener {
    Geocoder geocoder;
    List<String> vehicleCatogeryModalArrayList = new ArrayList<>();
    boolean status = false;
    boolean isRain = false;
    String firebaseUserId = "";
    String BookingStartTime;
    String BookingEndTime;
    TextView mainDistance;
    String BookingScheduleTime;
    GoogleApiClient mGoogleApiClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    double pickLat, pickLong, desLat, desLng;
    TextView pickupLocation, dropLocation;
    String picAdd = "";
    String destAddress = "";
    ElasticFloatingActionButton floatingActionButton;
    CabListAdapter cabListAdapter;
    VehicleDetailsAdapter vehicleDetailsAdapter;
    RecyclerView cabListRecyclerView, recyclerViewDetails;
    RelativeLayout pickRl, dropRl;
    private int mYear, mMonth, mDay, mHour, mMinute;
    List<CabListModel> cabListModelListss = new ArrayList<>();
    private GoogleMap mMap;
    boolean IsScreenMove = false;
    RelativeLayout rideDetailsLayout;
    RoundedHorizontalProgressBar roundedHorizontalProgressBar;
    private String TAG;
    private String PickUpAddress = "";
    private ProgressBar loader;
    private TextView MovableLocation;
    private View locationButton;
    DrawerLayout drawer;
    private View navHeader;
    NavigationView navigationView;
    ElasticImageView menuImage;
    String dropid, pickup_id;
    ElasticButton bookRideButton;
    AppCompatButton confirmBooking;
    String dropLtLng;
    String source_address = "", destination_address = "";
    int vehicle_cat_id;
    double source_lat, source_lng, dest_lat, dest_lng;
    double distanceReal = 0.0;
    SimpleDateFormat simpleDateFormat;
    String time;
    Calendar calander;
    int vehiclePriceId = 0;
    ProgressBar vehicleLoaderprogressBar, bookRideprogressBar;
    Button scheduleTime;
    String totalPrice = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home);
        bookRideprogressBar = findViewById(R.id.my_progressBar);
        confirmBooking = findViewById(R.id.btn_cnf_booking);
         vehicleLoaderprogressBar = findViewById(R.id.vahicle_loader_pb);
        bookRideprogressBar.setProgress(20);
        bookRideprogressBar.setSecondaryProgress(50);
        bookRideprogressBar.setIndeterminate(true);
        vehicleLoaderprogressBar.setProgress(20);
        vehicleLoaderprogressBar.setSecondaryProgress(50);
        vehicleLoaderprogressBar.setIndeterminate(true);
        bookRideButton = findViewById(R.id.btn_req_aRide);
        rideDetailsLayout = findViewById(R.id.rl_ride_details);

        pickup_id = getIntent().getStringExtra("pickup_id");
        if (pickup_id == null) {
            pickup_id = "0";
        }
        dropid = getIntent().getStringExtra("drop_id");
        TAG = this.getClass().getCanonicalName();
        menuImage = findViewById(R.id.iv_menu);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);
        navHeader = navigationView.getHeaderView(0);
        navigationView.setItemIconTintList(null);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView name, email;
        name = headerView.findViewById(R.id.nav_header_name);
        email = headerView.findViewById(R.id.nav_header_emailaddress);
        menuImage.setOnClickListener(v -> {
            drawer.openDrawer(Gravity.LEFT);
        });
        Log.e(TAG, "onCreate: " + SessionManager.getInstance(getApplicationContext()).getNAME());
        name.setText(SessionManager.getInstance(UserDashboardActivity.this).getNAME());
        email.setText(SessionManager.getInstance(UserDashboardActivity.this).getEMAIL());

        pickRl = findViewById(R.id.rl_pickup);
        dropRl = findViewById(R.id.rl_drop);
        cabListRecyclerView = findViewById(R.id.rv_cab_list);
        recyclerViewDetails = findViewById(R.id.rv_vehicle_details);
        floatingActionButton = findViewById(R.id.fab_getlocation);
        pickupLocation = findViewById(R.id.tv_picup_location);
        dropLocation = findViewById(R.id.tv_drop_location);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        MovableLocation = findViewById(R.id.tv_movable_location);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.setRetainInstance(true);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(UserDashboardActivity.this);
        if (ActivityCompat.checkSelfPermission(UserDashboardActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(UserDashboardActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        floatingActionButton.setOnClickListener(v -> {
            moveCurrent(mMap.getMyLocation());
        });

        pickRl.setOnClickListener(v -> {
            IsScreenMove = false;
            Intent intent = new Intent(UserDashboardActivity.this, SelectPickUpLocationActivity.class);
            intent.putExtra("pickup", "1");
            startActivity(intent);
        });

        dropRl.setOnClickListener(v -> {
            DropLocationPreference.getInstance(getApplicationContext()).cleanSession();
            Intent intent = new Intent(UserDashboardActivity.this, SelectDropLocationActivity.class);
            intent.putExtra("autoPlaceId", "2");
            startActivity(intent);
        });
        dropLocation.setText(DropLocationPreference.getInstance(getApplicationContext()).getPicupLocName());
        destination_address = DropLocationPreference.getInstance(getApplicationContext()).getPicupLocName();


        bookRideButton.setOnClickListener(v -> {
            Log.e(TAG, "onCreate: Drop Latlong" + dropLtLng);
//            Log.e(TAG, "onCreate: Drop Latlong" + desLat + "\n" + desLng);
            Log.e(TAG, "onCreate: Pickup Latlong" + pickLat + "\n" + pickLong);
        });
        confirmBooking.setOnClickListener(v -> {
//        bookARide();
            showBottomSheetDialog(totalPrice);
        });
    }


    private void moveCurrent(@NonNull Location location) {
//        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        if (location != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12.0f));
        }
    }

    @SuppressLint("MissingPermission")
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(UserDashboardActivity.this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(UserDashboardActivity.this,
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();
            if (location != null) {
                Geocoder geocoder = new Geocoder(UserDashboardActivity.this, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    PickUpAddress = addressList.get(0).getAddressLine(0);
                    source_address = PickUpAddress;
                    getVehicleCategoryList(PickUpAddress);
                } catch (IOException ioException) {

                }
            }
        });
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {

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

    @SuppressLint({"ObsoleteSdkInt", "ResourceType"})
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
//        mMap.setMapStyle(MapStyleOptions.loadRawResourceStyle(
//                this, R.raw.map_style));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(UserDashboardActivity.this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient();
                mMap.setMyLocationEnabled(true);
            }
        } else {
            buildGoogleApiClient();
            mMap.setMyLocationEnabled(true);
        }
        // get your maps fragment
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        // Extract My Location View from maps fragment
        locationButton = mapFragment.getView().findViewById(0x2);
        // Change the visibility of my location button
        if (locationButton != null)
            locationButton.setVisibility(View.GONE);
        mMap.setOnMapClickListener(this);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        geocoder = new Geocoder(UserDashboardActivity.this, Locale.getDefault());
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();
            if (location != null) {
                Geocoder geocoder = new Geocoder(UserDashboardActivity.this, Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    PickUpAddress = addressList.get(0).getAddressLine(0);
                    source_address = PickUpAddress;
                    getVehicleCategoryList(PickUpAddress);

                    try {
                        List<Address> addresses = geocoder.getFromLocationName(PickUpAddress, 1);

                        if (addresses.size() > 0) {
                            Address address = addresses.get(0);
                            LatLng india = new LatLng(address.getLatitude(), address.getLongitude());
                            MarkerOptions markerOptions = new MarkerOptions()
                                    .position(india)
                                    .title(address.getLocality()).draggable(true);
                            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 12.0f));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(india, 15));
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });
        mMap.setOnCameraChangeListener(cameraPosition -> {
//            Log.e(TAG, "onMapReady: "+pickup_id);
            try {
                Geocoder geocoder = new Geocoder(UserDashboardActivity.this, Locale.getDefault());
                List<Address> addressList = geocoder.getFromLocation(cameraPosition.target.latitude, cameraPosition.target.longitude, 1);

                if (addressList.size() > 0) {
                    Address address = addressList.get(0);
                    LatLng india = new LatLng(address.getLatitude(), address.getLongitude());
                    String mainAddress = addressList.get(0).getAddressLine(0);
                    if (mainAddress != null) {
                        MovableLocation.setText(mainAddress);
                        if (pickup_id.equals("1")) {
                            IsScreenMove = true;
                            pickup_id = "0";
//                                 Toast.makeText(this, "pickup_id1", Toast.LENGTH_SHORT).show();
                            pickupLocation.setText(PickupPreferenceManager.getInstance(getApplicationContext()).getPicupLocName());
                            picAdd = PickupPreferenceManager.getInstance(getApplicationContext()).getPicupLocName();
                            String picLatLng = PickupPreferenceManager.getInstance(UserDashboardActivity.this).getPicupLatLng();
                            if (picLatLng != null) {
                                String[] parts1 = picLatLng.split(",");
                                pickLat = Double.parseDouble(parts1[0]);
                                pickLong = Double.parseDouble(parts1[1]);
                                pickupLocation.setText(PickupPreferenceManager.getInstance(UserDashboardActivity.this).getPicupLocName());
                            }
                        } else if (pickup_id.equals("0")) {
                            pickupLocation.setText(mainAddress);
                            picAdd = mainAddress;
                            try {
                                if (IsScreenMove) {
//                                         Toast.makeText(this, "pickup_id0", Toast.LENGTH_SHORT).show();
                                    IsScreenMove = true;
                                    pickupLocation.setText(mainAddress);
                                    picAdd = mainAddress;
                                    Log.e(TAG, "onMapReady: " + cameraPosition.target.latitude + "\n" + cameraPosition.target.longitude);
                                    PrefManager.getInstance(UserDashboardActivity.this).savePicupLocation(cameraPosition.target.latitude + "," + cameraPosition.target.longitude, mainAddress);
                                } else {
                                    if (pickup_id != null) {
                                        IsScreenMove = true;
                                        pickupLocation.setText(PrefManager.getInstance(UserDashboardActivity.this).getPicupLocName());
//                                    destination.setText(PrefManager.getInstance(UserDashboardActivity.this).getDropLocName());
                                        picAdd = PrefManager.getInstance(UserDashboardActivity.this).getPicupLocName();

                                    } else {
                                        IsScreenMove = true;
                                        pickupLocation.setText(mainAddress);
                                        PrefManager.getInstance(UserDashboardActivity.this).savePicupLocation(cameraPosition.target.latitude + "," + cameraPosition.target.longitude, mainAddress);

                                    }
                                }
                            } catch (Exception ignore) {
                            }
                            String picLatLng = PrefManager.getInstance(UserDashboardActivity.this).getPicupLatLng();
                            if (picLatLng != null) {
                                String[] parts1 = picLatLng.split(",");
                                pickLat = Double.parseDouble(parts1[0]);
                                pickLong = Double.parseDouble(parts1[1]);
                                pickupLocation.setText(PrefManager.getInstance(UserDashboardActivity.this).getPicupLocName());
                                picAdd = PrefManager.getInstance(UserDashboardActivity.this).getPicupLocName();
                            }
                        }
                    }
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(UserDashboardActivity.this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_profile:
                startActivity(new Intent(UserDashboardActivity.this, ProfileActivity.class));
                break;
            case R.id.nav_your_rides:
                startActivity(new Intent(UserDashboardActivity.this, UserBookingListActivity.class));
                break;
            case R.id.nav_logout:
                SessionManager.getInstance(getApplicationContext()).logout();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(UserDashboardActivity.this, LoginTypeActivity.class));
                break;
        }
        return true;
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

    private void getVehicleCategoryList(String sourceAddress) {
        if (isNetworkConnected()){
            vehicleLoaderprogressBar.setVisibility(View.VISIBLE);
            Log.e(TAG, "getVehicleCategoryList: " + sourceAddress);
            Log.e(TAG, "getVehicleCategoryList: " + PickUpAddress);
            String[] parts1 = getLocationFromAddress(sourceAddress).split(",");
            pickLat = Double.parseDouble(parts1[0]);
            pickLong = Double.parseDouble(parts1[1]);

            VehicleCatogeryResponseModal vehicleCatogeryResponseModal = new VehicleCatogeryResponseModal();
            vehicleCatogeryResponseModal.setSourceAddress(sourceAddress);
            vehicleCatogeryResponseModal.setSourceLat(pickLat);
            vehicleCatogeryResponseModal.setSourceLong(pickLong);
            Log.e(TAG, "getVehicleCategoryList: " + sourceAddress);
            Log.e(TAG, "getVehicleCategoryList: " + pickLat);
            Log.e(TAG, "getVehicleCategoryList: " + pickLong);
            ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
            Call<VehicleCatogeryModal> call = api.getvehicleCatagory(vehicleCatogeryResponseModal);
            call.enqueue(new Callback<VehicleCatogeryModal>() {
                @Override
                public void onResponse(@NonNull Call<VehicleCatogeryModal> call, @NonNull Response<VehicleCatogeryModal> response) {
                    if (response.isSuccessful()) {
                        if (response.body() != null) {
                            if (response.body().getStatus()) {
                                vehicleLoaderprogressBar.setVisibility(View.GONE);
                                cabListAdapter = new CabListAdapter(getApplicationContext(), response.body().getVehicleCatList()
                                        , UserDashboardActivity.this, (vehicleCatogeryModal, pos, linearLayout, view, callFunction) -> {
                                    if (desLat == 0) {
                                        Toasty.error(UserDashboardActivity.this, "Please select drop location", Toast.LENGTH_SHORT, true).show();
                                    } else {
                                        getVehicleDetails(pos, vehicleCatogeryModal.getVehicleCategoryId());
                                    }
                                });
                                cabListRecyclerView.setLayoutManager(new LinearLayoutManager(UserDashboardActivity.this, LinearLayoutManager.HORIZONTAL, false));
                                cabListRecyclerView.setAdapter(cabListAdapter);
                            } else {
                                vehicleLoaderprogressBar.setVisibility(View.GONE);
                                Toast.makeText(UserDashboardActivity.this, "Something went wrong..", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            vehicleLoaderprogressBar.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleCatogeryModal> call, @NonNull Throwable t) {
                    call.cancel();
                    Toast.makeText(UserDashboardActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    vehicleLoaderprogressBar.setVisibility(View.GONE);
                }
            });
        }else{
            Toasty.success(UserDashboardActivity.this,"no internet connection...",1000).show();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        dropLtLng = DropLocationPreference.getInstance(UserDashboardActivity.this).getPicupLatLng();

        if (dropLtLng != null) {
            String[] parts2 = dropLtLng.split(",");
            desLat = Double.parseDouble(parts2[0]);
            desLng = Double.parseDouble(parts2[1]);
        }
        destination_address = DropLocationPreference.getInstance(getApplicationContext()).getPicupLocName();
        dropLocation.setText(DropLocationPreference.getInstance(getApplicationContext()).getPicupLocName());
    }

    private void getVehicleDetails(int position, int id) {
        googleDistanceApi(pickLat,pickLong,desLat,desLng);
            VehicleDetailsModal vehicleDetailsModal = new VehicleDetailsModal();
            vehicleDetailsModal.setDestinationLat(desLat);
            vehicleDetailsModal.setDestinationLong(desLng);
            vehicleDetailsModal.setSourceLat(pickLat);
            vehicleDetailsModal.setSourceLong(pickLong);
            vehicleDetailsModal.setVehicleCategoryId(id);
            vehicleDetailsModal.setDistance(distanceReal);
            vehicleDetailsModal.setDestinationAddress(destination_address);
            vehicleDetailsModal.setSourceAddress(source_address);
            vehicleDetailsModal.setDestinationLong((int) desLng);
            Log.e(TAG, "getVehicleDetails: " + (int) desLat);
            Log.e(TAG, "getVehicleDetails: " + (int) desLng);
            Log.e(TAG, "getVehicleDetails: " + (int) source_lat);
            Log.e(TAG, "getVehicleDetails: " + (int) source_lng);
            Log.e(TAG, "getVehicleDetails: " + (int) id);
            Log.e(TAG, "getVehicleDetails: " + destination_address);
            Log.e(TAG, "getVehicleDetails: " + source_address);
//        Log.e(TAG, "getVehicleDetails:ssss " + googleDistanceApi(pickLat, pickLat, desLat, desLng));
            ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
            Call<VehicleDetailsResponseModal> call = api.getvehicleDetails(vehicleDetailsModal);
            call.enqueue(new Callback<VehicleDetailsResponseModal>() {
                @Override
                public void onResponse(@NonNull Call<VehicleDetailsResponseModal> call, @NonNull Response<VehicleDetailsResponseModal> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        if (response.body().getStatus()) {
                            vehicleDetailsAdapter = new VehicleDetailsAdapter(getApplicationContext(), response.body().getVehiclePricesList()
                                    , UserDashboardActivity.this, (vehicleCatogeryModal, pos, linearLayout, view, callFunction) -> {
                                totalPrice = String.valueOf(vehicleCatogeryModal.getTotalFare());
                                SessionManager.getInstance(getApplicationContext()).saveTotalPrice(totalPrice);
                                isRain = vehicleCatogeryModal.getIsRain();
                                vehiclePriceId = vehicleCatogeryModal.getVehiclePriceId();

                            });
                            recyclerViewDetails.setLayoutManager(new LinearLayoutManager(UserDashboardActivity.this, LinearLayoutManager.VERTICAL, false));
                            recyclerViewDetails.setAdapter(vehicleDetailsAdapter);
                        }
                    }
                }

                @Override
                public void onFailure(@NonNull Call<VehicleDetailsResponseModal> call, @NonNull Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    private double googleDistanceApi(double s_lat, double s_lng, double d_lat, double d_lng) {
        Log.e(TAG, "googleDistanceApi: " + s_lat);
        Log.e(TAG, "googleDistanceApi: " + s_lng);
        Log.e(TAG, "googleDistanceApi: " + d_lat);
        Log.e(TAG, "googleDistanceApi: " + d_lng);
        Log.e(TAG, "googleDistanceApi: " + distanceReal);
            LatLng pik = new LatLng(s_lat, s_lng);
            LatLng dro = new LatLng(d_lat, d_lng);
            MapExtensionKt.getTravelEstimations(getString(R.string.google_maps_key), pik, dro, TravelMode.DRIVING, null,
                    (estimates -> {
                        distanceReal = estimates.getDistance().getValue() / 1000;
                        return null;
                    }));
    return  distanceReal;
    }

    private String getLocationFromAddress(String strAddress) {
        Log.e(TAG, "getLocationFromAddress: " + strAddress);

        Geocoder geocoder = new Geocoder(UserDashboardActivity.this,
                Locale.getDefault());
        String result = null;
        try {
            List addressList = geocoder.getFromLocationName(strAddress, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = (Address)
                        addressList.get(0);
                StringBuilder sb = new StringBuilder();
                sb.append(address.getLatitude()).append("\n");
                sb.append(address.getLongitude()).append("\n");
                result = address.getLatitude() + "," + address.getLongitude();
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to connect to Geocoder", e);
        }
        return result;
    }
    private String getLocationFromAddress1(String strAddress) {
        Log.e(TAG, "getLocationFromAddress: " + strAddress);
        Geocoder geocoder = new Geocoder(UserDashboardActivity.this,
                Locale.getDefault());
        String result = null;
        try {
            List addressList = geocoder.getFromLocationName(strAddress, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = (Address)
                        addressList.get(0);
                StringBuilder sb = new StringBuilder();
                sb.append(address.getLatitude()).append("\n");
                sb.append(address.getLongitude()).append("\n");
                result = address.getLatitude() + "," + address.getLongitude();
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to connect to Geocoder", e);
        }
        return result;
    }
    private String getLocationFromAddress2(String strAddress) {
        Log.e(TAG, "getLocationFromAddress: " + strAddress);
        Geocoder geocoder = new Geocoder(UserDashboardActivity.this,
                Locale.getDefault());
        String result = null;
        try {
            List addressList = geocoder.getFromLocationName(strAddress, 1);
            if (addressList != null && addressList.size() > 0) {
                Address address = (Address)
                        addressList.get(0);
                StringBuilder sb = new StringBuilder();
                sb.append(address.getLatitude()).append("\n");
                sb.append(address.getLongitude()).append("\n");
                result = address.getLatitude() + "," + address.getLongitude();
            }
        } catch (IOException e) {
            Log.e(TAG, "Unable to connect to Geocoder", e);
        }
        return result;
    }


    @SuppressLint("SetTextI18n")
    private void showBottomSheetDialog(String price) {
        double pLat = 0.0, pLng = 0.0, dLat = 0.0, dLng = 0.0;
        String picUpLoc = "", dropLoc = "", customerId = "";

//        double totalFare=0.0;
//
//        totalFare= Double.parseDouble(totalPrice);
        customerId = SessionManager.getInstance(UserDashboardActivity.this).getID();
        firebaseUserId = SessionManager.getInstance(getApplicationContext()).getFirebaseUserId();

        picUpLoc = pickupLocation.getText().toString();
        dropLoc = dropLocation.getText().toString();
             String[] parts1 = getLocationFromAddress1(picUpLoc).split(",");
            pLat = Double.parseDouble(parts1[0]);
            pLng = Double.parseDouble(parts1[1]);


            String[] parts2 = getLocationFromAddress2(dropLoc).split(",");
            dLat = Double.parseDouble(parts2[0]);
            dLng = Double.parseDouble(parts2[1]);
        MapExtensionKt.getTravelEstimations(getString(R.string.google_maps_key),new LatLng(pLat,pLng),new LatLng(dLat,dLng), TravelMode.DRIVING, null,
                //call the lambda if you need the estimates
                (estimates -> {
                    //Estimated time of arrival
//                        Log.e("estimatedTimeOfArrival", "withUnit " + Objects.requireNonNull(estimates.getDuration()).getText());
//                        Log.e("estimatedTimeOfArrival", "InMilliSec " + estimates.getDuration().getValue());
                    //Google suggested path distance
                    distanceReal = estimates.getDistance().getValue() / 1000;
//                   totalDistance.setText(Objects.requireNonNull(estimates.getDistance()).getText());
//                        Log.e("GoogleSuggestedDistance", "withUnit " + Objects.requireNonNull(estimates.getDistance()).getText());
//                        Log.e("GoogleSuggestedDistance", "InMilliSec " + new Gson().toJson(estimates.getDistance()));
                    return null;
                }));
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.layout_booking_details_bottonsheet);
        TextView pickUp = bottomSheetDialog.findViewById(R.id.tv_pick_up_location);
        TextView drop = bottomSheetDialog.findViewById(R.id.tv_drop_location);
        TextView totalPriceTv = bottomSheetDialog.findViewById(R.id.tv_total_price);
        TextView totalDistance = bottomSheetDialog.findViewById(R.id.tv_distance);
        ProgressBar loader = bottomSheetDialog.findViewById(R.id.book_loader_pb);
        loader.setIndeterminate(true);
        Button bookNow = bottomSheetDialog.findViewById(R.id.btn_cnf_booking);
        scheduleTime = bottomSheetDialog.findViewById(R.id.btn_scheduleRide);
        calander = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("hh:mm");
        time = simpleDateFormat.format(calander.getTime());
        scheduleTime.setText(time);
        scheduleTime.setOnClickListener(v -> {
            final Calendar c = Calendar.getInstance();
            mHour = c.get(Calendar.HOUR_OF_DAY);
            mMinute = c.get(Calendar.MINUTE);
            TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                    (view, hourOfDay, minute) ->
                            scheduleTime.setText(hourOfDay + ":" + minute), mHour, mMinute, true);
            BookingScheduleTime = mHour + ":" + mMinute;
            timePickerDialog.show();
        });
        Date s= Calendar.getInstance().getTime();
        System.out.println("Current time => " + s);
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String formattedDate = df.format(s);
        BookingStartTime = scheduleTime.getText().toString();
        pickUp.setText(pickupLocation.getText().toString());
        drop.setText(dropLocation.getText().toString());

        totalDistance.setText("" + distanceReal + " km");
        totalPriceTv.setText( "₹ " +price);
        BookRidePostModal bookRidePostModal = new BookRidePostModal();
        bookRidePostModal.setBookingMode(1);
        bookRidePostModal.setBookingEndTime("2022-08-07T19:43:21.714Z");
        bookRidePostModal.setBookingScheduleTime("2022-08-07T19:43:21.714Z");
        bookRidePostModal.setBookingStartTime("2022-08-07T19:43:21.714Z");
        bookRidePostModal.setDestination(dropLoc);
        bookRidePostModal.setMessage("msg");
        bookRidePostModal.setDistance((int)distanceReal);
        bookRidePostModal.setPickupLatitude((int) pLat);
        bookRidePostModal.setPickupLongitude((int) pLng);
        bookRidePostModal.setDropLatitude((int) dLat);
        bookRidePostModal.setDropLongitude((int) dLng);
        bookRidePostModal.setIsRain(isRain);
        bookRidePostModal.setSource(picUpLoc);
        bookRidePostModal.setFirebaseUserId(firebaseUserId);
        bookRidePostModal.setCustomerID(customerId);
        bookRidePostModal.setTotalFare(Double.valueOf(totalPrice));
        bookRidePostModal.setBookingMode(vehiclePriceId);

        assert bookNow != null;
        bookNow.setOnClickListener(v->{
            loader.setVisibility(View.VISIBLE);
            bookNow.setVisibility(View.GONE);
            ServiceApi api = ApiClient.getClient().create(ServiceApi.class);
            Call<BookRideResponseModal> call = api.bookRide(bookRidePostModal);
            call.enqueue(new Callback<BookRideResponseModal>() {
                @Override
                public void onResponse(@NonNull Call<BookRideResponseModal> call, @NonNull Response<BookRideResponseModal> response) {
                 if (response.isSuccessful()){
                     if (response.body()!=null){
                         if (response.body().getResultStatus().equals(1)){
                             Toasty.success(UserDashboardActivity.this,response.body().getResultMessage(), Toast.LENGTH_SHORT).show();
                             new Handler(Looper.myLooper()).postDelayed(() -> {
                                 finish();
                                 startActivity(getIntent());
                             },2000);
                         }else if (response.body().getResultStatus().equals(0)){
                             loader.setVisibility(View.GONE);
                             bookNow.setVisibility(View.VISIBLE);
                             Toasty.error(UserDashboardActivity.this,response.body().getResultMessage(), Toast.LENGTH_SHORT).show();
                         }else if (response.body().getResultStatus().equals(2)){
                             Toasty.error(UserDashboardActivity.this,response.body().getResultMessage(), Toast.LENGTH_SHORT).show();
                             loader.setVisibility(View.GONE);
                             bookNow.setVisibility(View.VISIBLE);
                         }
                     }
                 }
                }
                @Override
                public void onFailure(@NonNull Call<BookRideResponseModal> call,@NonNull Throwable t) {
                    call.cancel();
                    Toasty.info(UserDashboardActivity.this,t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    loader.setVisibility(View.GONE);
                    bookNow.setVisibility(View.VISIBLE);
                }
            });
        });
        bottomSheetDialog.show();
    }
}