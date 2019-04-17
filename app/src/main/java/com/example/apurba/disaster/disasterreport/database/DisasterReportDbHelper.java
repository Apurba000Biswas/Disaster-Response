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


        String SQL_CREATE_EARTHQUAKE_TABLE = "CREATE TABLE " + EarthQuakeEntry.TABLE_NAME + " ( " +
                EarthQuakeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EarthQuakeEntry.COLUMN_E_ID + " TEXT, " +
                EarthQuakeEntry.COLUMN_LOCATION + " TEXT NOT NULL, " +
                EarthQuakeEntry.COLUMN_GEO_LOCATION + " TEXT NOT NULL, " +
                EarthQuakeEntry.COLUMN_MAGNITUDE + " TEXT NOT NULL, " +
                EarthQuakeEntry.COLUMN_TIME + " TEXT NOT NULL, " +
                EarthQuakeEntry.COLUMN_URL + " TEXT ); " ;
        Log.v(LOG_TAG,SQL_CREATE_EARTHQUAKE_TABLE);
        db.execSQL(SQL_CREATE_EARTHQUAKE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //
    }

}
