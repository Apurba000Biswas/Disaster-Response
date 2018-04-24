package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/13/2018.
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

import android.support.v4.content.ContextCompat;
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

import java.text.DecimalFormat;


public class EarthQuakeDetailsActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "url for selected earthquake";
    public static final String EXTRA_MESSAGE2 = "title";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earth_quake_details);

        //setTitle("Earthquake Details");
        String title = "Earthquake Details";
        SpannableString s = new SpannableString(title);
        s.setSpan(new ForegroundColorSpan(Color.WHITE), 0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        final Drawable upArrow = getResources().getDrawable(R.drawable.back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.main_background), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);


        Intent intent = getIntent();
        final String location = intent.getStringExtra(EarthQuakeFragment.EXTRA_MESSAGE_1);
        final double magnitude = intent.getDoubleExtra(EarthQuakeFragment.EXTRA_MESSAGE_2, 0.0);
        final String url = intent.getStringExtra(EarthQuakeFragment.EXTRA_MESSAGE_3);

        TextView locationTextView = findViewById(R.id.title);
        locationTextView.setText(location);

        TextView magnitudeView = findViewById(R.id.perceived_magnitude);
        magnitudeView.setText(formatedMagnitude(magnitude));

        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        int magnitudeColor = getMagnitudeColor(magnitude);
        magnitudeCircle.setColor(magnitudeColor);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(url))
                .build();

        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);

        Button moreButton = findViewById(R.id.more_button);
        moreButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(EarthQuakeDetailsActivity.this, WebsiteViewActivity.class);
                    intent.putExtra(EXTRA_MESSAGE, url);
                    intent.putExtra(EXTRA_MESSAGE2, "" + magnitude + " " + location);
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(EarthQuakeDetailsActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


    private String formatedMagnitude(double mag){
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        return decimalFormatter.format(mag);
    }

    /**
     * Returns the approriate color for Magnitude text field
     */
    private int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0 :
            case 1 :
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2 :
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3 :
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4 :
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5 :
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6 :
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7 :
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8 :
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9 :
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(this, magnitudeColorResourceId);
    }
}
