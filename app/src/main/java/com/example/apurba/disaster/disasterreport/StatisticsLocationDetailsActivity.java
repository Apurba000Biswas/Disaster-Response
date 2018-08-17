package com.example.apurba.disaster.disasterreport;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apurba.disaster.disasterreport.database.DisasterReportDbContract.EarthQuakeEntry;

import java.util.ArrayList;
import java.util.List;

public class StatisticsLocationDetailsActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int CURSOR_DATA_LOADER_ID = 1;

    private List<EarthQuakeItem> mDataset;
    private StatisticsDetailsAdapter mAdapter;
    private RecyclerView recyclerView;
    private TextView totalTextView;
    private String displayLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics_location_details);

        Intent intent = getIntent();
        displayLocation =
                intent.getStringExtra(EarthQuakeFragment.EXTRA_MESSAGE_1);

        setAppBar(displayLocation);
        setCustomUpArraow();

        mDataset = new ArrayList<>();
        mAdapter = new StatisticsDetailsAdapter(this, mDataset);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        totalTextView = findViewById(R.id.total_text_view);
        GradientDrawable magnitudeCircle = (GradientDrawable) totalTextView.getBackground();
        magnitudeCircle.setColor(ContextCompat.getColor(this, R.color.upArrowColerd));

        FloatingActionButton deleteButton = findViewById(R.id.delete_location);
        setDeleteButton(deleteButton);

        getSupportLoaderManager().initLoader(
                CURSOR_DATA_LOADER_ID,
                null,
                this);
    }


    private void setDeleteButton(FloatingActionButton button){
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void showDeleteConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_this_Location_dialog_msg);

        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteThisLocation();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User clicked the "Cancel" button, so dismiss the dialog
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.upArrowColerd));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.upArrowColerd));
    }

    private void deleteThisLocation(){
        int rowsDeleted = 0;
        String selection = EarthQuakeEntry.COLUMN_LOCATION +
                " = " +
                "\"" +
                displayLocation +
                "\"";
        rowsDeleted = getContentResolver().delete(
                EarthQuakeEntry.CONTENT_URI,  // uri
                selection,           // selection
                null);    // selectionArgs
        if (rowsDeleted != 0){
            Toast.makeText(this,
                    "" + displayLocation +
                            " deleted successfully",
                    Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this,
                    "Error with deleting "
                            + displayLocation,
                    Toast.LENGTH_SHORT).show();
        }
        finish();
    }


    private void setAppBar(String location){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {EarthQuakeEntry.COLUMN_LOCATION,
                EarthQuakeEntry.COLUMN_MAGNITUDE,
                EarthQuakeEntry.COLUMN_GEO_LOCATION,
                EarthQuakeEntry.COLUMN_TIME,
                EarthQuakeEntry.COLUMN_URL};
        String selection =  EarthQuakeEntry.COLUMN_LOCATION +
                " LIKE " +
                "'%" +
                displayLocation +
                "%'";

        return new CursorLoader(this,
                EarthQuakeEntry.CONTENT_URI,
                projection,
                selection,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int locationColumnIndex = data.getColumnIndex(EarthQuakeEntry.COLUMN_LOCATION);
        int magnitudeColumnIndex = data.getColumnIndex(EarthQuakeEntry.COLUMN_MAGNITUDE);
        int geoLocationColumnIndex = data.getColumnIndex(EarthQuakeEntry.COLUMN_GEO_LOCATION);
        int timeColumnIndex = data.getColumnIndex(EarthQuakeEntry.COLUMN_TIME);
        int urlColumnIndex = data.getColumnIndex(EarthQuakeEntry.COLUMN_URL);
        int count = 0 ;

        if(data.getCount() != 0){
            data.moveToFirst();
            String location;
            String magnitude;
            String geoLocation;
            String time;
            String url;
            do {
                location = data.getString(locationColumnIndex);
                if(TextUtils.equals(displayLocation, location)){
                    magnitude = data.getString(magnitudeColumnIndex);
                    geoLocation = data.getString(geoLocationColumnIndex);
                    time = data.getString(timeColumnIndex);
                    url = data.getString(urlColumnIndex);
                    EarthQuakeItem item = new EarthQuakeItem(
                            Double.parseDouble(magnitude),
                            geoLocation,
                            Long.parseLong(time),
                            url);
                    mDataset.add(item);
                    count ++ ;
                }
            }while (data.moveToNext());

            totalTextView.setText(String.valueOf(count));
            mAdapter.adAllData(mDataset);
            recyclerView.setAdapter(mAdapter);
            recyclerView.setVisibility(View.VISIBLE);
        } else{
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.clearData();
        mDataset.clear();
    }
}
