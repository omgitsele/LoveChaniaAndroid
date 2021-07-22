package com.ch.myapplication.ui.beaches;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Debug;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ch.myapplication.R;
import com.ch.myapplication.ui.MyCustomAdapter;
import com.ch.myapplication.ui.poi.POIDetails;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

public class BeachesDetails extends AppCompatActivity {

    ImageView imageView;
    LinearLayout directionsLinearLayout;
    private int pos = -1;
    private final int PICK_IMAGE_REQUEST = 71;
    double lat;
    double longt;
    String poi;
    ViewPager viewPager;

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
        name = name.replace(" ", "");
        name = name.toLowerCase(); // Changing the given name to Lowercase in order to be readable
        for (int l = 0; l< numberOfPhotos; l++)
        {
            String photo = name+l;
            //int ph = Integer.parseInt(photo);
//            Context context = imageView.getContext();
            int drawableId = getResources().getIdentifier(photo, "drawable", getPackageName());
            // images[l] =  getApplicationContext().getResources().getIdentifier(photo, "drawable", getApplicationContext().getPackageName());
            images[l] = drawableId;


        }

        imageView = findViewById(R.id.img);
        viewPager = findViewById(R.id.viewPager);
        directionsLinearLayout = findViewById(R.id.directionsLinearLayoutBPOI);

        myCustomPagerAdapter = new MyCustomAdapter(BeachesDetails.this, images);
        viewPager.setAdapter(myCustomPagerAdapter);

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
        txtFile.setText(new StringBuilder().append("\nDescription: \n\n").append(text).toString());

//        int id = getResources().getIdentifier(name, "drawable", getPackageName()); //Create the int id of the image so it can be parseable
//        Drawable drawable = getResources().getDrawable(id);
//        imageView.setBackground(drawable); // setting the image



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


