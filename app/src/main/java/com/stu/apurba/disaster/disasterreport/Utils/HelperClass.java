package com.stu.apurba.disaster.disasterreport.Utils;

/* HelperClass class

 * Created by Apurba on 4/26/2018.
 * Its created for code optimization purpose
 * This class offers support for retrieving appropriate color for magnitude and severity
 * It also provides method to check internet connection and format magnitude
 */

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.content.ContextCompat;

import com.stu.apurba.disaster.disasterreport.R;

import java.text.DecimalFormat;

public class HelperClass {

    private Activity mContext;

    // constructor - takes an Activity
    public HelperClass(Activity context){
        mContext = context;
    }

    /** public final int getMagnitudeColor() method
     *  Returns the appropriate color for given Magnitude
     */
    public final int getMagnitudeColor(double magnitude){
        int magnitudeColorResourceId;
        int magnitudeFloor = (int) Math.floor(magnitude);
        switch (magnitudeFloor){
            case 0 :
            case 1 :
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2 :
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3 :
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4 :
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5 :
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6 :
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7 :
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8 :
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9 :
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            default:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;
        }
        return ContextCompat.getColor(mContext, magnitudeColorResourceId);
    }

    /** public int getSeverityCircleColor() method
     *  Returns the appropriate color for given Severity
     */
    public int getSeverityCircleColor(int magnitude){
        int colorResourceId;
        switch (magnitude){
            case 0 :
            case 1 :
                colorResourceId = R.color.severity1;
                break;
            case 2 :
                colorResourceId = R.color.severity2;
                break;
            case 3 :
                colorResourceId = R.color.severity3;
                break;
            case 4 :
                colorResourceId = R.color.severity4;
                break;
            default:
                colorResourceId = R.color.wrongcolor;
                break;
        }
        return ContextCompat.getColor(mContext, colorResourceId);
    }

    /** public boolean isConnectedToInternet() method
     *  Check for internet connection
     *  @return - if connected returns true and vice versa
     */
    public boolean isConnectedToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    /** public String formatedMagnitude() method
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    public String formatedMagnitude(double mag){
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        return decimalFormatter.format(mag);
    }
}
