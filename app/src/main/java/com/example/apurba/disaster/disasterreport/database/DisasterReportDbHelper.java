package com.example.apurba.disaster.disasterreport.database;

/*
 * Created by Apurba on 8/11/2018.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import com.example.apurba.disaster.disasterreport.database.DisasterReportDbContract.EarthQuakeEntry;

public class DisasterReportDbHelper extends SQLiteOpenHelper{

    private static final String LOG_TAG = DisasterReportDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "DisasterReport.db";
    private static final int DATABASE_VERSION = 1; // database version

    private Context context;

    public DisasterReportDbHelper(Context context){
        //super(context,DATABASE_NAME,null,DATABASE_VERSION);
        super(context, DATABASE_NAME,null ,DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        String SQL_CREATE_EARTHQUAKE_TABLE = "CREATE TABLE " + EarthQuakeEntry.TABLE_NAME + " ( " +
                EarthQuakeEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                EarthQuakeEntry.COLUMN_E_ID + "TEXT PRIMARY KEY, " +
                EarthQuakeEntry.COLUMN_LOCATION + "TEXT NOT NULL, " +
                EarthQuakeEntry.COLUMN_GEO_LOCATION + "TEXT NOT NULL, " +
                EarthQuakeEntry.COLUMN_MAGNITUDE + "TEXT NOT NULL, " +
                EarthQuakeEntry.COLUMN_TIME + "TEXT NOT NULL);";
        try{
            sqLiteDatabase.execSQL(SQL_CREATE_EARTHQUAKE_TABLE);
        }catch (Exception e){
            Toast.makeText(context, "Error - SQL-Create-Table-Query: " +
                    e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //
    }

}
