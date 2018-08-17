package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 8/17/2018.
 */

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class StatisticsDetailsAdapter extends RecyclerView.Adapter<StatisticsDetailsAdapter.ViewHolder> {

    private Activity mContext;
    private List<EarthQuakeItem> mDataset;

    public StatisticsDetailsAdapter(Activity context,
                                      List<EarthQuakeItem> dataset){
        this.mContext = context;
        this.mDataset = dataset;
    }


    public void clearData(){
        mDataset.clear();
    }
    public void adAllData(List<EarthQuakeItem> dataset){
        mDataset = dataset;
    }


    @Override
    public StatisticsDetailsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.statistics_details_item,
                parent, false);
        return new ViewHolder(view, mDataset);
    }

    @Override
    public void onBindViewHolder(StatisticsDetailsAdapter.ViewHolder holder, int position) {

        EarthQuakeItem currentItem = mDataset.get(position);
        holder.magnitudeTextView.setText(String.valueOf(currentItem.getMagnitude()));
        currentItem.splitLocation();
        holder.offsetTextView.setText(currentItem.getLocationOffset());
        holder.primaryLocationTextView.setText(currentItem.getPrimaryLocation());
        Date dateObject = new Date(currentItem.getTimeInMilliseconds());
        holder.dateTextView.setText(formatDate(dateObject));
        holder.timeTextView.setText(formatTime(dateObject));
    }

    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat =
                new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime(Date dateObject) {
        SimpleDateFormat timeFormat =
                new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }





    public class ViewHolder extends
            RecyclerView.ViewHolder implements View.OnClickListener{


        public View view;
        private List<EarthQuakeItem> mDataset;
        private TextView magnitudeTextView;
        private TextView offsetTextView;
        private TextView primaryLocationTextView;
        private TextView dateTextView;
        private TextView timeTextView;

        public ViewHolder(View itemView, List<EarthQuakeItem> dataset) {
            super(itemView);
            view = itemView;
            itemView.setOnClickListener(this);
            mDataset = dataset;
            initAllViews();
        }

        private void initAllViews(){
            magnitudeTextView = view.findViewById(R.id.magnitude_text_field);
            offsetTextView = view.findViewById(R.id.offset_text_field);
            primaryLocationTextView = view.findViewById(R.id.primary_location_text_field);
            dateTextView = view.findViewById(R.id.date_text_field);
            timeTextView = view.findViewById(R.id.time);
        }


        @Override
        public void onClick(View view) {

        }
    }
}
