package com.example.apurba.disaster.disasterreport.database;

/*
 * Created by Apurba on 8/12/2018.
 */

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class DisasterReportDbContract {

    private DisasterReportDbContract(){
    }

    public static final String CONTENT_AUTHORITY = "com.example.apurba.disaster.disasterreport";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" +CONTENT_AUTHORITY);
    public static final String PATH_EARTHQUAKE = "earthquake";


    public static final class EarthQuakeEntry implements BaseColumns{
        public static final String TABLE_NAME = "earthquake";
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_EARTHQUAKE);

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_E_ID = "e_id";
        public static final String COLUMN_LOCATION = "location";
        public static final String COLUMN_GEO_LOCATION = "geo_location";
        public static final String COLUMN_MAGNITUDE = "magnitude";
        public static final String COLUMN_TIME = "time";

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" +
                        CONTENT_AUTHORITY + "/" +
                        PATH_EARTHQUAKE;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.ANY_CURSOR_ITEM_TYPE + "/" +
                        CONTENT_AUTHORITY + "/" +
                        PATH_EARTHQUAKE;
    }
}
