package com.example.apurba.disaster.disasterreport;

/** public class EarthQuakeItem class:
 *
 *  Created by Apurba on 3/13/2018.
 *  This class represents a standard earth quake event and all
 *  of its behaviour
 */

public class EarthQuakeItem {

    private static final String LOCATION_SEPARATOR = " of ";

    private double mMagnitude;
    private String mLocation;
    private String locationOffset;
    private String primaryLocation;
    private String mUrl;
    private String e_id;
    private long mTimeInMilliseconds;

    public EarthQuakeItem(String id,
                          double magnitude,
                          String location,
                          long timeInMillisecond,
                          String url){
        this.e_id = id;
        this.mMagnitude = magnitude;
        this.mTimeInMilliseconds = timeInMillisecond;
        this.mLocation = location;
        this.mUrl = url;
    }


    // returns current items magnitude
    public double getMagnitude(){
        return mMagnitude;
    }
    // returns current items time(milliseconds)
    public long getTimeInMilliseconds(){
        return mTimeInMilliseconds;
    }
    //returns current items location offset
    public String getLocationOffset(){
        return locationOffset;
    }
    // returns current items primary location
    public String getPrimaryLocation(){
        return primaryLocation;
    }
    // returns current items url
    public String getUrl(){
        return mUrl;
    }
    // returns current items location
    public String getLocation(){
        return mLocation;
    }

    public String getE_id(){
        return e_id;
    }

    /** public void splitLocation() method
     *  Split the location String into two strings
     *  and save them as locationOffset & primaryLocation
     */
    public void splitLocation(){
        if(mLocation.contains(LOCATION_SEPARATOR)) {
            String[] locationParts = mLocation.split("of");
            locationOffset = locationParts[0] + LOCATION_SEPARATOR;
            primaryLocation = locationParts[1];
        }else {
            locationOffset = "Near the";
            primaryLocation = mLocation;
        }
    }
}
