package com.example.apurba.disaster.disasterreport;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;

public class FloodDetailsActivity extends AppCompatActivity {

    private static final String SHARE_URL = "https://www.gov.uk/government/organisations/environment-agency";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.flood_details);

        Intent intent = getIntent();
        String eaAreaName = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE1);
        String county = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE2);
        String riverOrSea = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE3);
        String severity = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE4);
        String severityLevel = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE5);
        String timeRaised = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE6);
        String message = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE7);
        int severityLevelInt = intent.getIntExtra(FloodFragment.EXTRA_MESSAGE8, 0);
        setAllViews(eaAreaName, county, riverOrSea, severity, severityLevel, timeRaised, message, severityLevelInt);

        ShareLinkContent content = new ShareLinkContent.Builder()
                .setContentUrl(Uri.parse(SHARE_URL))
                .setQuote("Flood Severity Level = " + severityLevel + "\nGeographical Area: " + county)
                .build();
        ShareButton shareButton = (ShareButton)findViewById(R.id.fb_share_button);
        shareButton.setShareContent(content);

    }

    /**
     * Sets all views with given text
     */
    private void setAllViews(String eaAreaName,String county,String riverOrSea,String severity,String severityLevel,String timeRaised, String message, int severityLevelInt){

        TextView eaAreaNameTextView = findViewById(R.id.ea_areaname_text_field);
        TextView countyTextView = findViewById(R.id.county_text_field);
        TextView riverOrSeaNameTextView = findViewById(R.id.river_or_sea_text_field);
        TextView severitytextView = findViewById(R.id.severity_text_field);
        TextView severityLevelTextView = findViewById(R.id.severityLevel_text_field);
        TextView timeRaisedTextView = findViewById(R.id.time_raised_text_field);
        TextView messageTextView = findViewById(R.id.mesgga_text_field);

        eaAreaNameTextView.setText(eaAreaName);
        countyTextView.setText(county);
        riverOrSeaNameTextView.setText(riverOrSea);
        severitytextView.setText(severity);
        severityLevelTextView.setText(severityLevel);
        timeRaisedTextView.setText(timeRaised);
        messageTextView.setText(message);

        GradientDrawable severityCircle = (GradientDrawable) severityLevelTextView.getBackground();
        int circleColor = getSeverityColor(severityLevelInt);
        severityCircle.setColor(circleColor);
    }

    private int getSeverityColor(int severotyLevel){
        int circleColorResourceId;
        switch (severotyLevel){
            case 1:
                circleColorResourceId = R.color.severity1;
                break;
            case 2:
                circleColorResourceId = R.color.severity2;
                break;
            case 3:
                circleColorResourceId = R.color.severity3;
                break;
            case 4:
                circleColorResourceId = R.color.severity4;
                break;
            default:
                circleColorResourceId = R.color.wrongcolor;
                break;
        }
        return ContextCompat.getColor(this, circleColorResourceId);
    }
}
