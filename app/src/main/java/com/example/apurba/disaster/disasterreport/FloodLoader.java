package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/15/2018.
 */

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import java.util.List;

public class FloodLoader extends AsyncTaskLoader<List<FloodItem>>{

    public static final String LOG_TAG = FloodLoader.class.getSimpleName();
    String url;

    public FloodLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<FloodItem> loadInBackground() {
        List<FloodItem> floods = FloodQueryUtils.fetchFloodData(url);
        return floods;
    }
}
