package com.example.apurba.disaster.disasterreport;

/** public class EarthQuakeDetailsActivity class:
 *
 *  Created by Apurba on 3/13/2018.
 *  A simple {@link android.support.v7.app.AppCompatActivity} subclass
 *  creates selected items details activity
 */

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class EarthQuakeDetailsActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "url for selected earthquake";
    public static final String EXTRA_MESSAGE2 = "title";
    public static final String TITTLE = "Earthquake Details";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earth_quake_details);

        setCustomTittle();

        setCustomUpArrow();

        // receive the intent
        Intent intent = getIntent();
        final String location =
                intent.getStringExtra(EarthQuakeFragment.EXTRA_MESSAGE_1);
        final double magnitude =
                intent.getDoubleExtra(EarthQuakeFragment.EXTRA_MESSAGE_2, 0.0);
        final String url =
                intent.getStringExtra(EarthQuakeFragment.EXTRA_MESSAGE_3);

        setAllViews(location, magnitude);

        setShareButton(url);

        setMoreButton(url, magnitude, location);
    }

    private void setAllViews( final String location, final double magnitude){
        // set loaction tittle
        TextView locationTextView = findViewById(R.id.title);
        locationTextView.setText(location);

        // set magnitude text
        TextView magnitudeView = findViewById(R.id.perceived_magnitude);
        HelperClass mHelper = new HelperClass(this);
        magnitudeView.setText(mHelper.formatedMagnitude(magnitude));

        // set background color of the magnitude text
        GradientDrawable magnitudeCircle =
                (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = mHelper.getMagnitudeColor(magnitude);
        magnitudeCircle.setColor(magnitudeColor);
    }

    private void setCustomUpArrow(){

        final Drawable upArrow = getResources().getDrawable(R.drawable.back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.appLevel),
                PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    private void setCustomTittle(){
        SpannableString s = new SpannableString(TITTLE);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.appLevel)),
                0,
                TITTLE.length(),
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setElevation(0);
    }

    private void setMoreButton(final String url,
                               final double magnitude,
                               final String location){
        Button moreButton = findViewById(R.id.more_button);
        moreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(EarthQuakeDetailsActivity.this,
                            WebsiteViewActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, url);
                    intent.putExtra(EXTRA_MESSAGE2, "" + magnitude + " " + location);
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(EarthQuakeDetailsActivity.this, "Error: " +
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setShareButton(final String url){
        Button shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, url);
                shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

}
