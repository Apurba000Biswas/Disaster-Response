package com.example.apurba.disaster.disasterreport.Loader;

/** public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuakeItem>> class
 *
 * Created by Apurba on 3/13/2018.
 * A subclass of {@link android.support.v4.content.AsyncTaskLoader}
 * This class loads StatisticsLocation data in the background thread
 */

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import com.example.apurba.disaster.disasterreport.DataModel.EarthQuakeItem;
import com.example.apurba.disaster.disasterreport.Network.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EarthquakeLoader extends AsyncTaskLoader<List<EarthQuakeItem>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getSimpleName();
    private String url;
    private static Map<String, List<EarthQuakeItem>> earthquakeMap;
    private static List<EarthQuakeItem> earthQuakeList;

    // suitable constructor
    public EarthquakeLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    /** protected void onStartLoading() method
     *  called from main thread
     */
    @Override
    protected void onStartLoading() {
        earthQuakeList = new ArrayList<>();
        forceLoad(); //Force an asynchronous load - called from main thread
    }

    /** public List<EarthQuakeItem> loadInBackground() method
     *  called from background thread
     *  This method actually dose the loading earthquake data operation from the server
     * @return -
     */
    @Override
    public List<EarthQuakeItem> loadInBackground() {
        // check for valid url length
        if (url.length() < 1) {
            return null;
        }

        String jasonResponse = QueryUtils.requestToApi(url);
        return extractFeatureFromJson(jasonResponse);
    }

    /** private static List<EarthQuakeItem> extractFeatureFromJson() method
     * Return a list of {@link EarthQuakeItem} objects that has been built up from
     * parsing a JSON response.
     */
    private static List<EarthQuakeItem> extractFeatureFromJson(String earthquakeJSON) {
        if (TextUtils.isEmpty(earthquakeJSON)) {
            return null;
        }

        List<EarthQuakeItem> earthquakes = new ArrayList<EarthQuakeItem>();
        earthquakeMap = new HashMap<>();
        try {
            JSONObject jsonObj = new JSONObject(earthquakeJSON);
            JSONArray features = jsonObj.getJSONArray("features");
            for (int i = 0; i < features.length(); i++){
                JSONObject earthquake = features.getJSONObject(i);
                JSONObject properties = earthquake.getJSONObject("properties");
                double magnitude = properties.getDouble("mag");
                String place = properties.getString("place");
                long time = properties.getLong("time");
                String url = properties.getString("url");
                String id = earthquake.getString("id");

                EarthQuakeItem earthQuakeItem = new EarthQuakeItem(id,magnitude, place, time, url);

                earthquakes.add(earthQuakeItem);
                putInEarthquakeMap(earthQuakeItem);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        earthQuakeList = earthquakes;
        return earthquakes;
    }

    private static void putInEarthquakeMap(EarthQuakeItem item){
        item.splitLocation();
        String exactLocation = item.getExactLocation();
        String exactLocationAllCaps = exactLocation.toUpperCase().trim();
        if (earthquakeMap.containsKey(exactLocationAllCaps)){
            earthquakeMap.get(exactLocationAllCaps).add(item);
        }else{
            List<EarthQuakeItem> list = new ArrayList<>();
            list.add(item);
            earthquakeMap.put(exactLocationAllCaps, list);
        }
    }

    public Map<String, List<EarthQuakeItem>> getEarthquakeMap(){
        return earthquakeMap;
    }

    public List<EarthQuakeItem> getEarthQuakeList(){
        return earthQuakeList;
    }
}
