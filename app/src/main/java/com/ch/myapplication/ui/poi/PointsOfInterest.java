package com.ch.myapplication.ui.poi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.google.android.gms.ads.AdError;
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

public class PointsOfInterest extends AppCompatActivity {


    private final String adUnitID = "ca-app-pub-6122629245886275/7115040940";
    private final String testUnitID = "ca-app-pub-3940256099942544/1033173712";
    private static final String TAG = "TAG";
    private InterstitialAd mInterstitialAd;
    GridView grid;
    List<Place> placeList = new ArrayList<>();
    private String currentLat = "0.0";
    private String currentLongt = "0.0";
    int PERMISSION_ID = 44;
    boolean flag = false;

    private FusedLocationProviderClient mFusedLocationClient;

    String[] poiList = {
            "Chania Old Town",
            "The Lighthouse",
            "Fortress Firkas",
            "The Mosque of the Janissaries",
            "Etz Haim Synagogue",
            "The Cathedral",
            "Museums of the city of Chania",
            "Agora",
            "Arsenals",
            "The church of Agios Nikolaos",
            "Turkish baths",
            "Theotokopoulou street ",
            "Halidon street",
            "Daliani street",
            "Koum Kapi",
            "House and tomb of E Venizelos",
            "The Akrotiri monasteries",
            "The antique city of Aptera",
            "Georgioupoli",
            "Kournas Lake",
            "Argiroupoli",
            "Sfakia and Frangokastello",
            "Loutro",
            "Samaria Gorge",
            "Lissos",
            "Moni Chrisokalitisa",
            "Kissamos",
            "Archaeological Museum of Kissamos",
            "Polyrinia and Venetian castle",
            "Ancient Falasarna",
            "Gramvousa Island",
            "Moni Gonias",
            "Platanias War Museum",
            "Therisso"

    } ;
    int[] imageId = {
            R.drawable.chaniaoldtown0,
            R.drawable.thelighthouse0,
            R.drawable.fortressfirkas0,
            R.drawable.themosqueofthejanissaries0,
            R.drawable.etzhaimsynagogue0,
            R.drawable.thecathedral0,
            R.drawable.museumsofthecityofchania0,
            R.drawable.agora0,
            R.drawable.arsenals0,
            R.drawable.thechurchofagiosnikolaos0,
            R.drawable.turkishbaths0,
            R.drawable.theotokopouloustreet0,
            R.drawable.halidonstreet0,
            R.drawable.dalianistreet0,
            R.drawable.koumkapi0,
            R.drawable.houseandtombofevenizelos0,
            R.drawable.theakrotirimonasteries0,
            R.drawable.theantiquecityofaptera0,
            R.drawable.georgioupoli0,
            R.drawable.kournaslake0,
            R.drawable.argiroupoli0,
            R.drawable.sfakiaandfrangokastello0,
            R.drawable.loutro0,
            R.drawable.samariagorge0,
            R.drawable.lissos0,
            R.drawable.monichrisokalitisa0,
            R.drawable.kissamos0,
            R.drawable.archaeologicalmuseumofkissamos0,
            R.drawable.polyriniaandvenetiancastle0,
            R.drawable.ancientfalasarna0,
            R.drawable.gramvousaisland0,
            R.drawable.monigonias0,
            R.drawable.plataniaswarmuseum0,
            R.drawable.therisso0
    };

    int [] poiNoPhotos = {8, 4, 4, 4, 2, 3, 8, 3, 2, 5, 2, 6, 7, 4, 3, 3, 5, 6, 2, 2, 6, 5, 4, 3, 6, 4, 2, 3, 4, 6, 4, 3, 3, 3};
    private final double[] Lat = {35.515257, 35.519647, 35.518965, 35.517362, 35.515780, 35.515253, 35.515868, 35.514133, 35.518348, 35.516393, 35.515494, 35.517903, 35.515341, 35.515268, 35.517470, 35.524865, 35.560617, 35.465330, 35.362590, 35.332524, 35.286659, 35.200763,
            35.198093, 35.307974, 35.241642, 35.231736, 34.848888, 35.311184, 35.494404, 35.495633, 35.454999, 35.511429, 35.610619, 35.550512, 35.514147, 35.405895};
    private final double[] Longt={24.017696, 24.016721, 24.015514, 24.017813, 24.016694, 24.018267, 24.017729, 24.020464, 24.020886, 24.022150, 24.017814, 24.015100, 24.017725, 24.020191, 24.024450, 24.056258, 24.135517, 24.147510, 24.262539, 24.279681, 24.335761, 24.136609,
            24.079873, 23.918382, 23.785975, 23.682961, 24.088304, 23.533732, 23.653577, 23.654412, 23.652764, 23.570326, 23.578834, 23.776891, 23.911988, 23.984251};
//35.515868, 24.017729 Bizantyne Museum  35.518423, 24.014942 Maritime Museum  35.518005, 24.015925
//    TURKISH BATHS 1 35.515494, 24.017814 2 35.516548, 24.016292 12
//17. MONASTERIES OF AKROTIRI Agia Triada   35.560617, 24.135517 Gouverneto   35.584467, 24.140918 Katoliko   35.590272, 24.146162
//22. SFAKIA & FRANGOKASTELLO Sfakia    35.200763, 24.136609 Frangokastello   35.182056, 24.234549

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.poi_grid);
        setTitle("Points Of Interest");
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        // Permission check
        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // method to get the location
        placeList.add(new Place("Chania Old Town", R.drawable.chaniaoldtown0, 8, 35.515257, 24.017696));
        placeList.add(new Place("The Lighthouse", R.drawable.thelighthouse0, 4, 35.519647, 24.016721));
        placeList.add(new Place("Fortress Firkas", R.drawable.fortressfirkas0, 4, 35.518965, 24.015514));
        placeList.add(new Place("The Mosque of the Janissaries", R.drawable.themosqueofthejanissaries0, 4, 35.517362, 24.017813));
        placeList.add(new Place("Etz Haim Synagogue", R.drawable.etzhaimsynagogue0, 2, 35.51578, 24.016694));
        placeList.add(new Place("The Cathedral", R.drawable.thecathedral0, 3, 35.515253, 24.018267));
        placeList.add(new Place("Museums of the city of Chania", R.drawable.museumsofthecityofchania0, 8, 35.515868, 24.017729));
        placeList.add(new Place("Agora", R.drawable.agora0, 3, 35.514133, 24.020464));
        placeList.add(new Place("Arsenals", R.drawable.arsenals0, 2, 35.518348, 24.020886));
        placeList.add(new Place("The church of Agios Nikolaos", R.drawable.thechurchofagiosnikolaos0, 5, 35.516393, 24.02215));
        placeList.add(new Place("Turkish baths", R.drawable.turkishbaths0, 2, 35.515494, 24.017814));
        placeList.add(new Place("Theotokopoulou street ", R.drawable.theotokopouloustreet0, 6, 35.517903, 24.0151));
        placeList.add(new Place("Halidon street", R.drawable.halidonstreet0, 7, 35.515341, 24.017725));
        placeList.add(new Place("Daliani street", R.drawable.dalianistreet0, 4, 35.515268, 24.020191));
        placeList.add(new Place("Koum Kapi", R.drawable.koumkapi0, 3, 35.51747, 24.02445));
        placeList.add(new Place("House and tomb of E Venizelos", R.drawable.houseandtombofevenizelos0, 3, 35.524865, 24.056258));
        placeList.add(new Place("The Akrotiri monasteries", R.drawable.theakrotirimonasteries0, 5, 35.560617, 24.135517));
        placeList.add(new Place("The antique city of Aptera", R.drawable.theantiquecityofaptera0, 6, 35.46533, 24.14751));
        placeList.add(new Place("Georgioupoli", R.drawable.georgioupoli0, 2, 35.36259, 24.262539));
        placeList.add(new Place("Kournas Lake", R.drawable.kournaslake0, 2, 35.332524, 24.279681));
        placeList.add(new Place("Argiroupoli", R.drawable.argiroupoli0, 6, 35.286659, 24.335761));
        placeList.add(new Place("Sfakia and Frangokastello", R.drawable.sfakiaandfrangokastello0, 5, 35.200763, 24.136609));
        placeList.add(new Place("Loutro", R.drawable.loutro0, 4, 35.198093, 24.079873));
        placeList.add(new Place("Samaria Gorge", R.drawable.samariagorge0, 3, 35.307974, 23.918382));
        placeList.add(new Place("Lissos", R.drawable.lissos0, 6, 35.241642, 23.785975));
        placeList.add(new Place("Moni Chrisoskalitisa", R.drawable.monichrisokalitisa0, 4, 35.231736, 23.682961));
        placeList.add(new Place("Kissamos", R.drawable.kissamos0, 2, 34.848888, 24.088304));
        placeList.add(new Place("Archaeological Museum of Kissamos", R.drawable.archaeologicalmuseumofkissamos0, 3, 35.311184, 23.533732));
        placeList.add(new Place("Polyrinia and Venetian castle", R.drawable.polyriniaandvenetiancastle0, 3, 35.494404, 23.653577));
        placeList.add(new Place("Ancient Falasarna", R.drawable.ancientfalasarna0, 6, 35.495633, 23.654412));
        placeList.add(new Place("Gramvousa Island", R.drawable.gramvousaisland0, 3, 35.454999, 23.652764));
        placeList.add(new Place("Moni Gonias", R.drawable.monigonias0, 3, 35.511429, 23.570326));
        placeList.add(new Place("Platanias War Museum", R.drawable.plataniaswarmuseum0, 3, 35.610619, 23.578834));
        placeList.add(new Place("Therisso", R.drawable.therisso0, 3, 35.550512, 23.776891));

        getLastLocation();



//        CustomGrid adapter = new CustomGrid(PointsOfInterest.this, poiList, imageId, poiNoPhotos, Lat, Longt, adUnitID);
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
                            mInterstitialAd.show(PointsOfInterest.this);
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
//                        currentLongt = String.valueOf(location.getLongitude());
//                        for (int i=0; i< poiList.length; i++)
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
//                            Place p = new Place(poiList[i], imageId[i], poiNoPhotos[i], Lat[i], Longt[i], distanceTo);
//                            placeList.add(p);
//                        }
                        //CustomGrid adapter = new CustomGrid(PointsOfInterest.this, poiList, imageId, poiNoPhotos, Lat, Longt, currentLat, currentLongt);
                        CustomGrid adapter = new CustomGrid(PointsOfInterest.this, placeList, currentLat, currentLongt);
                        grid = findViewById(R.id.grid);
                        grid.setAdapter(adapter);
                        Log.d("LATLONG", "Lat: " + currentLat + " Longt: " + currentLongt);
                    }
                });
            } else {
                CustomGrid adapter = new CustomGrid(PointsOfInterest.this, poiList, imageId, poiNoPhotos, Lat, Longt, adUnitID);
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


}
