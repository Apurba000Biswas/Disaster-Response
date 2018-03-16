package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/14/2018.
 */

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class FloodItemAdapter extends BaseAdapter{

    private Context mContext;
    private List<FloodItem> mFloods;

    public FloodItemAdapter(Context c, List<FloodItem> floods) {
        mContext = c;
        mFloods = floods;
    }

    public int getCount() {
        return mFloods.size();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridFloodView = convertView;
        if (convertView == null) {
            gridFloodView = LayoutInflater.from(mContext).inflate(R.layout.flood_item_list, parent, false);
        }
        FloodItem currentFlood = mFloods.get(position);
        TextView severityLevelTextView = gridFloodView.findViewById(R.id.severityLevel);
        TextView severityTextView = gridFloodView.findViewById(R.id.severity);
        TextView eaAreaTextView = gridFloodView.findViewById(R.id.ea_areaName);

        GradientDrawable severityLevelCircle = (GradientDrawable) severityLevelTextView.getBackground();
        severityLevelCircle.setColor(getSeverityCircleColor(currentFlood.getSevertyLevelInt()));

        severityLevelTextView.setText(currentFlood.getSeverityLevel());
        severityTextView.setText(currentFlood.getSeverity());
        eaAreaTextView.setText(currentFlood.getEaAreaName());

        return gridFloodView;
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

    public void addAll(List<FloodItem> floods){
        mFloods = floods;
    }

    public void clear(){
        mFloods = null;
    }

}
