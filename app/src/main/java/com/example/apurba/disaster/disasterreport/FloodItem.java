package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/14/2018.
 */

import java.text.DecimalFormat;

public class FloodItem {

    private int mSevertyLevel;
    private String mSeverity;
    private String mEaAreaName;

    public FloodItem(int severtyLevel, String severty, String eaAreaName){
        mSevertyLevel = severtyLevel;
        mSeverity = severty;
        mEaAreaName = eaAreaName;
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
}
