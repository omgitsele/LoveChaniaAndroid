package com.ch.myapplication.ui.beaches;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import com.ch.myapplication.ui.poi.CustomGrid;
import com.ch.myapplication.ui.poi.PointsOfInterest;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BeachesList extends AppCompatActivity {

    private final String adUnitID = "ca-app-pub-6122629245886275/5578991203";
    private final String testUnitID = "ca-app-pub-3940256099942544/1033173712";
    private static final String TAG = "TAG";
    private String currentLat = "0.0";
    private String currentLongt = "0.0";
    int PERMISSION_ID = 44;
    private FusedLocationProviderClient mFusedLocationClient;
    List<Place> placeList = new ArrayList<>();
    boolean flag = false;
    private InterstitialAd mInterstitialAd;
    GridView grid;


    String[] beachesList = {
            "Balos",
            "Falassarna",
            "Elafonissi",
            "Kedrodasos",
            "Sfinari",
            "Platanakia",
            "Krios",
            "Grammeno",
            "Pachia Ammos",
            "Chalikia",
            "Gialiskari and Anidroi",
            "Sougia",
            "Agia Roumeli",
            "Agios Pavlos",
            "Marmara",
            "Glyka Nera",
            "Vrissi",
            "Filaki",
            "Frangokastello",
            "Orthi Ammos",
            "Almyrida",
            "Kalyves",
            "Marathi",
            "Loutraki",
            "Seitan Limania",
            "Stavros",
            "Kalathas",
            "Nea Chora",
            "Agioi Apostoloi",
            "Platanias and Agia Marina",
            "Menies",
            "Afrata"
    };
    int[] imageId = {
            R.drawable.balos0,
            R.drawable.falassarna0,
            R.drawable.elafonissi0,
            R.drawable.kedrodasos0,
            R.drawable.sfinari0,
            R.drawable.platanakia0,
            R.drawable.krios0,
            R.drawable.grammeno0,
            R.drawable.pachiaammos0,
            R.drawable.chalikia0,
            R.drawable.gialiskariandanidroi0,
            R.drawable.sougia0,
            R.drawable.agiaroumeli0,
            R.drawable.agiospavlos0,
            R.drawable.marmara0,
            R.drawable.glykanera0,
            R.drawable.vrissi0,
            R.drawable.filaki0,
            R.drawable.frangokastello0,
            R.drawable.orthiammos0,
            R.drawable.almyrida0,
            R.drawable.kalyves0,
            R.drawable.marathi0,
            R.drawable.loutraki0,
            R.drawable.seitanlimania0,
            R.drawable.stavros0,
            R.drawable.kalathas0,
            R.drawable.neachora0,
            R.drawable.agioiapostoloi0,
            R.drawable.plataniasandagiamarina0,
            R.drawable.menies0,
            R.drawable.afrata0
    };

    int[] poiNoPhotos = {6, 4, 6, 4, 5, 1, 3, 3, 1, 2, 6, 4, 5, 3, 1, 2, 1, 3, 1, 1, 4, 3, 3, 3, 2, 3, 3, 3, 5, 4, 3, 1};

    private final double[] Lat = {35.5793729, 35.4906558, 35.2712125, 35.2685624, 35.4196034, 35.4134827, 35.2364959, 35.233102, 35.2313408, 35.233207, 35.2365063, 35.248125, 35.2296424, 35.2205719, 35.1966072, 35.201803, 35.2019445, 35.1941219, 35.180433, 35.1831966, 35.4493364, 35.4521019, 35.5046404, 35.4992475, 35.5519289, 35.5916602, 35.5540692, 35.5136734, 35.5143063, 35.5211079, 35.6645951, 35.5737733};
    private final double[] Longt = {23.5886969, 23.5796099, 23.5412119, 23.5631449, 23.5640538, 23.5546567, 23.5937271, 23.635933, 23.6787133, 23.685882, 23.7195552, 23.811425, 23.9548497, 24.0023979, 24.0581353, 24.107579, 24.1344067, 24.1572202, 24.233687, 24.2434482, 24.2007811, 24.1754127, 24.1732897, 24.1637816, 24.1932899, 24.0951951, 24.0866393, 24.0058016, 23.9832036, 23.9280201, 23.7677132, 23.7769722};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.beaches_grid);
        setTitle("Beaches");

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
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

        placeList.add(new Place("Balos", R.drawable.balos0, 6, 35.5793729, 23.5886969));
        placeList.add(new Place("Falassarna", R.drawable.falassarna0, 4, 35.4906558, 23.5796099));
        placeList.add(new Place("Elafonissi", R.drawable.elafonissi0, 6, 35.2712125, 23.5412119));
        placeList.add(new Place("Kedrodasos", R.drawable.kedrodasos0, 4, 35.2685624, 23.5631449));
        placeList.add(new Place("Sfinari", R.drawable.sfinari0, 5, 35.4196034, 23.5640538));
        placeList.add(new Place("Platanakia", R.drawable.platanakia0, 1, 35.4134827, 23.5546567));
        placeList.add(new Place("Krios", R.drawable.krios0, 3, 35.2364959, 23.5937271));
        placeList.add(new Place("Grammeno", R.drawable.grammeno0, 3, 35.233102, 23.635933));
        placeList.add(new Place("Pachia Ammos", R.drawable.pachiaammos0, 1, 35.2313408, 23.6787133));
        placeList.add(new Place("Chalikia", R.drawable.chalikia0, 2, 35.233207, 23.685882));
        placeList.add(new Place("Gialiskari and Anidroi", R.drawable.gialiskariandanidroi0, 6, 35.2365063, 23.7195552));
        placeList.add(new Place("Sougia", R.drawable.sougia0, 4, 35.248125, 23.811425));
        placeList.add(new Place("Agia Roumeli", R.drawable.agiaroumeli0, 5, 35.2296424, 23.9548497));
        placeList.add(new Place("Agios Pavlos", R.drawable.agiospavlos0, 3, 35.2205719, 24.0023979));
        placeList.add(new Place("Marmara", R.drawable.marmara0, 1, 35.1966072, 24.0581353));
        placeList.add(new Place("Glyka Nera", R.drawable.glykanera0, 2, 35.201803, 24.107579));
        placeList.add(new Place("Vrissi", R.drawable.vrissi0, 1, 35.2019445, 24.1344067));
        placeList.add(new Place("Filaki", R.drawable.filaki0, 3, 35.1941219, 24.1572202));
        placeList.add(new Place("Frangokastello", R.drawable.frangokastello0, 1, 35.180433, 24.233687));
        placeList.add(new Place("Orthi Ammos", R.drawable.orthiammos0, 1, 35.1831966, 24.2434482));
        placeList.add(new Place("Almyrida", R.drawable.almyrida0, 4, 35.4493364, 24.2007811));
        placeList.add(new Place("Kalyves", R.drawable.kalyves0, 3, 35.4521019, 24.1754127));
        placeList.add(new Place("Marathi", R.drawable.marathi0, 3, 35.5046404, 24.1732897));
        placeList.add(new Place("Loutraki", R.drawable.loutraki0, 3, 35.4992475, 24.1637816));
        placeList.add(new Place("Seitan Limania", R.drawable.seitanlimania0, 2, 35.5519289, 24.1932899));
        placeList.add(new Place("Stavros", R.drawable.stavros0, 3, 35.5916602, 24.0951951));
        placeList.add(new Place("Kalathas", R.drawable.kalathas0, 3, 35.5540692, 24.0866393));
        placeList.add(new Place("Nea Chora", R.drawable.neachora0, 3, 35.5136734, 24.0058016));
        placeList.add(new Place("Agioi Apostoloi", R.drawable.agioiapostoloi0, 5, 35.5143063, 23.9832036));
        placeList.add(new Place("Platanias and Agia Marina", R.drawable.plataniasandagiamarina0, 4, 35.5211079, 23.9280201));
        placeList.add(new Place("Menies", R.drawable.menies0, 3, 35.6645951, 23.7677132));
        placeList.add(new Place("Afrata", R.drawable.afrata0, 1, 35.5737733, 23.7769722));


        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        // method to get the location
        getLastLocation();



//        Collections.sort(placeList, new Value());
//        //adapter.notifyDataSetChanged();




       // CustomGrid adapter = new CustomGrid(BeachesList.this, beachesList, imageId, poiNoPhotos, Lat, Longt, currentLat, currentLongt);
//        CustomGrid adapter = new CustomGrid(BeachesList.this, placeList, currentLat, currentLongt);
//        grid = findViewById(R.id.grid);
//        grid.setAdapter(adapter);

//        Collections.sort(placeList, (p1, p2) -> (int) (p1.getDistance() - p2.getDistance()));
//        adapter.sort();
        //adapter.notifyDataSetChanged();





//
//        placeList.sort((o1, o2) -> Float.compare(o1.getDistance(), o2.getDistance()));
//        adapter.notifyDataSetChanged();

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
                            mInterstitialAd.show(BeachesList.this);
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
                        currentLongt = String.valueOf(location.getLongitude());
//                        for (int i=0; i< beachesList.length; i++)
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
//                            Place p = new Place(beachesList[i], imageId[i], poiNoPhotos[i], Lat[i], Longt[i], distanceTo);
//                            placeList.add(p);
//                        }
                        CustomGrid adapter = new CustomGrid(BeachesList.this, placeList, currentLat, currentLongt);                        grid = findViewById(R.id.grid);
                        grid.setAdapter(adapter);
                        Log.d("LATLONG", "Lat: " + currentLat + " Longt: " + currentLongt);
                    }
                });
            } else {
                CustomGrid adapter = new CustomGrid(BeachesList.this, beachesList, imageId, poiNoPhotos, Lat, Longt, adUnitID);
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

    @Override
    public void onResume() {
        super.onResume();
//        if (checkPermissions()) {
//            getLastLocation();
//        }
    }

    class Value implements Comparator<Place>{

        @Override
        public int compare(Place a1, Place a2) {
            return Float.compare(a1.getDistance(),a2.getDistance());
        }
    }


}
