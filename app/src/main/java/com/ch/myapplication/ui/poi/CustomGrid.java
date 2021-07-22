package com.ch.myapplication.ui.poi;

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
import com.ch.myapplication.ui.beaches.BeachesList;
import com.google.android.gms.ads.interstitial.InterstitialAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class CustomGrid extends BaseAdapter {
    private Context mContext;
    private String[] web;
    private int[] Imageid;
    private int[] noOfPhotos;
    private double[] Lat;
    private double[] Longt;
    private String lat;
    private String longt;
    private InterstitialAd mInterstitialAd;
    private int tries = 1;
    private int shown = 2;
    boolean flag = false;
    private List<Place> place = new ArrayList<>();
    private Boolean way = false;


    public CustomGrid(Context c, String[] web, int[] Imageid, int[] noOfPhotos, double[] Lat, double[] Longt, String adUnitID) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.noOfPhotos = noOfPhotos;
        this.Lat = Lat;
        this.Longt = Longt;


    }

    public CustomGrid(Context c, String[] web, int[] Imageid, int[] noOfPhotos, double[] Lat, double[] Longt, String lat, String longt) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.noOfPhotos = noOfPhotos;
        this.Lat = Lat;
        this.Longt = Longt;
        this.lat = lat;
        this.longt = longt;
        flag = true;

    }

    public CustomGrid(Context c, List<Place> p, String lat, String longt) {
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
        } else {
            textView.setText(place.get(position).getName());
            imageView.setImageResource(place.get(position).getImageID());
            TextView distance = grid.findViewById(R.id.grid_distance);
//            float distanceTo = place.get(position).getDistance();
//            String to = String.format("%.2f", distanceTo);
//            distance.setText("Distance: " + to + " km");
            if (flag) {


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


    private void startActivity(String poi, int position) {
        Intent intent = new Intent(mContext, POIDetails.class);
        intent.putExtra("position", position);
        intent.putExtra("poi", poi);
        intent.putExtra("number", noOfPhotos[position]);
        intent.putExtra("lat", Lat[position]);
        intent.putExtra("longt", Longt[position]);
        mContext.startActivity(intent);

    }

    private void startActivity2(String poi, int position) {
        Intent intent = new Intent(mContext, POIDetails.class);
        intent.putExtra("position", position);
        intent.putExtra("poi", poi);
        intent.putExtra("number", place.get(position).getNoOfPhotos());
        intent.putExtra("lat", place.get(position).getLat());
        intent.putExtra("longt", place.get(position).getLongt());
        mContext.startActivity(intent);

    }

    class Value implements Comparator<Place> {

        @Override
        public int compare(Place a1, Place a2) {
            return Float.compare(a2.getDistance(), a1.getDistance());
        }
    }


}