package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/13/2018.
 */

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuakeItem>> {

    public static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    String url;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "TEST: called onStartLoading() ");
        forceLoad();
    }

    @Override
    public List<EarthQuakeItem> loadInBackground() {
        Log.i(LOG_TAG, "TEST: called loadInBackground() ");
        if (url.length() < 1) {
            return null;
        }
        List<EarthQuakeItem> earthquakeData = EqQueryUtils.fetchEarthquakeData(url);
        return earthquakeData;
    }

}
