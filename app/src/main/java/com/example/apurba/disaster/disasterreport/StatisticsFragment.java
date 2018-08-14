package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 8/13/2018.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.apurba.disaster.disasterreport.database.DisasterDatabaseLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class StatisticsFragment extends Fragment{

    private RecyclerView recyclerView;
    private List<StatisticsLocation> mDataset;
    StatisticsLocationAdapater mAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.statistics_fragment,
                container,
                false);

        final DisasterDatabaseLoader mDatabaseLoader = DisasterDatabaseLoader.getLoadedObject();

        mDataset = getDataSet(mDatabaseLoader);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        if(mDataset != null){
            mAdapter = new StatisticsLocationAdapater(getActivity(), mDataset);
            mAdapter.adAllData(mDataset);
            recyclerView.setAdapter(mAdapter);
        }else{
            Toast.makeText(getContext(), "make sure you saved all earthquake data", Toast.LENGTH_SHORT).show();
        }
        Button clickMeButton = rootView.findViewById(R.id.clicke_me);

        clickMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataset = getDataSet(mDatabaseLoader);
                mAdapter.adAllData(mDataset);
                if(mDataset == null){
                    Toast.makeText(getContext(), "Make sure you saved all data", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
                }
                recyclerView.setAdapter(mAdapter);
            }
        });
        return rootView;
    }

    private ArrayList<StatisticsLocation> getDataSet(DisasterDatabaseLoader mDatabaseLoader){
        ArrayList<StatisticsLocation> dataset = new ArrayList<>();
        if(mDatabaseLoader != null){

            Map<String, Integer> locations = mDatabaseLoader.getEarthquakeLocationMap();
            if(locations != null){
                if(!locations.isEmpty()){
                    for(String currentLocation : locations.keySet()){
                        dataset.add(makeStatisticsObject(currentLocation, locations.get(currentLocation)));
                    }
                }
            }else {
                Toast.makeText(getContext(), "Database is empty", Toast.LENGTH_SHORT).show();
            }
        }
        return dataset;
    }

    private StatisticsLocation makeStatisticsObject(String location, int occurance){
        return new StatisticsLocation(location, occurance);
    }
}
