package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 8/14/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StatisticsLocationAdapater extends
        RecyclerView.Adapter<StatisticsLocationAdapater.ViewHolder> {

    private List<StatisticsLocation> mDataset;
    private Activity mContext;

    public StatisticsLocationAdapater(Activity context,
                                      List<StatisticsLocation> dataset){
        this.mContext = context;
        this.mDataset = dataset;
    }


    public void clearData(){
        mDataset.clear();
    }
    public void adAllData(List<StatisticsLocation> dataset){
        mDataset = dataset;
    }


    @Override
    public StatisticsLocationAdapater.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistics_item,
                parent, false);

        return new ViewHolder(view, mDataset);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StatisticsLocation currentLocation = mDataset.get(position);
        holder.locatinTextView.setText(currentLocation.getLocation());
        holder.countTextView.setText(String.valueOf(currentLocation.getOccurance()));

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public class ViewHolder extends
            RecyclerView.ViewHolder implements View.OnClickListener{

        public View view;
        private List<StatisticsLocation> mDataset;
        private TextView locatinTextView;
        private TextView countTextView;

        public ViewHolder(View itemView, List<StatisticsLocation> dataset) {
            super(itemView);
            view = itemView;
            itemView.setOnClickListener(this);
            mDataset = dataset;
            initAllViews();
        }

        private void initAllViews(){
            locatinTextView = view.findViewById(R.id.location_text);
            countTextView = view.findViewById(R.id.count_text);
        }

        @Override
        public void onClick(View view) {


            System.out.println("Statistics Location onclick method gets called");
            Context context = view.getContext();

            StatisticsLocation clickedLocation = mDataset.get(getAdapterPosition());

            Toast.makeText(context, "Ok clicked on " + clickedLocation.getLocation(), Toast.LENGTH_SHORT).show();
        }
    }
}
