package com.example.apurba.disaster.disasterreport.database;

/*
 * Created by Apurba on 8/13/2018.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.widget.Toast;

import com.example.apurba.disaster.disasterreport.EarthQuakeItem;
import com.example.apurba.disaster.disasterreport.database.DisasterReportDbContract.EarthQuakeEntry;


import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisasterDatabaseLoader implements
        LoaderManager.LoaderCallbacks<Cursor>{


    private Context mContext;
    private LoaderManager loaderManager;
    private Map<String,String> earthquakeMap;
    private static  DisasterDatabaseLoader currentObject;


    private DisasterDatabaseLoader(Context context,
                                  LoaderManager loaderManager){
        this.mContext = context;
        this.loaderManager = loaderManager;
        // this constructor used in This class
    }


    public void LoadDisasterDatabase(){
        int CURSOR_DATA_LOADER_ID = 1;
        earthquakeMap = new HashMap<>();
        loaderManager.initLoader(CURSOR_DATA_LOADER_ID,
                null,
                this).forceLoad();
    }

    public static DisasterDatabaseLoader getLoadedObject(){
        return currentObject;
    }

    public static DisasterDatabaseLoader getObject(Context context,
                                                   LoaderManager loaderManager){
        currentObject = new DisasterDatabaseLoader(context, loaderManager);
        return currentObject;
    }

    void setMap( Map<String, String> newMap){
        earthquakeMap = newMap;
    }

    public void insertListIntoDatabase(List<EarthQuakeItem> earthquakesList){
        listInsertionTask task = new listInsertionTask(mContext, earthquakeMap, currentObject);
        task.execute(earthquakesList);
    }

    public Map<String, Integer> getEarthquakeLocationMap(){
        Map<String, Integer> locationMap = new HashMap<String, Integer>();
        try {
            if(!earthquakeMap.isEmpty()){
                for(String eid: earthquakeMap.keySet()){
                    String location = earthquakeMap.get(eid);
                    if(locationMap.containsKey(location)){
                        int count = locationMap.get(location);
                        locationMap.remove(location);
                        count++;
                        locationMap.put(location, count);
                    }else{
                        locationMap.put(location, 1);
                    }
                }
            }
        }catch (ConcurrentModificationException e){
            Toast.makeText(mContext, "Please Wait Loading... ", Toast.LENGTH_SHORT).show();
        }catch (NullPointerException e){
            // No Internet Connection state
        }

        return locationMap;
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

        if(data.getCount() != 0){
            data.moveToFirst();
            String e_id;
            String location;

            do {
                e_id = data.getString(e_idColumnIndex);
                location = data.getString(locationColumnIndex);
                earthquakeMap.put(e_id, location);
            }while (data.moveToNext());
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        earthquakeMap.clear();
    }
}







class listInsertionTask extends AsyncTask<List<EarthQuakeItem>, Void, Void>{
    private Map<String,String> mMap;
    private Context mContext;
    DisasterDatabaseLoader mLoader;

    public listInsertionTask(Context context,
                             Map<String,String> earthquakeMap,
                             DisasterDatabaseLoader loader){
        this.mMap = earthquakeMap;
        this.mContext = context;
        this.mLoader = loader;
    }

    @Override
    protected Void doInBackground(List<EarthQuakeItem>[] lists) {
        List<EarthQuakeItem> list = lists[0];
        try{
            checkList(list);
            EarthQuakeItem currentEarthQuake;
            String id;
            for(int i=0; i<list.size(); i++){
                currentEarthQuake = list.get(i);
                id = currentEarthQuake.getE_id();
                if(id != null && !mMap.containsKey(id)){
                    currentEarthQuake.splitLocation();
                    mMap.put(id, currentEarthQuake.getExactLocation());
                    ContentValues values = getContentValues(currentEarthQuake);
                    mContext.getContentResolver().insert(EarthQuakeEntry.CONTENT_URI, values);
                }
            }
        }catch (emptyListException e){
            Toast.makeText(mContext,
                    "Make sure You are connected With Internet!",
                    Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        mLoader.setMap(mMap);
        Toast.makeText(mContext,
                "All are saved",
                Toast.LENGTH_SHORT).show();
    }

    private ContentValues getContentValues(EarthQuakeItem earthQuakeItem){
        earthQuakeItem.splitLocation();

        ContentValues values = new ContentValues();
        values.put(EarthQuakeEntry.COLUMN_E_ID,
                earthQuakeItem.getE_id());
        values.put(EarthQuakeEntry.COLUMN_LOCATION,
                earthQuakeItem.getExactLocation());
        values.put(EarthQuakeEntry.COLUMN_GEO_LOCATION,
                earthQuakeItem.getLocation());
        values.put(EarthQuakeEntry.COLUMN_MAGNITUDE,
                String.valueOf(earthQuakeItem.getMagnitude()));
        values.put(EarthQuakeEntry.COLUMN_TIME ,
                String.valueOf(earthQuakeItem.getTimeInMilliseconds()));
        return values;
    }




    private void checkList(List<EarthQuakeItem> list) throws emptyListException {
        if(list.isEmpty()){
            throw new emptyListException("List is empty");
        }
    }
}


class emptyListException extends Exception{
    private String message;
    public emptyListException(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
}

