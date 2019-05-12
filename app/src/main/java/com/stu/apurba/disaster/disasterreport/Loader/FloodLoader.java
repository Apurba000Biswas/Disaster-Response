package com.stu.apurba.disaster.disasterreport.Loader;

/*
 * Created by Apurba on 3/15/2018.
 *
 * FloodLoader:
 * loads flood item in background thread
 */

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.text.TextUtils;
import android.util.Log;

import com.stu.apurba.disaster.disasterreport.DataModel.FloodItem;
import com.stu.apurba.disaster.disasterreport.Network.QueryUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FloodLoader extends AsyncTaskLoader<List<FloodItem>>{

    public static final String LOG_TAG = FloodLoader.class.getSimpleName();
    String url;

    // suitable constructor
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
        if (url.length() < 1) {
            return null;
        }
        String jasonResponse = QueryUtils.requestToApi(url);
        return extractItemsFromJSON(jasonResponse);
    }

    /**
     * Extract data from the Jason response after receving tha data
     */
    private static List<FloodItem> extractItemsFromJSON(String floodJASON){
        if (TextUtils.isEmpty(floodJASON)) {
            return null;
        }
        List<FloodItem> floods = new ArrayList<FloodItem>();
        try {
            JSONObject jsonObj = new JSONObject(floodJASON);
            JSONArray items = jsonObj.getJSONArray("items");

            for (int i = 0; i < items.length(); i++){
                JSONObject flood = items.getJSONObject(i);
                String eaAreaName = flood.getString("eaAreaName");

                JSONObject floodArea = flood.getJSONObject("floodArea");
                String county = floodArea.getString("county");
                String riverOrSea = floodArea.getString("riverOrSea");

                String message;
                try{
                    message = flood.getString("message");
                }catch (JSONException e){
                    message = "No Message provided";
                }

                String severity = flood.getString("severity");
                int severityLevel = flood.getInt("severityLevel");
                String timeMessageChanged = flood.getString("timeMessageChanged");
                String timeRaised = flood.getString("timeRaised");

                floods.add(new FloodItem(severityLevel,
                        severity,
                        eaAreaName,
                        county,
                        riverOrSea,
                        message,
                        timeMessageChanged,
                        timeRaised));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the Flood JSON results", e);
        }
        return floods;
    }
}
