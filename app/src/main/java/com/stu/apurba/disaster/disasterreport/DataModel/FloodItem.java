package com.stu.apurba.disaster.disasterreport.DataModel;

/*
 * Created by Apurba on 3/14/2018.
 */

import java.text.DecimalFormat;

public class FloodItem {

    private int mSevertyLevel;
    private String mSeverity;
    private String mEaAreaName;
    private String mCounty;
    private String mRiverOrSea;
    private String mMessage;
    private String mTimeMessageChanged;
    private String mTimeRaised;

    // suitable constructor
    public FloodItem(int severtyLevel,
                     String severty,
                     String eaAreaName,
                     String county,
                     String riverOrSea,
                     String message,
                     String timeMessageChanged,
                     String timeRaised){

        mSevertyLevel = severtyLevel;
        mSeverity = severty;
        mEaAreaName = eaAreaName;
        mCounty = county;
        mRiverOrSea = riverOrSea;
        mMessage = message;
        mTimeMessageChanged = timeMessageChanged;
        mTimeRaised = timeRaised;
    }

    public String getSeverityLevel(){
        DecimalFormat decimalFormatter = new DecimalFormat("0");
        return decimalFormatter.format(mSevertyLevel);
    }
    public String getSeverity(){
        return mSeverity;
    }
    public String getEaAreaName(){
        return mEaAreaName;
    }
    public String getCounty(){
        return mCounty;
    }
    public String getRiverOrSea(){
        return mRiverOrSea;
    }
    public String getMessage(){
        return mMessage;
    }
    public String getTimeRaised(){
        return mTimeRaised;
    }
    public int getSevertyLevelInt(){
        return mSevertyLevel;
    }
}
