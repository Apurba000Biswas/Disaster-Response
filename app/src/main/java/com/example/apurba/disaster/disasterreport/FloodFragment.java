package com.example.apurba.disaster.disasterreport;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FloodFragment extends Fragment {


    public FloodFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flood_fragment, container, false);

        List<FloodItem> floods = new ArrayList<FloodItem>();
        floods.add(new FloodItem(3, "Flood Alert", "Maxico"));
        floods.add(new FloodItem(3, "Flood Alert", "Maxico"));
        floods.add(new FloodItem(3, "Flood Alert", "Maxico"));
        floods.add(new FloodItem(3, "Flood Alert", "Maxico"));
        floods.add(new FloodItem(3, "Flood Alert", "Maxico"));
        floods.add(new FloodItem(3, "Flood Alert", "Maxico"));
        floods.add(new FloodItem(3, "Flood Alert", "Maxico"));
        floods.add(new FloodItem(3, "Flood Alert", "Maxico"));
        floods.add(new FloodItem(3, "Flood Alert", "Maxico"));
        floods.add(new FloodItem(3, "Flood Alert", "Maxico"));

        GridView gridview = (GridView) rootView.findViewById(R.id.gridview);
        gridview.setAdapter(new ImageAdapter(getActivity(), floods));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        return rootView;
    }

}
