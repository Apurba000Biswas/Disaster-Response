package com.example.apurba.disaster.disasterreport;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class FloodDetailsActivity extends AppCompatActivity {

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

        setAllViews(eaAreaName, county, riverOrSea, severity, severityLevel, timeRaised);
    }

    private void setAllViews(String eaAreaName,String county,String riverOrSea,String severity,String severityLevel,String timeRaised){

        TextView eaAreaNameTextView = findViewById(R.id.ea_areaname_text_field);
        TextView countyTextView = findViewById(R.id.county_text_field);
        TextView riverOrSeaNameTextView = findViewById(R.id.river_or_sea_text_field);
        TextView severitytextView = findViewById(R.id.severity_text_field);
        TextView severityLevelTextView = findViewById(R.id.severityLevel_text_field);
        TextView timeRaisedTextView = findViewById(R.id.time_raised_text_field);

        eaAreaNameTextView.setText(eaAreaName);
        countyTextView.setText(county);
        riverOrSeaNameTextView.setText(riverOrSea);
        severitytextView.setText(severity);
        severityLevelTextView.setText(severityLevel);
        timeRaisedTextView.setText(timeRaised);
    }
}
