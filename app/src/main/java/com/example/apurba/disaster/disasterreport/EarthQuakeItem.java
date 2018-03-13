package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/13/2018.
 */

public class EarthQuakeItem {

    private static final String LOCATION_SEPARATOR = " of ";

    private double mMagnitude;
    private String mLocation;
    private String locationOffset;
    private String primaryLocation;
    private String mUrl;
    private long mTimeInMilliseconds;

    public EarthQuakeItem(double magnitude, String location, long timeInMillisecond, String url){
        this.mMagnitude = magnitude;
        this.mTimeInMilliseconds = timeInMillisecond;
        this.mLocation = location;
        this.mUrl = url;
    }
    /*------------------------------------------------------------------Methods---------------------------------------------------------------------**/
    public double getMagnitude(){
        return mMagnitude;
    }
    public long getTimeInMilliseconds(){
        return mTimeInMilliseconds;
    }
    public String getLocationOffset(){
        return locationOffset;
    }
    public String getPrimaryLocation(){
        return primaryLocation;
    }
    public String getUrl(){
        return mUrl;
    }
    public String getLocation(){
        return mLocation;
    }
    /**
     * Split the location String into two string
     *
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
