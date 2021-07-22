package com.ch.myapplication.ui.shops;

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
import com.ch.myapplication.ui.beaches.BeachesList;
import com.ch.myapplication.ui.poi.CustomGrid;
import com.ch.myapplication.ui.rnb.RestaurantBars;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;


public class ShopsActivity extends AppCompatActivity {

    private static final String TAG = "TAG";
    private final String adUnitID = "ca-app-pub-6122629245886275/6218065703";
    private final String testUnitID = "ca-app-pub-3940256099942544/1033173712";
    private InterstitialAd mInterstitialAd;
    GridView grid;
    private String currentLat = "0.0";
    private String currentLongt = "0.0";
    int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    List<Place> placeList = new ArrayList<>();
    boolean flag = false;

    String[] shopList = { "K.And", "Miden Agan", "Panos Koromilas", "L & O", "Andreadakis", "Vape Port", "PetWorld", "Yoyo", "Cannabis Spot", "Bio Green"};

    int[] imageId = {
            R.drawable.kand0,
            R.drawable.midenagan0,
            R.drawable.panoskoromilas0,
            R.drawable.lo0,
            R.drawable.andreadakis0,
            R.drawable.vapeport0,
            R.drawable.petworld0,
            R.drawable.yoyo0,
            R.drawable.cannabisspot0,
            R.drawable.biogreen0
    };

    int [] poiNoPhotos = { 2, 3, 4, 3, 2, 4, 1, 1, 8, 7};

    char[] hasPhone = { '1', '1', '1', '1', '1', '1', '1', '1', '1', '1'};

    String[] phoneList = { "+302821043901", "+302821027068", "+302821050916", "+302821075227", "+302821064459", "+302821007005", "+302821091862", "+306973081463", "+302822024654", "+302822024665"};

    private final double[] Lat = { 35.515438, 35.517394, 35.516098, 35.518268, 35.530546, 35.526220, 35.512359, 35.517938, 35.4952912, 35.4950634};

    private final double[] Longt = { 24.018787, 24.020844, 24.017958, 24.014963, 24.076144, 24.071131, 24.015863, 24.015012, 23.6489765,23.6524899};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_accommodation_list);
        setTitle("Shops");
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // method to get the location
        placeList.add(new Place("K.And", R.drawable.kand0, 2, 35.515438, 24.018787, '1', "+302821043901"));
        placeList.add(new Place("Miden Agan", R.drawable.midenagan0, 3, 35.517394, 24.020844, '1', "+302821027068"));
        placeList.add(new Place("Panos Koromilas", R.drawable.panoskoromilas0, 4, 35.516098, 24.017958, '1', "+302821050916"));
        placeList.add(new Place("L & O", R.drawable.lo0, 3, 35.518268, 24.014963, '1', "+302821075227"));
        placeList.add(new Place("Andreadakis", R.drawable.andreadakis0, 2, 35.530546, 24.076144, '1', "+302821064459"));
        placeList.add(new Place("Vape Port", R.drawable.vapeport0, 4, 35.52622, 24.071131, '1', "+302821007005"));
        placeList.add(new Place("PetWorld", R.drawable.petworld0, 1, 35.512359, 24.015863, '1', "+302821091862"));
        placeList.add(new Place("Yoyo", R.drawable.yoyo0, 1, 35.517938, 24.015012, '1', "+306973081463"));
        placeList.add(new Place("Cannabis Spot", R.drawable.cannabisspot0, 8, 35.4952912, 23.6489765, '1', "+302822024654"));
        placeList.add(new Place("Bio Green", R.drawable.biogreen0, 7, 35.4950634, 23.6524899, '1', "+302822024665"));
        getLastLocation();



//        AdsCustomGrid adapter = new AdsCustomGrid(ShopsActivity.this, shopList, imageId, poiNoPhotos, Lat, Longt, hasPhone, phoneList, adUnitID);
//        grid=findViewById(R.id.grid);
//        grid.setAdapter(adapter);

        AdRequest adRequest = new AdRequest.Builder().build();
        MobileAds.initialize(this, initializationStatus -> {
        });

        InterstitialAd.load(this, adUnitID, adRequest,
                new InterstitialAdLoadCallback() {
                    @Override
                    public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                        // The mInterstitialAd reference will be null until
                        // an ad is loaded.
                        mInterstitialAd = interstitialAd;
                        mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                            @Override
                            public void onAdDismissedFullScreenContent() {
                                // Called when fullscreen content is dismissed.
                                Log.d("TAG", "The ad was dismissed.");
                            }

                            @Override
                            public void onAdFailedToShowFullScreenContent(AdError adError) {
                                // Called when fullscreen content failed to show.
                                Log.d("TAG", "The ad failed to show.");
                            }

                            @Override
                            public void onAdShowedFullScreenContent() {
                                // Called when fullscreen content is shown.
                                // Make sure to set your reference to null so you don't
                                // show it a second time.
                                mInterstitialAd = null;
                                Log.d("TAG", "The ad was shown.");
                            }
                        });
                        if (mInterstitialAd != null) {
                            mInterstitialAd.show(ShopsActivity.this);
                        } else {
                            Log.d("TAG", "The interstitial ad wasn't ready yet.");
                        }
                        Log.i(TAG, "onAdLoaded");
                    }


                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error
                        Log.i(TAG, loadAdError.getMessage());
                        mInterstitialAd = null;
                    }
                });




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
//                        for (int i=0; i< shopList.length; i++)
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
//                            Place p = new Place(shopList[i], imageId[i], poiNoPhotos[i], Lat[i], Longt[i], distanceTo, hasPhone[i], phoneList[i]);
//                            placeList.add(p);
//                        }
                        AdsCustomGrid adapter = new AdsCustomGrid(ShopsActivity.this, placeList, currentLat, currentLongt);
                        grid = findViewById(R.id.grid);
                        grid.setAdapter(adapter);
                        Log.d("LATLONG", "Lat: " + currentLat + " Longt: " + currentLongt);
                    }
                });
            } else {
                AdsCustomGrid adapter = new AdsCustomGrid(ShopsActivity.this, shopList, imageId, poiNoPhotos, Lat, Longt, hasPhone, phoneList, adUnitID);
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
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }


}