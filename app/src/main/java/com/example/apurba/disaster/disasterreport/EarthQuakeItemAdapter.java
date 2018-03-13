package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/13/2018.
 */

import android.app.Activity;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class EarthQuakeItemAdapter extends ArrayAdapter<EarthQuakeItem>{

    //Constructor
    public EarthQuakeItemAdapter(Activity context, List<EarthQuakeItem> quakeItems){
        super(context, 0, quakeItems);
    }

    /*------------------------------------------------------------------Methods---------------------------------------------------------------------**/
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listQuakeItemView = convertView;
        if(listQuakeItemView == null) {
            listQuakeItemView = LayoutInflater.from(getContext()).inflate(R.layout.earth_quake_item_list, parent, false);
        }
        EarthQuakeItem currentEarthquake = getItem(position);//get the current item to create view
        setAllViews(currentEarthquake, listQuakeItemView);
        return listQuakeItemView;
    }

    /**
     * Sets all views in the current position of the list view with
     * formated views
     *
     */
    private void setAllViews(EarthQuakeItem currentEarthquake, View listQuakeItemView){

        //find and Set the magnitude
        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        TextView magnitudeView = listQuakeItemView.findViewById(R.id.magnitude_text_field);
        GradientDrawable magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getMagnitude());

        // Set the color on the magnitude circle
        magnitudeCircle.setColor(magnitudeColor);
        setATextView(magnitudeView, formatedMagnitude(currentEarthquake.getMagnitude()));

        //find and set the location offset
        currentEarthquake.splitLocation();
        setATextView((TextView) listQuakeItemView.findViewById(R.id.location_offset), currentEarthquake.getLocationOffset());

        //find and set the primary location
        setATextView((TextView) listQuakeItemView.findViewById(R.id.primary_location), currentEarthquake.getPrimaryLocation());

        // create human readable formate for date and then display it
        Date dateObject = new Date(currentEarthquake.getTimeInMilliseconds());
        setATextView((TextView) listQuakeItemView.findViewById(R.id.date_text_field), formatDate(dateObject));

        //create human readable formate for time and then display it
        setATextView((TextView) listQuakeItemView.findViewById(R.id.time), formatTime(dateObject));
    }
    /**
     * Set the Specified Text view with a given text
     */
    private void setATextView(TextView textView, String text){
        textView.setText(text);
    }

    /**
     * Returns the approriate color for Magnitude text field
     */
    private int getMagnitudeColor(double magnitude){
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
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

    /**
     * Return the formatted magnitude string showing 1 decimal place (i.e. "3.2")
     * from a decimal magnitude value.
     */
    private String formatedMagnitude(double mag){
        DecimalFormat decimalFormatter = new DecimalFormat("0.0");
        return decimalFormatter.format(mag);
    }
    /**
     * Return the formatted date string (i.e. "Mar 3, 1984") from a Date object.
     */
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }
    /**
     * Return the formatted date string (i.e. "4:30 PM") from a Date object.
     */
    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }
}
