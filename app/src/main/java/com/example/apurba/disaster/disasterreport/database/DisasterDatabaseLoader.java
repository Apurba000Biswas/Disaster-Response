package com.example.apurba.disaster.disasterreport.database;

/** DisasterDatabaseLoader class:
 *
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
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.apurba.disaster.disasterreport.EarthQuakeItem;
import com.example.apurba.disaster.disasterreport.StatisticsLocationAdapater;
import com.example.apurba.disaster.disasterreport.database.DisasterReportDbContract.EarthQuakeEntry;

import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DisasterDatabaseLoader {

    private Context mContext;


    private DisasterDatabaseLoader(Context context){
        this.mContext = context;
    }


    /** public static DisasterDatabaseLoader getObject() method
     *  Its a factory method which serve as object
     *  creator for this class
     * @param context - indicate the activity
     * @return - a brand new {@link DisasterDatabaseLoader} object.
     */
    public static DisasterDatabaseLoader getObject(Context context){
        return new DisasterDatabaseLoader(context);
    }


    /** public void insertListIntoDatabase() method
     *  Creates a new background task which used to insert a list
     *  in the database
     * @param earthquakesList - this list gets inserted
     */
    public void insertListIntoDatabase(List<EarthQuakeItem> earthquakesList){
        listInsertionTask task = new listInsertionTask(mContext);
        task.execute(earthquakesList);
    }

}


class listInsertionTask extends AsyncTask<List<EarthQuakeItem>, Void, Void>{

    private Context mContext;

    public listInsertionTask(Context context){
        mContext = context;
    }

    @Override
    protected Void doInBackground(List<EarthQuakeItem>[] lists) {
        List<EarthQuakeItem> list = lists[0];
        try {
            checkList(list);
            String[] projection = {EarthQuakeEntry.COLUMN_E_ID,
                    EarthQuakeEntry.COLUMN_LOCATION};

            Cursor data = mContext.getContentResolver().query(
                    EarthQuakeEntry.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);
            insertList(list, getEarthquakeMapFromCursor(data));

        }catch (emptyListException e){
            Toast.makeText(mContext,
                    "Make sure You are connected With Internet!",
                    Toast.LENGTH_SHORT).show();
        }
        return null;
    }

    private void insertList(List<EarthQuakeItem> list,
                            Map<String, String> earthquakeMap){
        EarthQuakeItem currentEarthQuake;
        String id;
        for(int i=0; i<list.size(); i++){
            currentEarthQuake = list.get(i);
            id = currentEarthQuake.getE_id();
            if(id != null && !earthquakeMap.containsKey(id)){
                currentEarthQuake.splitLocation();
                ContentValues values = getContentValues(currentEarthQuake);
                mContext.getContentResolver().insert(EarthQuakeEntry.CONTENT_URI, values);
            }
        }
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
        values.put(EarthQuakeEntry.COLUMN_URL, earthQuakeItem.getUrl());
        return values;
    }



    private Map<String, String> getEarthquakeMapFromCursor(Cursor data){
        HashMap<String, String> dataMap = new HashMap<>();

        int e_idColumnIndex = data.getColumnIndex(EarthQuakeEntry.COLUMN_E_ID);
        int locationColumnIndex = data.getColumnIndex(EarthQuakeEntry.COLUMN_LOCATION);

        if(data.getCount() != 0){
            data.moveToFirst();
            String e_id;
            String location;

            do {
                e_id = data.getString(e_idColumnIndex);
                location = data.getString(locationColumnIndex);
                dataMap.put(e_id, location);
            }while (data.moveToNext());
        }

        return dataMap;
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


    @Override
    protected void onPostExecute(Void aVoid) {
        Toast.makeText(mContext, "All are saved", Toast.LENGTH_SHORT).show();
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
