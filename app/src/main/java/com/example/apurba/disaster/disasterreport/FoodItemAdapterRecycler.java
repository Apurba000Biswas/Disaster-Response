package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 4/24/2018.
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

import java.util.List;

import static com.example.apurba.disaster.disasterreport.FloodFragment.EXTRA_MESSAGE1;
import static com.example.apurba.disaster.disasterreport.FloodFragment.EXTRA_MESSAGE2;
import static com.example.apurba.disaster.disasterreport.FloodFragment.EXTRA_MESSAGE3;
import static com.example.apurba.disaster.disasterreport.FloodFragment.EXTRA_MESSAGE4;
import static com.example.apurba.disaster.disasterreport.FloodFragment.EXTRA_MESSAGE5;
import static com.example.apurba.disaster.disasterreport.FloodFragment.EXTRA_MESSAGE6;
import static com.example.apurba.disaster.disasterreport.FloodFragment.EXTRA_MESSAGE7;
import static com.example.apurba.disaster.disasterreport.FloodFragment.EXTRA_MESSAGE8;

public class FoodItemAdapterRecycler extends RecyclerView.Adapter<FoodItemAdapterRecycler.ViewHolder>{

    private List<FloodItem> mDataset;
    private Activity mContext;
    // Provide a suitable constructor (depends on the kind of dataset)
    public FoodItemAdapterRecycler(Activity context, List<FloodItem> Dataset) {
        mDataset = Dataset;
        mContext = context;
    }

    public void clearData(){
        mDataset.clear();
    }

    public void addAllData(List<FloodItem> dataset){
        mDataset = dataset;
    }

    @Override
    public FoodItemAdapterRecycler.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.flood_item_list, parent, false);
        return  new ViewHolder(view, mDataset);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FloodItem currentFlood = mDataset.get(position);
        int severityLevelCircleColor = getSeverityCircleColor(currentFlood.getSevertyLevelInt());
        holder.severityLevelCircle.setColor(severityLevelCircleColor);
        holder.severityLevel.setText(currentFlood.getSeverityLevel());
        holder.severity.setText(currentFlood.getSeverity());
        holder.ea_areaName.setText(currentFlood.getEaAreaName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    /**
     * Returns the approriate color for Severity text field
     */
    private int getSeverityCircleColor(int magnitude){
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






    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public View mView;
        private List<FloodItem> mDataset;
        //public TextView mTextView;

        public TextView severityLevel ;
        public TextView severity;
        public TextView ea_areaName;
        public GradientDrawable severityLevelCircle;

        // Constructor
        public ViewHolder(View v, List<FloodItem> dataset) {
            super(v);
            mView = v;
            v.setOnClickListener(this);
            mDataset = dataset;
            setUpAllViews();
        }
        private void setUpAllViews(){
            severityLevel = mView.findViewById(R.id.severityLevel);
            severity = mView.findViewById(R.id.severity);
            ea_areaName = mView.findViewById(R.id.ea_areaName);
            severityLevelCircle = (GradientDrawable) severityLevel.getBackground();
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            FloodItem clickedFlood = mDataset.get(getAdapterPosition());

            String eaAreaName = clickedFlood.getEaAreaName();
            String county = clickedFlood.getCounty();
            String riverOrSea = clickedFlood.getRiverOrSea();
            String severity = clickedFlood.getSeverity();
            String severityLevel = clickedFlood.getSeverityLevel();
            String timeRaised = clickedFlood.getTimeRaised();
            String message = clickedFlood.getMessage();
            int severityLevelInt = clickedFlood.getSevertyLevelInt();

            Intent intent = new Intent(context, FloodDetailsActivity.class);
            intent.putExtra(EXTRA_MESSAGE1, eaAreaName);
            intent.putExtra(EXTRA_MESSAGE2, county);
            intent.putExtra(EXTRA_MESSAGE3, riverOrSea);
            intent.putExtra(EXTRA_MESSAGE4, severity);
            intent.putExtra(EXTRA_MESSAGE5, severityLevel);
            intent.putExtra(EXTRA_MESSAGE6, timeRaised);
            intent.putExtra(EXTRA_MESSAGE7, message);
            intent.putExtra(EXTRA_MESSAGE8, severityLevelInt);

            context.startActivity(intent);

        }
    }
}
