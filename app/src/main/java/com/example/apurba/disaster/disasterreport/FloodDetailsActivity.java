package com.example.apurba.disaster.disasterreport;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


/**
 * creates a details activity for selected flood item
 */
public class FloodDetailsActivity extends AppCompatActivity {

    private static final String SHARE_URL = "https://www.gov.uk/government/organisations/environment-agency";
    private static final String TITLE = "Flood Details";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flood_details);

        // set custom tittle
        SpannableString s = new SpannableString(TITLE);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.appLevel)),
                0, TITLE.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);
        getSupportActionBar().setElevation(0);

        // set custom up arrow
        final Drawable upArrow = getResources().getDrawable(R.drawable.back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.appLevel), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);

        // receive intent and all its extra messages
        Intent intent = getIntent();
        String eaAreaName = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE1);
        String county = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE2);
        String riverOrSea = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE3);
        String severity = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE4);
        String severityLevel = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE5);
        String timeRaised = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE6);
        String message = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE7);
        int severityLevelInt = intent.getIntExtra(FloodFragment.EXTRA_MESSAGE8, 0);

        // set all views
        setAllViews(eaAreaName,
                county,
                riverOrSea,
                severity,
                severityLevel,
                timeRaised,
                message,
                severityLevelInt);

        // set facebook share button
        Button shareButton = findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, SHARE_URL);
                shareIntent.putExtra(Intent.EXTRA_TEXT, SHARE_URL);
                startActivity(Intent.createChooser(shareIntent, "Share using"));
            }
        });
    }

    /**
     * Sets all views with given text
     */
    private void setAllViews(String eaAreaName,
                             String county,
                             String riverOrSea,
                             String severity,
                             String severityLevel,
                             String timeRaised,
                             String message,
                             int severityLevelInt){

        // finds all views to set
        TextView eaAreaNameTextView = findViewById(R.id.ea_areaname_text_field);
        TextView countyTextView = findViewById(R.id.county_text_field);
        TextView riverOrSeaNameTextView = findViewById(R.id.river_or_sea_text_field);
        TextView severitytextView = findViewById(R.id.severity_text_field);
        TextView severityLevelTextView = findViewById(R.id.severityLevel_text_field);
        TextView timeRaisedTextView = findViewById(R.id.time_raised_text_field);
        TextView messageTextView = findViewById(R.id.mesgga_text_field);

        // set all views
        eaAreaNameTextView.setText(eaAreaName);
        countyTextView.setText(county);
        riverOrSeaNameTextView.setText(riverOrSea);
        severitytextView.setText(severity);
        severityLevelTextView.setText(severityLevel);
        timeRaisedTextView.setText(timeRaised);
        messageTextView.setText(message);

        HelperClass mHepler = new HelperClass(this);
        GradientDrawable severityCircle = (GradientDrawable) severityLevelTextView.getBackground();
        int circleColor = mHepler.getSeverityCircleColor(severityLevelInt);
        severityCircle.setColor(circleColor);
    }
}
