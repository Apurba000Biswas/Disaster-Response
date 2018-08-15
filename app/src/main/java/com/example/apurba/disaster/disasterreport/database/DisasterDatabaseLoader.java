package com.example.apurba.disaster.disasterreport.database;

/** DisasterDatabaseLoader class:
 *
 * Created by Apurba on 8/13/2018.
 * Implements {@link android.support.v4.app.LoaderManager.LoaderCallbacks}
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
    private listInsertionTask task;

    /**
     *  This private constructor used by the factory method
     * @param context - indicate the activity its called from
     * @param loaderManager - used to load data in the background thread
     */
    private DisasterDatabaseLoader(Context context,
                                  LoaderManager loaderManager){
        this.mContext = context;
        this.loaderManager = loaderManager;
    }

    /** public void LoadDisasterDatabase() method
     *  Initialize the loader and also
     *  create a new HashMap to save loaded data
     */
    public void LoadDisasterDatabase(){
        int CURSOR_DATA_LOADER_ID = 1;
        earthquakeMap = new HashMap<>();
        loaderManager.initLoader(CURSOR_DATA_LOADER_ID,
                null,
                this).forceLoad();
    }


    /** public static DisasterDatabaseLoader getObject() method
     *  Its a factory method which serve as object
     *  creator for this class
     * @param context - indicate the activity
     * @param loaderManager - used to manage this loader
     * @return - a brand new {@link DisasterDatabaseLoader} object.
     */
    public static DisasterDatabaseLoader getObject(Context context,
                                                   LoaderManager loaderManager){
        currentObject = new DisasterDatabaseLoader(context, loaderManager);
        return currentObject;
    }

    /** void setMap() method
     *  This package private method used to set the current hasMap
     *  with a new one(Updated)
     * @param newMap - Updated Map
     */
    void setMap( Map<String, String> newMap){
        earthquakeMap.clear();
        earthquakeMap.putAll(newMap);
        Toast.makeText(mContext, "All are saved", Toast.LENGTH_SHORT).show();
    }

    /** public void insertListIntoDatabase() method
     *  Creates a new background task which used to insert a list
     *  in the database
     * @param earthquakesList - this list gets inserted
     */
    public void insertListIntoDatabase(List<EarthQuakeItem> earthquakesList){
        task = new listInsertionTask(mContext, earthquakeMap, currentObject);
        task.execute(earthquakesList);
    }

    public void reloadData(){
        loaderManager.restartLoader(1, null, this);
    }

    /** public int deleteAllData()
     *  deletes all data from database
     * @return - how many rows were deleted
     */
    public int deleteAllData(){
        int rowsDeleted = 0;
        rowsDeleted = mContext.getContentResolver().delete(
                EarthQuakeEntry.CONTENT_URI,
                null,
                null);
        if(rowsDeleted != 0){
            earthquakeMap.clear();
        }
        return rowsDeleted;
    }

    /** public Map<String, Integer> getEarthquakeLocationMap() method
     *  Counts how many earthquake happened in location then put the
     *  counted value in a new HasMap along with that location where
     *  location serve as key and counted value serve as value for that
     *  key
     * @return - a new HasMap of Location-count pair
     */
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

    /** public Loader<Cursor> onCreateLoader() method
     *  Gets called from main thread
     *  This method initialize the database query for
     *  selecting data
     * @param id - loader id(not used)
     * @param args - (not used)
     * @return - a new {@link CursorLoader} object
     */
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

    /** public void onLoadFinished() method
     *  Called from main thread when background thread finishes
     *  its execution. This method load resultant Cursor data
     *  into hashMap
     * @param loader - used to load data in a Cursor
     * @param data - loaded Cursor data used make the HasMap
     */
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

    /** public void onLoaderReset() method
     *  when loader resets this method gets called
     *  It clear all previuosly loaded data from HashMap
     * @param loader
     */
    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        earthquakeMap.clear();
    }
}


/** listInsertionTask class (package private):
 *
 *  A {@link AsyncTask} subclass
 *  creates a new background task which used to insert a data list into
 *  the database in the background thread, after inserting it update the
 *  passed HashMap by calling its caller's setMap() method
 */
class listInsertionTask extends AsyncTask<List<EarthQuakeItem>, Void, Map<String,String>>{
    private Map<String,String> mMap;
    private Context mContext;
    DisasterDatabaseLoader mLoader;


    /** Public Constructor
     * @param context - indicate the activity
     * @param earthquakeMap - used to compare data is already exits in the database or not
     * @param loader - used to set updated Map in the old Map
     */
    public listInsertionTask(Context context,
                             Map<String,String> earthquakeMap,
                             DisasterDatabaseLoader loader){
        this.mMap = earthquakeMap;
        this.mContext = context;
        this.mLoader = loader;

    }


    /** protected Void doInBackground() method
     *  Gets called from background thread
     *  This method inerts all data from the list by checking
     *  its already exits in the database or not. If exits then
     *  ignore the data , if not then put it in the HashMap and
     *  then insert into the database
     * @param lists
     * @return
     */
    @Override
    protected Map<String,String> doInBackground(List<EarthQuakeItem>[] lists) {
        List<EarthQuakeItem> list = lists[0];
        Map<String,String> updatedMap = new HashMap<>();
        try{
            checkList(list);
            EarthQuakeItem currentEarthQuake;
            String id;

            for(int i=0; i<list.size(); i++){
                currentEarthQuake = list.get(i);
                id = currentEarthQuake.getE_id();
                if(id != null && !mMap.containsKey(id)){
                    currentEarthQuake.splitLocation();
                    updatedMap.put(id, currentEarthQuake.getExactLocation());
                    ContentValues values = getContentValues(currentEarthQuake);
                    mContext.getContentResolver().insert(EarthQuakeEntry.CONTENT_URI, values);
                }
            }
            updatedMap.putAll(mMap);
        }catch (emptyListException e){
            Toast.makeText(mContext,
                    "Make sure You are connected With Internet!",
                    Toast.LENGTH_SHORT).show();
        }
        return updatedMap;
    }

    /** onPostExecute() method
     *  called from main thread after background thread returns result
     *  set the updated HasMap in the old one
     * @param updatedMap - no result get passed here
     */
    @Override
    protected void onPostExecute(Map<String,String> updatedMap) {
        mLoader.setMap(updatedMap);
    }

    /** private ContentValues getContentValues() method
     *  Make ContentValues to insert the value in the database
     * @param earthQuakeItem - this object gets convert into ContentValue
     * @return - a new ContentValues
     */
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


    /** private void checkList() method
     *  Checks for list is empty or not, if empty throws a
     *  new {@link emptyListException} exception
     * @param list - checks this list is empty or not
     * @throws emptyListException - a custom exception
     */
    private void checkList(List<EarthQuakeItem> list)
            throws emptyListException {
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
