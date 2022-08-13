package com.cabossarthi.osaarthi.gmap;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cabossarthi.osaarthi.R;
import com.cabossarthi.osaarthi.mapsession.PickupPreferenceManager;
import com.cabossarthi.osaarthi.ui.user.UserDashboardActivity;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.model.TypeFilter;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.skydoves.elasticviews.ElasticButton;
import com.skydoves.elasticviews.ElasticFloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import in.madapps.placesautocomplete.PlaceAPI;
import in.madapps.placesautocomplete.adapter.PlacesAutoCompleteAdapter;


public class SelectPickUpLocationActivity extends AppCompatActivity implements OnMapReadyCallback,
        LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, GoogleMap.OnMapClickListener, GoogleMap.OnMarkerDragListener{
    String TAG;
    PlacesClient placesClient;
    private GoogleMap mMap;
    private TextView MovableLocation;
    Geocoder geocoder;
    private String PickUpAddress;
    private String SelectedPlace;
    GoogleApiClient mGoogleApiClient;
    FusedLocationProviderClient fusedLocationProviderClient;
    ElasticFloatingActionButton floatingActionButton;
    private View locationButton;
    RelativeLayout MapLayout;
    ElasticButton submit;
    String id = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_pick_up_location);
        id=getIntent().getStringExtra("id");
        TAG = this.getClass().getCanonicalName();
        submit = findViewById(R.id.btn_submit);
        floatingActionButton = findViewById(R.id.fab_getlocation);
        MovableLocation = findViewById(R.id.tv_movable_location);
        MapLayout = findViewById(R.id.rl_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        mapFragment.setRetainInstance(true);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            getLocation();
        } else {
            ActivityCompat.requestPermissions(SelectPickUpLocationActivity.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);
        }
        floatingActionButton.setOnClickListener(v -> {
            moveCurrent(mMap.getMyLocation());
        });
        if (!Places.isInitialized()) {
            Places.initialize(SelectPickUpLocationActivity.this, getString(R.string.google_maps_key), Locale.getDefault());
        }
        placesClient = Places.createClient(getApplicationContext());
        AutocompleteSupportFragment autocompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.autocomplete_fragment);
        autocompleteFragment.setCountries("IN");
        autocompleteFragment.setTypeFilter(TypeFilter.CITIES);
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                LatLng movablelatlng=place.getLatLng();
                moveCurrentLocation(movablelatlng);
                SelectedPlace = place.getName();
//                Log.e(TAG, "onPlaceSelected: showOnMap" + place.getLatLng().latitude + "\n" + place.getLatLng().longitude);
                try {
                    List<Address> addresses;
                    geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        addresses = geocoder.getFromLocation(place.getLatLng().latitude, place.getLatLng().longitude, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5
                        String address1 = addresses.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String address2 = addresses.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                        String city = addresses.get(0).getLocality();
                        String state = addresses.get(0).getAdminArea();
                        String country = addresses.get(0).getCountryName();
                        String postalCode = addresses.get(0).getPostalCode();
                        PickupPreferenceManager.getInstance(getApplicationContext()).savePicupLocation(place.getLatLng().latitude + "," + place.getLatLng().longitude,
                                place.getName(), state, city, addresses.get(0).getLocality(), postalCode, place.getAddress());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    //setMarker(latLng);
                }
            }

            @Override
            public void onError(@NonNull Status status) {
            }
        });

        submit.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UserDashboardActivity.class);
            intent.putExtra("pickup_id","1");
//            intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent);
            this.finish();
        });
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(task -> {
            Location location = task.getResult();
            if (location != null) {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                try {
                    List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                    PickUpAddress = addressList.get(0).getAddressLine(0);
                } catch (IOException ioException) {

                }
            }
        });
    }
    private void moveCurrent(@NonNull Location location) {
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(), location.getLongitude()), 15.0f));
    }

    private void moveCurrentLocation(LatLng latLng) {
        mMap.clear();
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(latLng.latitude,latLng.longitude), 15.0f));

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

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }

    @SuppressLint("ResourceType")
    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getApplicationContext(),
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
        geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull @NotNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        PickUpAddress = addressList.get(0).getAddressLine(0);
                        try {
                            if (SelectedPlace != null) {
                                PickUpAddress = SelectedPlace;
                            }
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
            }
        });
        mMap.setOnCameraChangeListener(cameraPosition -> {
            try {
                Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                List<Address> addressList = geocoder.getFromLocation(cameraPosition.target.latitude, cameraPosition.target.longitude, 1);
                String address1 = addressList.get(0).getAddressLine(0); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String address2 = addressList.get(0).getAddressLine(1); // If any additional address line present than only, check with max available address lines by getMaxAddressLineIndex()
                String city = addressList.get(0).getLocality();
                String state = addressList.get(0).getAdminArea();
                String country = addressList.get(0).getCountryName();
                String postalCode = addressList.get(0).getPostalCode();
                if (addressList.size() > 0) {
                    Address address = addressList.get(0);
                    LatLng india = new LatLng(address.getLatitude(), address.getLongitude());
                    String mainAddress = addressList.get(0).getAddressLine(0);
                    LatLng latLng = new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude);
                    if (mainAddress != null) {
                        MovableLocation.setText(mainAddress);
                        PickupPreferenceManager.getInstance(getApplicationContext()).savePicupLocation(
                                cameraPosition.target.latitude + "," + cameraPosition.target.longitude,
                                address.getAddressLine(0), state, city, addressList.get(0).getSubAdminArea(), postalCode, address1);
                    }
                }
            } catch (IndexOutOfBoundsException | IOException ioException) {
                ioException.printStackTrace();
            }
        });
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
        mGoogleApiClient.connect();
    }
}