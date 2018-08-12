package com.example.apurba.disaster.disasterreport.database;

/*
 * Created by Apurba on 8/11/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;


import com.example.apurba.disaster.disasterreport.database.DisasterReportDbContract.EarthQuakeEntry;

public class DisasterReportDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = DisasterReportDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "DisasterReport.db";
    private static final int DATABASE_VERSION = 1; // database version

    Context context;
    public DisasterReportDbHelper(Context context){
        super(context, DATABASE_NAME,null ,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        /**
         * String SQL_CREATE_INVENTORY_TABLE = "CREATE TABLE " + InventoryEntry.TABLE_NAME + " ( " +
         InventoryEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
         InventoryEntry.COLUMN_INVENTORY_NAME + " TEXT NOT NULL, " +
         InventoryEntry.COLUMN_STATUS + " INTEGER NOT NULL DEFAULT 2, " +
         InventoryEntry.COLUMN_INVENTORY_URL + " TEXT, " +
         InventoryEntry.COLUMN_INVENTORY_DESCRIPTION + " TEXT, " +
         InventoryEntry.COLUMN_INVENTORY_PLATFORM + " TEXT NOT NULL, " +
         InventoryEntry.COLUMN_INVENTORY_TYPE + " TEXT);";
         Log.v(LOG_TAG,SQL_CREATE_INVENTORY_TABLE);
         //String SQL_DROP_TABLE = "DROP TABLE " + InventoryEntry.TABLE_NAME + ";";
         db.execSQL(SQL_CREATE_INVENTORY_TABLE);
         */


        String SQL_CREATE_EARTHQUAKE_TABLE = "CREATE TABLE " + EarthQuakeEntry.TABLE_NAME + " ( " +
                EarthQuakeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EarthQuakeEntry.COLUMN_E_ID + " TEXT, " +
                EarthQuakeEntry.COLUMN_LOCATION + " TEXT NOT NULL, " +
                EarthQuakeEntry.COLUMN_GEO_LOCATION + " TEXT NOT NULL, " +
                EarthQuakeEntry.COLUMN_MAGNITUDE + " TEXT NOT NULL, " +
                EarthQuakeEntry.COLUMN_TIME + " TEXT NOT NULL); " ;
        Log.v(LOG_TAG,SQL_CREATE_EARTHQUAKE_TABLE);
        //String SQL_DROP_TABLE = "DROP TABLE " + EarthQuakeEntry.TABLE_NAME + ";";
        Toast.makeText(context , "DisasterReportDbHelper called", Toast.LENGTH_SHORT).show();
        db.execSQL(SQL_CREATE_EARTHQUAKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //
    }

}
