package com.example.apurba.disaster.disasterreport.database;

/*
 * Created by Apurba on 8/13/2018.
 */

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import com.example.apurba.disaster.disasterreport.database.DisasterReportDbContract.EarthQuakeEntry;

import java.util.HashMap;
import java.util.Map;

public class DisasterDatabaseLoader implements LoaderManager.LoaderCallbacks<Cursor>{


    private Context mContext;
    LoaderManager loaderManager;
    private  Map<String,String> earthquakeMap;

    public DisasterDatabaseLoader(Context context, LoaderManager loaderManager){
        this.mContext = context;
        this.loaderManager = loaderManager;
    }

    public void LoadDisasterDatabase(){
        int CURSOR_DATA_LOADER_ID = 1;
        earthquakeMap = new HashMap<>();
        loaderManager.initLoader(CURSOR_DATA_LOADER_ID,
                null,
                this).forceLoad();
    }

    public Map<String, String> getDataMap(){
        return earthquakeMap;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        String[] projection = {EarthQuakeEntry._ID,
                EarthQuakeEntry.COLUMN_E_ID,
                EarthQuakeEntry.COLUMN_LOCATION};
        return new CursorLoader(mContext,
                EarthQuakeEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int e_idColumnIndex = data.getColumnIndex(EarthQuakeEntry.COLUMN_E_ID);
        int locationColumnIndex = data.getColumnIndex(EarthQuakeEntry.COLUMN_LOCATION);

        data.moveToFirst();
        String e_id;
        String location;

        do {
            e_id = data.getString(e_idColumnIndex);
            location = data.getString(locationColumnIndex);
            earthquakeMap.put(e_id, location);
            System.out.print("Id: " + e_id);
            System.out.println("Location: " + location);
        }while (data.moveToNext());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        earthquakeMap = null;
    }
}
