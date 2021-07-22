package com.ch.myapplication.ui.ads;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;


import androidx.annotation.RequiresApi;

import com.ch.myapplication.Place;
import com.ch.myapplication.R;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AdsCustomGrid extends BaseAdapter {
    private Context mContext;
    private String[] web;
    private int[] Imageid;
    private int[] noOfPhotos;
    private double[] Lat;
    private double[] Longt;
    private char[] hasPhone;
    private String[] phoneList;
    private InterstitialAd mInterstitialAd;
    private int tries = 1;
    private int shown = 2;
    private Boolean way = false;
    private String lat;
    private String longt;
    private List<Place> place = new ArrayList<>();
    private Boolean flag = false;


    public AdsCustomGrid(Context c, String[] web, int[] Imageid, int[] noOfPhotos, double[] Lat, double[] Longt, char[] hasPhone, String[] phoneList, String adUnitID) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.noOfPhotos = noOfPhotos;
        this.Lat = Lat;
        this.Longt = Longt;
        this.hasPhone = hasPhone;
        this.phoneList = phoneList;
    }

    public AdsCustomGrid(Context c, List<Place> p, String lat, String longt) {
        mContext = c;
        place = p;
        way = true;
        this.lat = lat;
        this.longt = longt;
        flag = true;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (web == null) {
            return place.size();
        }
        return web.length;
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return true;
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void sort() {
        //place.sort(Comparator.comparing(Place::getDistance));
        Collections.sort(place, (p1, p2) -> (int) (p1.getDistance() - p2.getDistance()));
        this.notifyDataSetChanged();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        View grid;


        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            assert inflater != null;
            grid = inflater.inflate(R.layout.grid_single, parent, false);

        } else {
            grid = convertView;
        }
        TextView textView = grid.findViewById(R.id.grid_text);
        ImageButton imageView = grid.findViewById(R.id.grid_image);
        imageView.setClickable(true);
        if (!way) {

            textView.setText(web[position]);
            imageView.setImageResource(Imageid[position]);
            if (flag) {
                TextView distance = grid.findViewById(R.id.grid_distance);

                Location locationA = new Location("point A");

                locationA.setLatitude(Lat[position]);
                locationA.setLongitude(Longt[position]);

                Location locationB = new Location("point B");

                locationB.setLatitude(Double.parseDouble(lat));
                locationB.setLongitude(Double.parseDouble(longt));

                float distanceTo = locationA.distanceTo(locationB) / 1000;

                String to = String.format("%.2f", distanceTo);

                distance.setText("Distance: " + to + " km");
            }
            imageView.setOnClickListener(view -> {

                startActivity(web[position], position);

            });
        }
        else
        {
            textView.setText(place.get(position).getName());
            imageView.setImageResource(place.get(position).getImageID());
            if (flag) {
                TextView distance = grid.findViewById(R.id.grid_distance);

                Location locationA = new Location("point A");

                locationA.setLatitude(place.get(position).getLat());
                locationA.setLongitude(place.get(position).getLongt());

                Location locationB = new Location("point B");

                locationB.setLatitude(Double.parseDouble(lat));
                locationB.setLongitude(Double.parseDouble(longt));

                float distanceTo = locationA.distanceTo(locationB) / 1000;
                place.get(position).setDistance(distanceTo);

                String to = String.format("%.2f", distanceTo);
                distance.setText("Distance: " + to + " km");
            }
            imageView.setOnClickListener(view -> {

                startActivity2(place.get(position).getName(), position);

            });
        }






        return grid;
    }


    private void startActivity(String ad, int position) {
        Intent intent = new Intent(mContext, AdsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("ad", ad);
        intent.putExtra("number", noOfPhotos[position]);
        intent.putExtra("lat", Lat[position]);
        intent.putExtra("longt", Longt[position]);
        intent.putExtra("hasPhone", hasPhone[position]);
        intent.putExtra("phoneNumber", phoneList[position]);
        mContext.startActivity(intent);
    }

    private void startActivity2(String ad, int position) {
        Intent intent = new Intent(mContext, AdsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("ad", ad);
        intent.putExtra("number", place.get(position).getNoOfPhotos());
        intent.putExtra("lat", place.get(position).getLat());
        intent.putExtra("longt", place.get(position).getLongt());
        intent.putExtra("hasPhone", place.get(position).getHasPhone());
        intent.putExtra("phoneNumber", place.get(position).getPhoneNumber());
        mContext.startActivity(intent);
    }

}