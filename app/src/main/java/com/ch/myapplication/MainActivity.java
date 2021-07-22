package com.ch.myapplication;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import com.ch.myapplication.ui.accommodation.AccommodationList;
import com.ch.myapplication.ui.beaches.BeachesList;
import com.ch.myapplication.ui.generalinfo.GeneralInfo;
import com.ch.myapplication.ui.otheractivities.OtherActivitiesList;
import com.ch.myapplication.ui.poi.CustomGrid;
import com.ch.myapplication.ui.poi.PointsOfInterest;
import com.ch.myapplication.ui.rnb.RestaurantBars;
import com.ch.myapplication.ui.shops.ShopsActivity;
import com.ch.myapplication.ui.tradition.Tradition;

import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
//    private ImageView logo;
    private int clicks = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
//        logo = findViewById(R.id.logo_circle);
        setTitle("Love Chania");

        setSupportActionBar(toolbar);
        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        NavigationView mNavigationView = findViewById(R.id.nav_view);
     //   GridLayout mainGrid = findViewById(R.id.mainGrid);
//        setSingleEvent(mainGrid);

        if (mNavigationView != null) {
            mNavigationView.setNavigationItemSelectedListener(this);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);




        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_beaches,
                R.id.nav_points_of_interest,
                R.id.nav_general_info,
                R.id.nav_history_and_tradition,
                R.id.nav_rnb,
                R.id.nav_accommodation,
                R.id.nav_shops,
                R.id.nav_other_activities
                )
                .setDrawerLayout(drawer)
                .build();
        drawer.setOnClickListener(view -> {

        });
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        assert mNavigationView != null;
        NavigationUI.setupWithNavController(mNavigationView, navController);

    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Log.d("ITEM:", "ID: "+id);
        if (id == R.id.nav_beaches){
            Intent intent = new Intent(MainActivity.this, BeachesList.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_points_of_interest){
            Intent intent = new Intent(MainActivity.this, PointsOfInterest.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_general_info){
            Intent intent = new Intent(MainActivity.this, GeneralInfo.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_history_and_tradition){
            Intent intent = new Intent(MainActivity.this, Tradition.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_rnb){
            Intent intent = new Intent(MainActivity.this, RestaurantBars.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_accommodation){
            Intent intent = new Intent(MainActivity.this, AccommodationList.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_shops){
            Intent intent = new Intent(MainActivity.this, ShopsActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_other_activities){
            Intent intent = new Intent(MainActivity.this, OtherActivitiesList.class);
            startActivity(intent);
        }


        DrawerLayout drawer =  findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    return true;
    }




}

