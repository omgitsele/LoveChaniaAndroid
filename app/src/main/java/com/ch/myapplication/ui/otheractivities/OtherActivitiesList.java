package com.ch.myapplication.ui.otheractivities;

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
import com.ch.myapplication.ui.accommodation.AccommodationList;
import com.ch.myapplication.ui.ads.AdsCustomGrid;
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


public class OtherActivitiesList extends AppCompatActivity {

    private final String adUnitID = "ca-app-pub-6122629245886275/8952040258";
    private final String testUnitID = "ca-app-pub-3940256099942544/1033173712";
    GridView grid;
    private String currentLat = "0.0";
    private String currentLongt = "0.0";
    int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    List<Place> placeList = new ArrayList<>();
    boolean flag = false;
    String[] activityList = {
            "Ktel Public Bus",
            "Cretan Daily Cruises",
            "Botanical Park",
            "Strata Tours",
            "Kissamos Sea Sport",
            "Alaloum",
            "My Crete Tours" };



    int[] imageId = {
            R.drawable.ktelpublicbus0,
            R.drawable.cretadailycruise2,
            R.drawable.botanicalpark0,
            R.drawable.stratatours0,
            R.drawable.kissamosseasport0,
            R.drawable.alaloum0,
            R.drawable.mycretetours0
    };

    int [] poiNoPhotos = { 2, 3, 8, 9, 9, 3, 3, 9 };

    char[] hasPhone = { '1', '1', '1', '1', '1', '1', '1'};

    String[] phoneList = { "+302821093052", "+302822022888", "+302821200770", "+302822024249", "+306994673336", "+302821068718", "+302821007442" } ;

    private double[] Lat =  { 35.511983, 35.516664, 35.418429, 35.495543, 35.499815,  35.517304, 35.534023};

    private double[] Longt = { 24.016925, 23.635553, 23.939715, 23.654792, 23.646565,  23.936020, 24.076876};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MobileAds.initialize(this, initializationStatus -> {
        });


        setContentView(R.layout.activity_accommodation_list);
        setTitle("Other Activities");
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // method to get the location
        placeList.add(new Place("Ktel Public Bus", R.drawable.ktelpublicbus0, 2, 35.511983, 24.016925, '1', "+302821093052"));
        placeList.add(new Place("Cretan Daily Cruises", R.drawable.cretadailycruise2, 3, 35.516664, 23.635553, '1', "+302822022888"));
        placeList.add(new Place("Botanical Park", R.drawable.botanicalpark0, 8, 35.418429, 23.939715, '1', "+302821200770"));
        placeList.add(new Place("Strata Tours", R.drawable.stratatours0, 9, 35.495543, 23.654792, '1', "+302822024249"));
        placeList.add(new Place("Kissamos Sea Sport", R.drawable.kissamosseasport0, 9, 35.499815, 23.646565, '1', "+306994673336"));
        placeList.add(new Place("Alaloum", R.drawable.alaloum0, 3, 35.517304, 23.93602, '1', "+302821068718"));
        placeList.add(new Place("My Crete Tours", R.drawable.mycretetours0, 9, 35.534023, 24.076876, '1', "+302821007442"));
        getLastLocation();

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
//                        for (int i=0; i< activityList.length; i++)
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
//                            Place p = new Place(activityList[i], imageId[i], poiNoPhotos[i], Lat[i], Longt[i], distanceTo, hasPhone[i], phoneList[i]);
//                            placeList.add(p);
//                        }
                        AdsCustomGrid adapter = new AdsCustomGrid(OtherActivitiesList.this, placeList, currentLat, currentLongt);
                        grid = findViewById(R.id.grid);
                        grid.setAdapter(adapter);
                        Log.d("LATLONG", "Lat: " + currentLat + " Longt: " + currentLongt);
                    }
                });
            } else {
                AdsCustomGrid adapter = new AdsCustomGrid(OtherActivitiesList.this, activityList, imageId, poiNoPhotos, Lat, Longt, hasPhone, phoneList, adUnitID);
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



}
