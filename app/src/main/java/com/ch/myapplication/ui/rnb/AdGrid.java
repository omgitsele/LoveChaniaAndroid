package com.ch.myapplication.ui.rnb;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.myapplication.MainActivity;
import com.ch.myapplication.R;
import com.ch.myapplication.ui.ads.AdsActivity;

public class AdGrid extends BaseAdapter{
    private Context mContext;
    private final String[] web;
    private final int[] Imageid;
    private final int[] noOfPhotos;
    private final double[] Lat;
    private final double[] Longt;

    public AdGrid(Context c,String[] web,int[] Imageid, int[] noOfPhotos, double[] Lat, double[] Longt ) {
        mContext = c;
        this.Imageid = Imageid;
        this.web = web;
        this.noOfPhotos = noOfPhotos;
        this.Lat = Lat;
        this.Longt = Longt;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
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
        TextView textView =  grid.findViewById(R.id.grid_text);
        ImageButton imageView = grid.findViewById(R.id.grid_image);
        imageView.setClickable(true);
        textView.setText(web[position]);
        imageView.setImageResource(Imageid[position]);
        // imageView.setOnClickListener(v -> Toast.makeText(imageView.getContext(), "You Clicked at " + position+" NAME: "+web[position], Toast.LENGTH_SHORT).show());
        imageView.setOnClickListener(view -> startActivity(web[position], position));

        return grid;
    }


    private void startActivity(String ad, int position)
    {
        Intent intent = new Intent(mContext, AdsActivity.class);
        intent.putExtra("position", position);
        intent.putExtra("ad", ad);
        intent.putExtra("number", noOfPhotos[position]);
        intent.putExtra("lat", Lat[position]);
        intent.putExtra("longt", Longt[position]);
        mContext.startActivity(intent);
    }

}