package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/13/2018.
 *
 * EarthquakeLoader:
 * loads the earthquake items in background thread
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

    String url;

    // suitable constructor
    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<EarthQuakeItem> loadInBackground() {
        if (url.length() < 1) {
            return null;
        }
        // get jasonResponse
        String jasonResponse = QueryUtils.requestToApi(url);
        // make a list from jason data and return it
        return extractFeatureFromJson(jasonResponse);
    }
    /**
     * Return a list of {@link EarthQuakeItem} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<EarthQuakeItem> extractFeatureFromJson(String earthquakeJSON) {
        if (TextUtils.isEmpty(earthquakeJSON)) {
            // if response is empty then return null
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
            Log.e("EarthqakeLoader", "Problem parsing the earthquake JSON results", e);
        }
        return earthquakes;
    }

}
