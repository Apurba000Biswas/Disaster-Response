package com.example.apurba.disaster.disasterreport.database;

/*
 * Created by Apurba on 8/12/2018.
 */

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.example.apurba.disaster.disasterreport.database.DisasterReportDbContract.EarthQuakeEntry;

public class DisasterProvider extends ContentProvider{

    public static final String LOG_TAG = DisasterProvider.class.getSimpleName();
    private static final int EARTHQUAKE = 100; // uri matcher code for earthquake table
    private static final int EARTHQUAKE_ID = 101; //uri matcher code for single earthquake row

    private static final UriMatcher sUrimatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        sUrimatcher.addURI(DisasterReportDbContract.CONTENT_AUTHORITY,
                DisasterReportDbContract.PATH_EARTHQUAKE, EARTHQUAKE);
        sUrimatcher.addURI(DisasterReportDbContract.CONTENT_AUTHORITY,
                DisasterReportDbContract.PATH_EARTHQUAKE + "/#",
                EARTHQUAKE_ID);
    }

    private DisasterReportDbHelper mDbHelper;

    @Override
    public boolean onCreate() {
        mDbHelper = new DisasterReportDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri,
                        String[] projection,
                        String selection,
                        String[] selectionArgs,
                        String sortOrder) {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor cursor = null;
        int match = sUrimatcher.match(uri);

        switch (match){
            case EARTHQUAKE:
                cursor = db.query(EarthQuakeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case EARTHQUAKE_ID:
                selection = EarthQuakeEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(EarthQuakeEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = sUrimatcher.match(uri);
        switch (match){
            case EARTHQUAKE:
                return EarthQuakeEntry.CONTENT_LIST_TYPE;
            case EARTHQUAKE_ID:
                return EarthQuakeEntry.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown uri " + uri + " with match " + match);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {

        final int match = sUrimatcher.match(uri);
        switch (match){
            case EARTHQUAKE:
                return insertEarthquake(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }

    }

    private Uri insertEarthquake(Uri uri, ContentValues values){

        checkForValidData(values);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id = db.insert(EarthQuakeEntry.TABLE_NAME, null, values);
        if (id == -1){
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, id);
    }

    private void checkForValidData(ContentValues values){

        String eid = values.getAsString(EarthQuakeEntry.COLUMN_E_ID);
        if(TextUtils.isEmpty(eid)){
            throw new IllegalArgumentException("Earthquake requires a ID");
        }
        String location = values.getAsString(EarthQuakeEntry.COLUMN_LOCATION);
        if(TextUtils.isEmpty(location)){
            throw new IllegalArgumentException("Earthquake requires a location");
        }
        String geoLocation = values.getAsString(EarthQuakeEntry.COLUMN_GEO_LOCATION);
        if(TextUtils.isEmpty(geoLocation)){
            throw new IllegalArgumentException("Earthquake requires a Geographical location");
        }
        String magnitude = values.getAsString(EarthQuakeEntry.COLUMN_MAGNITUDE);
        if(TextUtils.isEmpty(magnitude)){
            throw new IllegalArgumentException("Earthquake requires a Magnitude");
        }
        String time = values.getAsString(EarthQuakeEntry.COLUMN_TIME);
        if(TextUtils.isEmpty(time)){
            throw new IllegalArgumentException("Earthquake requires a time");
        }
    }

    @Override
    public int delete(@NonNull Uri uri,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        final int match = sUrimatcher.match(uri);
        int rowsDeleted;
        switch (match){
            case EARTHQUAKE:
                rowsDeleted = db.delete(EarthQuakeEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            case EARTHQUAKE_ID:
                selection = EarthQuakeEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = db.delete(EarthQuakeEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
        if(rowsDeleted != 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri,
                      @Nullable ContentValues contentValues,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {

        final int match = sUrimatcher.match(uri);
        switch (match){
            case EARTHQUAKE:
                return updateEarthquake(uri,
                        contentValues,
                        selection,
                        selectionArgs);
            case EARTHQUAKE_ID:
                selection = EarthQuakeEntry._ID + "=?";
                selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                return updateEarthquake(uri,
                        contentValues,
                        selection,
                        selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }

    private int updateEarthquake(Uri uri,
                                 ContentValues values,
                                 String selection,
                                 String[] selectionArgs){

        checkForValidData(values);

        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        int rowsUpdated = db.update(EarthQuakeEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);
        if (rowsUpdated != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rowsUpdated;
    }
}
