package com.example.apurba.disaster.disasterreport.Adapter;

/*
 * Created by Apurba on 8/14/2018.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.apurba.disaster.disasterreport.Activities.StatisticsLocationDetailsActivity;
import com.example.apurba.disaster.disasterreport.R;
import com.example.apurba.disaster.disasterreport.DataModel.StatisticsLocation;

import java.util.List;

import static com.example.apurba.disaster.disasterreport.Fragment.EarthQuakeFragment.EXTRA_MESSAGE_1;

public class StatisticsLocationAdapter extends
        RecyclerView.Adapter<StatisticsLocationAdapter.ViewHolder> {

    private List<StatisticsLocation> mDataset;
    private Activity mContext;

    public StatisticsLocationAdapter(Activity context,
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
    public StatisticsLocationAdapter.ViewHolder
    onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistics_item,
                parent, false);

        return new ViewHolder(view, mDataset);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        StatisticsLocation currentLocation = mDataset.get(position);
        holder.locationTextView.setText(currentLocation.getLocation());
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
        private TextView locationTextView;
        private TextView countTextView;

        public ViewHolder(View itemView, List<StatisticsLocation> dataset) {
            super(itemView);
            view = itemView;
            itemView.setOnClickListener(this);
            mDataset = dataset;
            initAllViews();
        }

        private void initAllViews(){
            locationTextView = view.findViewById(R.id.location_text);
            countTextView = view.findViewById(R.id.count_text);
        }

        @Override
        public void onClick(View view) {
            Context context = view.getContext();

            StatisticsLocation clickedLocation = mDataset.get(getAdapterPosition());
            Intent intent = new Intent(context, StatisticsLocationDetailsActivity.class);
            intent.putExtra(EXTRA_MESSAGE_1, clickedLocation.getLocation());
            context.startActivity(intent);
        }
    }
}
