package com.example.apurba.disaster.disasterreport;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.util.List;

public class StatisticsLocationDetailsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<List<EarthQuakeItem>>{

    private RecyclerView recyclerView;
    private TextView totalTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_location_details);

        Intent intent = getIntent();
        final String location =
                intent.getStringExtra(EarthQuakeFragment.EXTRA_MESSAGE_1);

        setAppBar(location);
        setCustomUpArraow();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        totalTextView = findViewById(R.id.total_text_view);

    }

    /** private void setAppBar() method
     *  set up the custom app bar
     */
    private void setAppBar(String location){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // disable the up arrow of the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        CollapsingToolbarLayout collapsingToolbar =
                findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle(location);
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.appLevel));
    }

    private void setCustomUpArraow(){
        final Drawable upArrow = getResources().getDrawable(R.drawable.ic_back_arrow_colored);
        upArrow.setColorFilter(getResources().getColor(R.color.upArrowColerd),
                PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }





    @Override
    public Loader<List<EarthQuakeItem>> onCreateLoader(int id, Bundle args) {
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<EarthQuakeItem>> loader, List<EarthQuakeItem> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<EarthQuakeItem>> loader) {

    }
}
