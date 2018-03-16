package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/13/2018.
 */

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
        String jasonResponse = QueryUtils.requestToApi(url);
        List<EarthQuakeItem> earthquakeData = extractFeatureFromJson(jasonResponse);
        return earthquakeData;
    }
    /**
     * Return a list of {@link EarthQuakeItem} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<EarthQuakeItem> extractFeatureFromJson(String earthquakeJSON) {
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }
        List<EarthQuakeItem> earthquakes = new ArrayList<>();
        try {
            // build up a list of Earthquake objects with the corresponding data.
            JSONObject jsonObj = new JSONObject(earthquakeJSON);
            JSONArray features = jsonObj.getJSONArray("features");
            // looping through All features
            for (int i = 0; i < features.length(); i++){
                JSONObject earthquake = features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                earthquakes.add(new EarthQuakeItem(magnitude, place, time, url));
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

}
