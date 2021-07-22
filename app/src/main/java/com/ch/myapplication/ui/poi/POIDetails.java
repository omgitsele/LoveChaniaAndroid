package com.ch.myapplication.ui.poi;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.res.Resources;

import com.ch.myapplication.R;

import com.ch.myapplication.ui.MyCustomAdapter;
import com.ch.myapplication.ui.beaches.MapsActivity;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;


public class POIDetails extends AppCompatActivity {


    ImageView imageView;
    private int pos = -1;
    private final int PICK_IMAGE_REQUEST = 71;
    double lat;
    double longt;
    String poi;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;
    LinearLayout directionsLinearLayout;

    MyCustomAdapter myCustomPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_beaches_details);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        String name = getIntent().getStringExtra("poi"); //getting the woof name from Woofs activity
        poi = name;
        setTitle(name);
        int pos = getIntent().getIntExtra("position", 0);
        int numberOfPhotos = getIntent().getIntExtra("number", 0);
        lat = getIntent().getDoubleExtra("lat", 0);
        longt = getIntent().getDoubleExtra("longt", 0);
        int[] images = new int[numberOfPhotos];
        assert name != null;
        name = name.replace(" ", "");
        name = name.toLowerCase(); // Changing the given name to Lowercase in order to be readable
        for (int l = 0; l< numberOfPhotos; l++)
        {
            String photo = name+l;
            int drawableId = getResources().getIdentifier(photo, "drawable", getPackageName());
            images[l] = drawableId;


        }

        imageView = findViewById(R.id.img);
        viewPager = findViewById(R.id.viewPager);

        sliderDotspanel =  findViewById(R.id.SliderDots);
        directionsLinearLayout = findViewById(R.id.directionsLinearLayoutBPOI);


        myCustomPagerAdapter = new MyCustomAdapter(POIDetails.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);


        dotscount = myCustomPagerAdapter.getCount();
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

                for(int i = 0; i< dotscount; i++){
                    dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));
                }

                dots[position].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });



        TextView txtFile = findViewById(R.id.description);
        String text = null;
        try {
            text = "";
            InputStream inputStream = getAssets().open(name+".txt");

            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            text = new String(buffer);

        } catch (IOException e) {
            e.printStackTrace();
        }
        txtFile.setText(new StringBuilder().append("\n\n").append(text).toString());

//        int id = getResources().getIdentifier(name, "drawable", getPackageName()); //Create the int id of the image so it can be parseable
//        Drawable drawable = getResources().getDrawable(id);
//        imageView.setBackground(drawable); // setting the image


//        String finalName = name;
//        mButton.setOnClickListener(view -> {
//            Intent intent = new Intent(POIDetails.this, MapsActivity.class);
////            intent.putExtra("name", finalName);
//            intent.putExtra("lat", lat);
//            intent.putExtra("longt", longt);
//            intent.putExtra("name", poi);
//            startActivity(intent);
//        });


        directionsLinearLayout.setOnClickListener(view -> {
//            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+lat+"0,"+longt+"0"));
//            startActivity(intent);
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("https")
                    .authority("www.google.com")
                    .appendPath("maps")
                    .appendPath("dir")
                    .appendPath("")
                    .appendQueryParameter("api", "1")
                    .appendQueryParameter("destination", lat + "," + longt);
            String url = builder.build().toString();
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }


    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
