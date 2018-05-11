package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/13/2018.
 *
 * EarthQuakeDetailsActivity:
 * creates selected items details activity
 */

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
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

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;


public class EarthQuakeDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "url for selected earthquake";
    public static final String EXTRA_MESSAGE2 = "title";
    public static final String TITTLE = "Earthquake Details";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earth_quake_details);

        // set custom tittle
        SpannableString s = new SpannableString(TITTLE);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, TITTLE.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        // set custom up arrow
        final Drawable upArrow = getResources().getDrawable(R.drawable.back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.main_background), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        // receive the intent
        Intent intent = getIntent();
        final String location = intent.getStringExtra(EarthQuakeFragment.EXTRA_MESSAGE_1);
        final double magnitude = intent.getDoubleExtra(EarthQuakeFragment.EXTRA_MESSAGE_2, 0.0);
        final String url = intent.getStringExtra(EarthQuakeFragment.EXTRA_MESSAGE_3);

        // set loaction tittle
        TextView locationTextView = findViewById(R.id.title);
        locationTextView.setText(location);

        // set magnitude text
        TextView magnitudeView = findViewById(R.id.perceived_magnitude);
        HelperClass mHelper = new HelperClass(this);
        magnitudeView.setText(mHelper.formatedMagnitude(magnitude));

        // set backgground color of the magnitude text
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = mHelper.getMagnitudeColor(magnitude);
        magnitudeCircle.setColor(magnitudeColor);

        // create facebook share link conetnt and set it to the button
        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(url))
                .build();
        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);

        // more button response
        Button moreButton = findViewById(R.id.more_button);
        moreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(EarthQuakeDetailsActivity.this, WebsiteViewActivity.class);
                    // url
                    intent.putExtra(EXTRA_MESSAGE, url);
                    // tittle
                    intent.putExtra(EXTRA_MESSAGE2, "" + magnitude + " " + location);
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(EarthQuakeDetailsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
