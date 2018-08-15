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

        DisasterDatabaseLoader mDatabaseLoader = DisasterDatabaseLoader.getObject(getContext(), getLoaderManager());
        mDatabaseLoader.LoadDisasterDatabase();

        mDataset = getDataSet(mDatabaseLoader);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        if(mDataset != null){
            mAdapter = new StatisticsLocationAdapater(getActivity(), mDataset);
            mAdapter.adAllData(mDataset);
            recyclerView.setAdapter(mAdapter);
        }
        Button refreshButton = rootView.findViewById(R.id.refresh_button);
        setRefreshButton(refreshButton, mDatabaseLoader);
        Button deleteAllButton = rootView.findViewById(R.id.delete_button);
        setDeleteButton(deleteAllButton, mDatabaseLoader);

        return rootView;
    }

    private void setDeleteButton(Button deleteButton, final  DisasterDatabaseLoader mDatabaseLoader){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDatabaseLoader != null){
                    int rowsDeleted = mDatabaseLoader.deleteAllData();
                    if(rowsDeleted != 0){
                        mDataset.clear();
                        mAdapter.clearData();
                        recyclerView.setAdapter(mAdapter);
                        Toast.makeText(getContext(), "All Data Deleted", Toast.LENGTH_SHORT).show();
                    }else{
                        mDataset.clear();
                        mAdapter.clearData();
                        Toast.makeText(getContext(), "Nothing to delete", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }




    private void setRefreshButton(Button refreshButton , final DisasterDatabaseLoader mDatabaseLoader){
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabaseLoader.reloadData();
                mDataset = getDataSet(mDatabaseLoader);
                mAdapter.adAllData(mDataset);
                if(mDataset != null){
                    if(mDataset.isEmpty()){
                        Toast.makeText(getContext(), "Make sure you saved all data", Toast.LENGTH_SHORT).show();
                    }else{
                        recyclerView.setAdapter(mAdapter);
                        Toast.makeText(getContext(), "Refreshed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private ArrayList<StatisticsLocation> getDataSet(DisasterDatabaseLoader mDatabaseLoader){
        ArrayList<StatisticsLocation> dataset = new ArrayList<>();
        if(mDatabaseLoader != null){

            Map<String, Integer> locations = mDatabaseLoader.getEarthquakeLocationMap();
            if(locations != null){
                if(!locations.isEmpty()){
                    for(String currentLocation : locations.keySet()){
                        dataset.add(makeStatisticsObject(currentLocation,
                                locations.get(currentLocation)));
                    }
                }
            }
        }
        return dataset;
    }

    private StatisticsLocation makeStatisticsObject(String location, int occurance){
        return new StatisticsLocation(location, occurance);
    }
}
