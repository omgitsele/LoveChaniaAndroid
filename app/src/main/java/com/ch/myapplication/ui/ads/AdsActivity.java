package com.ch.myapplication.ui.ads;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.myapplication.R;
import com.ch.myapplication.ui.MyCustomAdapter;
import com.ch.myapplication.ui.beaches.BeachesDetails;
import com.ch.myapplication.ui.beaches.MapsActivity;
import com.ch.myapplication.ui.poi.POIDetails;

import java.io.IOException;
import java.io.InputStream;

public class AdsActivity extends AppCompatActivity {


    ImageView imageView;
    LinearLayout directionsLinearLayout;
    private int pos = -1;
    private final int PICK_IMAGE_REQUEST = 71;
    double lat;
    double longt;
    char hasPhone = '0';
    String poi;
    ViewPager viewPager;
    LinearLayout sliderDotspanel;
    LinearLayout advPhone;
    //Phone number TextView
    TextView phoneNumberTextView;
    String phoneNumber;
    // URL TextView
//    TextView urlTextView;
//    String urlToVisit;

    private int dotscount;
    private ImageView[] dots;


    MyCustomAdapter myCustomPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_ads);
        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
        //Getting all the data from the previous activity
        String name = getIntent().getStringExtra("ad"); //getting the woof name from Woofs activity
        poi = name;
        setTitle(name);
       // int pos = getIntent().getIntExtra("position", 0);
        int numberOfPhotos = getIntent().getIntExtra("number", 0);
        lat = getIntent().getDoubleExtra("lat", 0);
        hasPhone = getIntent().getCharExtra("hasPhone", '0');
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        longt = getIntent().getDoubleExtra("longt", 0);

        //setting up all the photos
        int[] images = new int[numberOfPhotos];
        assert name != null;
        name = name.replace(" ", "");
        name = name.replace("'","");
        name = name.replace("&","");
        name = name.replace(".","");
        name = name.toLowerCase(); // Changing the given name to Lowercase in order to be readable
        for (int l = 0; l< numberOfPhotos; l++)
        {
            String photo = name+l;
            int drawableId = getResources().getIdentifier(photo, "drawable", getPackageName());
            images[l] = drawableId;
        }

        //Setting all the necessary views
        imageView = findViewById(R.id.img);
        viewPager = findViewById(R.id.viewPager);
        sliderDotspanel =  findViewById(R.id.SliderDots);
        phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        advPhone = findViewById(R.id.advPhone);
        directionsLinearLayout = findViewById(R.id.directionsLinearLayout);

        //checking to see if ad has a phone number
        //if it does then we set its view to visible and set the text for the phone
        if (hasPhone == '1')
        {
            advPhone.setVisibility(View.VISIBLE);
            phoneNumberTextView.setText(phoneNumber);
        }
        else
            advPhone.setVisibility(View.INVISIBLE);

        //setting the custom adapter
        myCustomPagerAdapter = new MyCustomAdapter(AdsActivity.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);

        //creating the dots shown below the photos
        dotscount =numberOfPhotos;
        dots = new ImageView[dotscount];

        for(int i = 0; i < dotscount; i++){

            dots[i] = new ImageView(this);
            dots[i].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.non_active_dot));

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            params.setMargins(8, 0, 8, 0);

            sliderDotspanel.addView(dots[i], params);

        }

        dots[0].setImageDrawable(ContextCompat.getDrawable(getApplicationContext(), R.drawable.active_dot));

        //when the user scrolls the photos we change their appearance
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
        //Creating the description
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
        Linkify.addLinks(txtFile, Linkify.WEB_URLS);

//        int id = getResources().getIdentifier(name, "drawable", getPackageName()); //Create the int id of the image so it can be parseable
//        Drawable drawable = getResources().getDrawable(id);
//        imageView.setBackground(drawable); // setting the image


        //Setting up the google maps activity
//        String finalName = name;
//        mButton.setOnClickListener(view -> {
//            Intent intent = new Intent(AdsActivity.this, MapsActivity.class);
//            intent.putExtra("name", finalName);
//            intent.putExtra("lat", lat);
//            intent.putExtra("longt", longt);
//            intent.putExtra("name", poi);
//            startActivity(intent);
//        });
//        mButton.setOnClickListener(view -> {
////            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse("http://maps.google.com/maps?daddr="+lat+"0,"+longt+"0"));
////            startActivity(intent);
//            Uri.Builder builder = new Uri.Builder();
//            builder.scheme("https")
//                    .authority("www.google.com")
//                    .appendPath("maps")
//                    .appendPath("search")
//                    .appendPath("")
//                    .appendQueryParameter("api", "1")
//                    .appendQueryParameter("destination", lat + "," + longt);
//            String url = builder.build().toString();
//            Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//            startActivity(i);
//        });

        directionsLinearLayout.setOnClickListener(view -> {
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
    //creating the back button action
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void openPhone(View view)
    {
        String phone = phoneNumberTextView.getText().toString();
        String fullPhone = "tel:"+phone;
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse(fullPhone));
        startActivity(intent);
    }
}


