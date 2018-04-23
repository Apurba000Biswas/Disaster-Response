package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 4/23/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.apurba.disaster.disasterreport.EarthQuakeFragment.EXTRA_MESSAGE_1;
import static com.example.apurba.disaster.disasterreport.EarthQuakeFragment.EXTRA_MESSAGE_2;
import static com.example.apurba.disaster.disasterreport.EarthQuakeFragment.EXTRA_MESSAGE_3;

public class EarthQuakeItemAdapterRecycler extends RecyclerView.Adapter<EarthQuakeItemAdapterRecycler.ViewHolder>{

    private List<EarthQuakeItem> mDataset;
    private Activity mContext;
    // Provide a suitable constructor (depends on the kind of dataset)
    public EarthQuakeItemAdapterRecycler(Activity context, List<EarthQuakeItem> Dataset) {
        mDataset = Dataset;
        mContext = context;
    }

    public void clearData(){
        mDataset.clear();
    }

    public void addAllData(List<EarthQuakeItem> dataset){
        mDataset = dataset;
    }


    @Override
    public EarthQuakeItemAdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.earth_quake_item_list, parent, false);
        return  new ViewHolder(view, mDataset);
    }

    @Override
    public void onBindViewHolder(EarthQuakeItemAdapterRecycler.ViewHolder holder, int position) {
        //holder.mTextView.setText(mDataset.get(position));
        EarthQuakeItem currentEarthQuake = mDataset.get(position);
        // Get the appropriate background color based on the current earthquake magnitude
        int magnitudeColor = getMagnitudeColor(currentEarthQuake.getMagnitude());
        holder.magnitudeCircle.setColor(magnitudeColor);
        // set magnitude text
        holder.magnitudeView.setText(formatedMagnitude(currentEarthQuake.getMagnitude()));

        // split the loaction of current Earthquake
        currentEarthQuake.splitLocation();
        //set Loaction offset
        holder.mLoacationOffset.setText(currentEarthQuake.getLocationOffset());
        //set primary Location
        holder.mPrimaryLoaction.setText(currentEarthQuake.getPrimaryLocation());
        // create human readable formate for date and then display it
        Date dateObject = new Date(currentEarthQuake.getTimeInMilliseconds());
        holder.mDate.setText(formatDate(dateObject));
        // set time
        holder.mTime.setText(formatTime(dateObject));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
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
        return ContextCompat.getColor(mContext, magnitudeColorResourceId);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View mView;
        private List<EarthQuakeItem> mDataset;
        //public TextView mTextView;

        public TextView magnitudeView ;
        public TextView mLoacationOffset;
        public TextView mPrimaryLoaction;
        public TextView mDate;
        public TextView mTime;
        public GradientDrawable magnitudeCircle;

        // Constructor
        public ViewHolder(View v, List<EarthQuakeItem> dataset) {
            super(v);
            mView = v;
            v.setOnClickListener(this);
            mDataset = dataset;
            //mTextView = v.findViewById(R.id.text_view);
            setUpAllViews();
        }
        private void setUpAllViews(){
            magnitudeView = mView.findViewById(R.id.magnitude_text_field);
            mLoacationOffset = mView.findViewById(R.id.location_offset);
            mPrimaryLoaction = mView.findViewById(R.id.primary_location);
            mDate = mView.findViewById(R.id.date_text_field);
            mTime = mView.findViewById(R.id.time);
            magnitudeCircle = (GradientDrawable) magnitudeView.getBackground();
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            EarthQuakeItem clickedEarthQuake = mDataset.get(getAdapterPosition());

            Intent intent = new Intent(context, EarthQuakeDetailsActivity.class);
            String url = clickedEarthQuake.getUrl();
            String location = clickedEarthQuake.getLocation();
            double magnitude =  clickedEarthQuake.getMagnitude();
            intent.putExtra(EXTRA_MESSAGE_1, location);
            intent.putExtra(EXTRA_MESSAGE_2, magnitude);
            intent.putExtra(EXTRA_MESSAGE_3, url);
            context.startActivity(intent);
        }
    }
}
