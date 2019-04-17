package com.example.apurba.disaster.disasterreport.DataModel;

/*
 * Created by Apurba on 8/14/2018.
 */

public class StatisticsLocation {

    private String mLocation;
    private int mOccurance;

    public StatisticsLocation(String location, int occurance){
        this.mLocation = location;
        this.mOccurance = occurance;
    }

    public String getLocation(){
        return mLocation;
    }
    public int getOccurance(){
        return mOccurance;
    }
}
