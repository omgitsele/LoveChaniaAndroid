package com.ch.myapplication.ui.beaches;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import androidx.fragment.app.Fragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.ch.myapplication.R;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private int pos = -1;
    private double lat;
    private double longt;
    private Context mContext=MapsActivity.this;
    private double currentLat;
    private double currentLongt;
    private String name;
    private GoogleApiClient googleApiClient;

    protected String beach;
    private static final int REQUEST = 112;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps2);
        lat = getIntent().getDoubleExtra("lat", 0.0);

        longt = getIntent().getDoubleExtra("longt", 0.0);
        name = getIntent().getStringExtra("name"); //getting the name from the corresponding activity
//        getCurrentLocation();




        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        //Initializing googleApiClient
        //Initializing googleApiClient
//        googleApiClient = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(LocationServices.API)
//                .build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


        // Add a marker in Sydney and move the camera
        LatLng mBeach = new LatLng(lat, longt);
        mMap.addMarker(new MarkerOptions()
                .title(name)
                .position(mBeach)
        );
//        mMap.addMarker(new MarkerOptions()
//                .title("Current Location")
//                .position(new LatLng(currentLat, currentLongt)
//                )
//        );
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mBeach));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(12));

    }

    //Getting current location
//    private void getCurrentLocation() {
//        String[] PERMISSIONS = {android.Manifest.permission.ACCESS_COARSE_LOCATION,android.Manifest.permission.ACCESS_FINE_LOCATION};
//        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            ActivityCompat.requestPermissions((Activity) mContext, PERMISSIONS, REQUEST );
//            return;
//        }
//        Location location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
//        if (location != null) {
//            //Getting longitude and latitude
//            currentLongt = location.getLongitude();
//            currentLat = location.getLatitude();
//
//        }
    //}
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        switch (requestCode) {
//            case REQUEST: {
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    getCurrentLocation();
//                } else {
//                    Toast.makeText(mContext, "The app was not allowed to access your location", Toast.LENGTH_LONG).show();
//                }
//            }
//        }
//    }
//    @Override
//    protected void onStart() {
//        googleApiClient.connect();
//        super.onStart();
//    }
//
//    @Override
//    protected void onStop() {
//        googleApiClient.disconnect();
//        super.onStop();
//    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        getCurrentLocation();
//    }

//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        getCurrentLocation();
//    }

//    @Override
//    public void onConnectionSuspended(int i) {
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
}
