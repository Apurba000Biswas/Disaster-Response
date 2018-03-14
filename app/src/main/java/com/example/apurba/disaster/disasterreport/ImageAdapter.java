package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/14/2018.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImageAdapter extends BaseAdapter{

    private Context mContext;
    private List<FloodItem> mFloods;

    public ImageAdapter(Context c, List<FloodItem> floods) {
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

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridFloodView = convertView;
        if (convertView == null) {
            gridFloodView = LayoutInflater.from(mContext).inflate(R.layout.flood_item_list, parent, false);
        }
        FloodItem currentFlood = mFloods.get(position);
        TextView severityLevelTextView = gridFloodView.findViewById(R.id.severityLevel);
        TextView severityTextView = gridFloodView.findViewById(R.id.severity);
        TextView eaAreaTextView = gridFloodView.findViewById(R.id.ea_areaName);

        severityLevelTextView.setText(currentFlood.getSeverityLevel());
        severityTextView.setText(currentFlood.getSeverity());
        eaAreaTextView.setText(currentFlood.getEaAreaName());

        return gridFloodView;
    }

    // references to our images
   /* private Integer[] mThumbIds = {
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7,
            R.drawable.sample_0, R.drawable.sample_1,
            R.drawable.sample_2, R.drawable.sample_3,
            R.drawable.sample_4, R.drawable.sample_5,
            R.drawable.sample_6, R.drawable.sample_7
    };
    */
}
