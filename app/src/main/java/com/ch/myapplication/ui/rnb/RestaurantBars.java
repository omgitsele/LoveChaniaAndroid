package com.ch.myapplication.ui.rnb;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.ch.myapplication.Place;
import com.ch.myapplication.R;

import com.ch.myapplication.ui.ads.AdsCustomGrid;
import com.ch.myapplication.ui.shops.ShopsActivity;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;

import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import java.util.ArrayList;
import java.util.List;

public class RestaurantBars extends AppCompatActivity {

    private static final String TAG = "TAG";
    private final String adUnitID = "ca-app-pub-6122629245886275/3647742682";
    private final String testUnitID = "ca-app-pub-3940256099942544/1033173712";
    private InterstitialAd mInterstitialAd;

    private String currentLat = "0.0";
    private String currentLongt = "0.0";
    int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    List<Place> placeList = new ArrayList<>();
    boolean flag = false;

    GridView grid;
    String[] rnbList = {
            "Salis",
            "Let's Spoon",
            "Glossitses",
            "To Antikristo",
            "Semiramis",
            "Bourakis",
            "Asotos Yios",
            "Veneto",
            "Pizza Gustosa",
            "Kantari",
            "Manteio",
            "Valentino Pasta & Grill",
            "Perigiali",
            "Porfira",
            "Monica's Garden",
            "Loukoulos",
            "Roubini Beach",
            "Naya"};


    int[] imageId = {
            R.drawable.salis0,
            R.drawable.letsspoon0,
            R.drawable.glossitses0,
            R.drawable.toantikristo0,
            R.drawable.semiramis0,
            R.drawable.bourakis0,
            R.drawable.asotosyios0,
            R.drawable.veneto0,
            R.drawable.pizzagustosa0,
            R.drawable.kantari0,
            R.drawable.manteio0,
            R.drawable.valentinopastagrill0,
            R.drawable.perigiali0,
            R.drawable.porfira0,
            R.drawable.monicasgarden0,
            R.drawable.loukoulos0,
            R.drawable.roubinibeach0,
            R.drawable.naya0
    };

    int[] poiNoPhotos = {
            6,
            4,
            1,
            2,
            4,
            6,
            3,
            1,
            3,
            6,
            3,
            3,
            5,
            6,
            6,
            4,
            6,
            18};

    char[] hasPhone = {
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1',
            '1'};

    String[] phoneList = {
            "+302821043700",
            "+306982933746",
            "+302821059074",
            "+30 2821072700",
            "+302821098650",
            "+302821064254",
            "+302821050203",
            "+302821093527",
            "+302821066511",
            "+302821068090",
            "+302821068090",
            "+302821068805",
            "+302822305098",
            "+306993069527",
            "+302823041150",
            "+302821066320",
            "+302822083341",
            "+306981234910"};

    private final double[] Lat = {
            35.518353,
            35.516077,
            35.518654,
            35.488049,
            35.516049,
            35.533467,
            35.516981,
            35.516382,
            35.530641,
            35.517377,
            35.517364,
            35.517365,
            35.4892699,
            35.5052467,
            35.230519,
            35.505056,
            35.499548,
            35.497884};

    private final double[] Longt = {
            24.022014,
            24.018068,
            24.022604,
            23.992468,
            24.016510,
            24.076898,
            24.024851,
            24.017231,
            24.075917,
            23.906167,
            23.906427,
            23.917127,
            23.5810072,
            23.7130334,
            23.681707,
            24.173887,
            23.646301,
            23.670712};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_accommodation_list);
        setTitle("Restaurants & Bars");
        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // method to get the location
        placeList.add(new Place("Salis", R.drawable.salis0, 6, 35.518353, 24.022014, '1', "+302821043700"));
        placeList.add(new Place("Let's Spoon", R.drawable.letsspoon0, 4, 35.516077, 24.018068, '1', "+306982933746"));
        placeList.add(new Place("Glossitses", R.drawable.glossitses0, 1, 35.518654, 24.022604, '1', "+302821059074"));
        placeList.add(new Place("To Antikristo", R.drawable.toantikristo0, 2, 35.488049, 23.992468, '1', "+30 2821072700"));
        placeList.add(new Place("Semiramis", R.drawable.semiramis0, 4, 35.516049, 24.01651, '1', "+302821098650"));
        placeList.add(new Place("Bourakis", R.drawable.bourakis0, 6, 35.533467, 24.076898, '1', "+302821064254"));
        placeList.add(new Place("Asotos Yios", R.drawable.asotosyios0, 3, 35.516981, 24.024851, '1', "+302821050203"));
        placeList.add(new Place("Veneto", R.drawable.veneto0, 1, 35.516382, 24.017231, '1', "+302821093527"));
        placeList.add(new Place("Pizza Gustosa", R.drawable.pizzagustosa0, 3, 35.530641, 24.075917, '1', "+302821066511"));
        placeList.add(new Place("Kantari", R.drawable.kantari0, 6, 35.517377, 23.906167, '1', "+302821068090"));
        placeList.add(new Place("Manteio", R.drawable.manteio0, 3, 35.517364, 23.906427, '1', "+302821068090"));
        placeList.add(new Place("Valentino Pasta & Grill", R.drawable.valentinopastagrill0, 3, 35.517365, 23.917127, '1', "+302821068805"));
        placeList.add(new Place("Perigiali", R.drawable.perigiali0, 5, 35.4892699, 23.5810072, '1', "+302822305098"));
        placeList.add(new Place("Porfira", R.drawable.porfira0, 6, 35.5052467, 23.7130334, '1', "+306993069527"));
        placeList.add(new Place("Monica's Garden", R.drawable.monicasgarden0, 6, 35.230519, 23.681707, '1', "+302823041150"));
        placeList.add(new Place("Loukoulos", R.drawable.loukoulos0, 4, 35.505056, 24.173887, '1', "+302821066320"));
        placeList.add(new Place("Roubini Beach", R.drawable.roubinibeach0, 6, 35.499548, 23.646301, '1', "+302822083341"));
        placeList.add(new Place("Naya", R.drawable.naya0, 18, 35.497884, 23.670712, '1', "+306981234910"));
        getLastLocation();





//        AdsCustomGrid adapter = new AdsCustomGrid(RestaurantBars.this, rnbList, imageId, poiNoPhotos, Lat, Longt, hasPhone, phoneList, adUnitID);
//        grid = findViewById(R.id.grid);
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
                            mInterstitialAd.show(RestaurantBars.this);
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
//                        for (int i=0; i< rnbList.length; i++)
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
//                            Place p = new Place(rnbList[i], imageId[i], poiNoPhotos[i], Lat[i], Longt[i], distanceTo, hasPhone[i], phoneList[i]);
//                            placeList.add(p);
//                        }
                        AdsCustomGrid adapter = new AdsCustomGrid(RestaurantBars.this, placeList, currentLat, currentLongt);
                        grid = findViewById(R.id.grid);
                        grid.setAdapter(adapter);
                        Log.d("LATLONG", "Lat: " + currentLat + " Longt: " + currentLongt);
                    }
                });
            } else {
                AdsCustomGrid adapter = new AdsCustomGrid(RestaurantBars.this, rnbList, imageId, poiNoPhotos, Lat, Longt, hasPhone, phoneList, adUnitID);
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
        return true;
    }

}