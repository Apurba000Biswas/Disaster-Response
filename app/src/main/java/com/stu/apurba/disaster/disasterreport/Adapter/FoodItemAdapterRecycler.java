package com.stu.apurba.disaster.disasterreport.Adapter;

/*
 * Created by Apurba on 4/24/2018.
 *
 * FoodItemAdapterRecycler:
 * Supply floods data to the recycler view
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stu.apurba.disaster.disasterreport.Activities.FloodDetailsActivity;
import com.stu.apurba.disaster.disasterreport.DataModel.FloodItem;
import com.stu.apurba.disaster.disasterreport.Utils.HelperClass;
import com.stu.apurba.disaster.disasterreport.R;

import java.util.List;

import static com.stu.apurba.disaster.disasterreport.Fragment.FloodFragment.EXTRA_MESSAGE1;
import static com.stu.apurba.disaster.disasterreport.Fragment.FloodFragment.EXTRA_MESSAGE2;
import static com.stu.apurba.disaster.disasterreport.Fragment.FloodFragment.EXTRA_MESSAGE3;
import static com.stu.apurba.disaster.disasterreport.Fragment.FloodFragment.EXTRA_MESSAGE4;
import static com.stu.apurba.disaster.disasterreport.Fragment.FloodFragment.EXTRA_MESSAGE5;
import static com.stu.apurba.disaster.disasterreport.Fragment.FloodFragment.EXTRA_MESSAGE6;
import static com.stu.apurba.disaster.disasterreport.Fragment.FloodFragment.EXTRA_MESSAGE7;
import static com.stu.apurba.disaster.disasterreport.Fragment.FloodFragment.EXTRA_MESSAGE8;

public class FoodItemAdapterRecycler extends
        RecyclerView.Adapter<FoodItemAdapterRecycler.ViewHolder>{

    private List<FloodItem> mDataset;
    private Activity mContext;

    public FoodItemAdapterRecycler(Activity context, List<FloodItem> Dataset) {
        mDataset = Dataset;
        mContext = context;
    }

    // clears all data from the list
    public void clearData(){
        mDataset.clear();
    }
    // add data from given list to the current list
    public void addAllData(List<FloodItem> dataset){
        mDataset = dataset;
    }

    @Override
    public FoodItemAdapterRecycler.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(parent.getContext()).inflate(R.layout.flood_item_list,
                parent,
                false);
        return  new ViewHolder(view, mDataset);
    }

    // binds all views with the layout
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        FloodItem currentFlood = mDataset.get(position);
        HelperClass mHelper = new HelperClass(mContext);
        int severityLevelCircleColor =
                mHelper.getSeverityCircleColor(currentFlood.getSevertyLevelInt());
        holder.severityLevelCircle.setColor(severityLevelCircleColor);
        holder.severityLevel.setText(currentFlood.getSeverityLevel());
        holder.severity.setText(currentFlood.getSeverity());
        holder.ea_areaName.setText(currentFlood.getEaAreaName());
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    // creates a viw holder that holds each single item in the recycler view
    public static class ViewHolder extends
            RecyclerView.ViewHolder implements
            View.OnClickListener {

        public View mView;
        private List<FloodItem> mDataset;

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
            initAllViews();
        }
        // initializes all views
        private void initAllViews(){
            severityLevel = mView.findViewById(R.id.severityLevel);
            severity = mView.findViewById(R.id.severity);
            ea_areaName = mView.findViewById(R.id.ea_areaName);
            severityLevelCircle = (GradientDrawable) severityLevel.getBackground();
        }

        // when clciked happen in  a item ,
        // this method starts details activity with the clicked item
        @Override
        public void onClick(View view) {
            Context context = view.getContext();
            FloodItem clickedFlood = mDataset.get(getAdapterPosition());

            // get all info of the current flood
            String eaAreaName = clickedFlood.getEaAreaName();
            String county = clickedFlood.getCounty();
            String riverOrSea = clickedFlood.getRiverOrSea();
            String severity = clickedFlood.getSeverity();
            String severityLevel = clickedFlood.getSeverityLevel();
            String timeRaised = clickedFlood.getTimeRaised();
            String message = clickedFlood.getMessage();
            int severityLevelInt = clickedFlood.getSevertyLevelInt();

            // put those info to the intent
            Intent intent = new Intent(context, FloodDetailsActivity.class);
            intent.putExtra(EXTRA_MESSAGE1, eaAreaName);
            intent.putExtra(EXTRA_MESSAGE2, county);
            intent.putExtra(EXTRA_MESSAGE3, riverOrSea);
            intent.putExtra(EXTRA_MESSAGE4, severity);
            intent.putExtra(EXTRA_MESSAGE5, severityLevel);
            intent.putExtra(EXTRA_MESSAGE6, timeRaised);
            intent.putExtra(EXTRA_MESSAGE7, message);
            intent.putExtra(EXTRA_MESSAGE8, severityLevelInt);

            // start the activity
            context.startActivity(intent);
        }
    }
}
