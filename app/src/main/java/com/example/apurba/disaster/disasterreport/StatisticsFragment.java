package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 8/13/2018.
 */

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.apurba.disaster.disasterreport.database.DisasterDatabaseLoader;

import java.util.Map;


public class StatisticsFragment extends Fragment{


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.statistics_fragment,
                container,
                false);

        Button clickMeButton = rootView.findViewById(R.id.clicke_me);

        final DisasterDatabaseLoader mDatabaseLoader = DisasterDatabaseLoader.getLoadedObject();
        clickMeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mDatabaseLoader != null){

                    Map<String, Integer> locations = mDatabaseLoader.getEarthquakeLocationMap();
                    if(locations != null){
                        if(!locations.isEmpty()){
                            for(String currentLocation : locations.keySet()){
                                System.out.println(currentLocation+":  " + locations.get(currentLocation));
                            }
                        }
                    }else {
                        Toast.makeText(getContext(), "Database is empty", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return rootView;
    }
}
