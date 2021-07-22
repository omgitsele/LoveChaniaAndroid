package com.ch.myapplication.ui.accommodation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.ch.myapplication.Place;
import com.ch.myapplication.R;
import com.ch.myapplication.ui.ads.AdsCustomGrid;
import com.ch.myapplication.ui.poi.CustomGrid;
import com.ch.myapplication.ui.shops.ShopsActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;


public class AccommodationList extends AppCompatActivity {

    private final String adUnitID = "ca-app-pub-6122629245886275/5177829663";
    private final String testUnitID = "ca-app-pub-3940256099942544/1033173712";
    GridView grid;
    private String currentLat = "0.0";
    private String currentLongt = "0.0";
    int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    List<Place> placeList = new ArrayList<>();
    boolean flag = false;

    String[] accomodationList = {
            "Porto Veneziano",
            "Sunrise Village",
            "Sunny Bay",
            "Vranas",
            "Klinakis",
            "Evans House",
            "Nikolas Rooms",
            "Casa Veneta"
    };



    int[] imageId = {
            R.drawable.portoveneziano0,
            R.drawable.sunrisevillage0,
            R.drawable.sunnybay0,
            R.drawable.vranas0,
            R.drawable.klinakis0,
            R.drawable.evanshouse0,
            R.drawable.nikolasrooms0,
            R.drawable.casaveneta0,
    };

    int [] poiNoPhotos = { 5, 14, 5, 3, 2, 3, 4, 5};

    char[] hasPhone = {'1', '1', '1', '1', '1', '1', '1', '1'}	;

    String[] phoneList = {"+302821027100", "+302821083640", "+302822083062", "+302821058618", "+306947663585", "+306974814184", "+306978187357", "+302821090007"};

    private double[] Lat =   {35.518916, 35.515179, 35.497736, 35.515588,  35.512961, 35.515028, 35.515451, 35.517829};

    private double[] Longt = { 24.022742, 23.915792, 23.662395, 24.018468, 24.003265, 24.027404, 24.017892, 24.015040};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        MobileAds.initialize(this, initializationStatus -> {
//        });

        setContentView(R.layout.activity_accommodation_list);
        setTitle("Accommodation");
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // method to get the location
        placeList.add(new Place("Porto Veneziano", R.drawable.portoveneziano0, 5, 35.518916, 24.022742, '1', "+302821027100"));
        placeList.add(new Place("Sunrise Village", R.drawable.sunrisevillage0, 14, 35.515179, 23.915792, '1', "+302821083640"));
        placeList.add(new Place("Sunny Bay", R.drawable.sunnybay0, 5, 35.497736, 23.662395, '1', "+302822083062"));
        placeList.add(new Place("Vranas", R.drawable.vranas0, 3, 35.515588, 24.018468, '1', "+302821058618"));
        placeList.add(new Place("Klinakis", R.drawable.klinakis0, 2, 35.512961, 24.003265, '1', "+306947663585"));
        placeList.add(new Place("Evans House", R.drawable.evanshouse0, 3, 35.515028, 24.027404, '1', "+306974814184"));
        placeList.add(new Place("Nikolas Rooms", R.drawable.nikolasrooms0, 4, 35.515451, 24.017892, '1', "+306978187357"));
        placeList.add(new Place("Casa Veneta", R.drawable.casaveneta0, 5, 35.517829, 24.01504, '1', "+302821090007"));
        getLastLocation();


    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();
            currentLat = String.valueOf(mLastLocation.getLatitude());
            currentLongt = String.valueOf(mLastLocation.getLongitude());
        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;

        // If we want background location
        // on Android 10.0 and higher,
        // use:
        // ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
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

    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                mFusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {
                        currentLat = String.valueOf(location.getLatitude());
                        currentLongt = String.valueOf(location.getLongitude());
//                        for (int i=0; i< accomodationList.length; i++)
//                        {
//
//                            Location locationA = new Location("point A");
//
//                            locationA.setLatitude(Lat[i]);
//                            locationA.setLongitude(Longt[i]);
//
//                            Location locationB = new Location("point B");
//
//                            locationB.setLatitude(Double.parseDouble(currentLat));
//                            locationB.setLongitude(Double.parseDouble(currentLongt));
//
//                            float distanceTo = locationA.distanceTo(locationB) / 1000;
//                            Log.d("Distance", ""+distanceTo);
//                            Place p = new Place(accomodationList[i], imageId[i], poiNoPhotos[i], Lat[i], Longt[i], distanceTo, hasPhone[i], phoneList[i]);
//                            placeList.add(p);
//                        }
                        AdsCustomGrid adapter = new AdsCustomGrid(AccommodationList.this, placeList, currentLat, currentLongt);
                        grid = findViewById(R.id.grid);
                        grid.setAdapter(adapter);
                        Log.d("LATLONG", "Lat: " + currentLat + " Longt: " + currentLongt);
                    }
                });
            } else {
                AdsCustomGrid adapter = new AdsCustomGrid(AccommodationList.this, accomodationList, imageId, poiNoPhotos, Lat, Longt, hasPhone, phoneList, adUnitID);
                grid = findViewById(R.id.grid);
                grid.setAdapter(adapter);
                if (!flag) {
                    flag = true;
                    Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent);
                }
            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        finish();
        return true;
    }


}
