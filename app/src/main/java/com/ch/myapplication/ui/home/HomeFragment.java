package com.ch.myapplication.ui.home;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityGroup;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.ch.myapplication.MainActivity;
import com.ch.myapplication.R;
import com.ch.myapplication.ui.accommodation.AccommodationList;
import com.ch.myapplication.ui.ads.AdsActivity;
import com.ch.myapplication.ui.beaches.BeachesList;
import com.ch.myapplication.ui.generalinfo.GeneralInfo;
import com.ch.myapplication.ui.otheractivities.OtherActivitiesList;
import com.ch.myapplication.ui.poi.PointsOfInterest;
import com.ch.myapplication.ui.rnb.RestaurantBars;
import com.ch.myapplication.ui.shops.ShopsActivity;
import com.ch.myapplication.ui.tradition.Tradition;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private String currentLat;
    private String currentLongt;
    private FusedLocationProviderClient mFusedLocationClient;
    private final static int PERMISSION_ID = 144;
    private boolean flag = false;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        homeViewModel =ViewModelProviders.of(this).get(HomeViewModel.class);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(getContext());


        View view = inflater.inflate(R.layout.fragment_home, container, false);
        GridLayout mainGrid = view.findViewById(R.id.mainGrid);
        ViewCompat.setNestedScrollingEnabled(mainGrid,true);
        setSingleEvent(mainGrid);

        // View root = inflater.inflate(R.layout.fragment_home, container, false);

        //final ImageView imageView = root.findViewById(R.id.main_img);


        //imageView.setBackground(drawable); // setting the image
        //homeViewModel.getText().observe(getViewLifecycleOwner(), s -> textView.setText(s));
        return view;
    }

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item of Main Grid
        for (int i = 0; i < mainGrid.getChildCount(); i++) {
            //You can see , all child item is CardView , so we just cast object to CardView
            CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(view -> {
                switch (finalI) {
                    case 0:
                        Intent intent = new Intent(getActivity(), BeachesList.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent chat = new Intent(getActivity(), PointsOfInterest.class);
                        startActivity(chat);
                        break;
                    case 2:
                        Intent general = new Intent(getActivity(), GeneralInfo.class);
                        startActivity(general);
                        break;
                    case 3:
                        Intent tradition = new Intent(getActivity(), Tradition.class);
                        startActivity(tradition);
                        break;
                    case 4:
                        double latkiss = 35.4935823;
                        double longtkiss = 23.6495404;
                        Intent kiss = new Intent(getActivity(), AdsActivity.class);
                        kiss.putExtra("ad", "Discovery Kissamos");
                        kiss.putExtra("hasPhone", '0');
                        kiss.putExtra("number", 1);
                        kiss.putExtra("lat", latkiss);
                        kiss.putExtra("longt",longtkiss);
                        startActivity(kiss);
                        break;
                    case 5:
                        double latbrewery = 35.486979;
                        double longtbrewery = 23.825745;
                        Intent brewery = new Intent(getActivity(), AdsActivity.class);
                        brewery.putExtra("ad", "Cretan Brewery");
                        brewery.putExtra("hasPhone", '1');
                        brewery.putExtra("phoneNumber", "+30 28240 31 002");
                        brewery.putExtra("number", 16);
                        brewery.putExtra("lat", latbrewery);
                        brewery.putExtra("longt",longtbrewery);
                        startActivity(brewery);
                        break;
                    case 6:
                        Intent rnb = new Intent(getActivity(), RestaurantBars.class);
                        startActivity(rnb);
                        break;
                    case 7:
                        Intent accom = new Intent(getActivity(), AccommodationList.class);
                        startActivity(accom);
                        break;
                    case 8:
                        Intent shops = new Intent(getActivity(), ShopsActivity.class);
                        startActivity(shops);
                        break;
                    case 9:
                        Intent other = new Intent(getActivity(), OtherActivitiesList.class);
                        startActivity(other);
                        break;
                    case 10:
                        //openKtel(getView());
                        double lat = 35.5120401;
                        double longt = 24.0168622;
                        Intent ktel = new Intent(getActivity(), AdsActivity.class);
                        ktel.putExtra("ad", "Ktel Public Bus");
                        ktel.putExtra("number", 2);
                        ktel.putExtra("hasPhone",'1');
                        ktel.putExtra("phoneNumber", "+30 2821 093052");
                        ktel.putExtra("lat", lat);
                        ktel.putExtra("longt",longt);
                        startActivity(ktel);
                        break;
                    case 11:
                        double latcruise = 35.516664;
                        double longtcruise = 23.635553;
                        Intent cruise = new Intent(getActivity(), AdsActivity.class);
                        cruise.putExtra("ad", "Cretan Daily Cruises");
                        cruise.putExtra("hasPhone", '1');
                        cruise.putExtra("phoneNumber","+30 28220 22 888");
                        cruise.putExtra("number", 3);
                        cruise.putExtra("lat", latcruise);
                        cruise.putExtra("longt",longtcruise);
                        startActivity(cruise);
                        break;
                    default:

                        break;

                }
            });
        }
    }
//
//    public void openKtel (View view) {
//        goToUrl ( "https://www.e-ktel.com/en/");
//    }
//
//    private void goToUrl (String url) {
//        Uri uriUrl = Uri.parse(url);
//        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
//        startActivity(launchBrowser);
//    }


}