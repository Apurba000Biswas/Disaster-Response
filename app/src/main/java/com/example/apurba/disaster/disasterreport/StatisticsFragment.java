package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 8/13/2018.
 */

import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.apurba.disaster.disasterreport.database.DisasterReportDbContract;
import com.example.apurba.disaster.disasterreport.database.DisasterReportDbContract.EarthQuakeEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class StatisticsFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<Cursor>{

    private static final int CURSOR_DATA_LOADER_ID = 1;
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
        mDataset = new ArrayList<StatisticsLocation>();
        mAdapter = new StatisticsLocationAdapater(getActivity(), mDataset);

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        recyclerView.setAdapter(mAdapter);

        FloatingActionButton refreshButton = rootView.findViewById(R.id.refresh_button);
        setRefreshButton(refreshButton, this);
        Button deleteAllButton = rootView.findViewById(R.id.delete_button);
        setDeleteButton(deleteAllButton);

        getLoaderManager().initLoader(CURSOR_DATA_LOADER_ID,
                null,
                this).forceLoad();
        return rootView;
    }

    private void setDeleteButton(Button deleteButton){
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDeleteConfirmationDialog();
            }
        });
    }

    private void showDeleteConfirmationDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(R.string.delete_all_earthquake_dialog_message);

        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteAllEarthquakes();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.upArrowColerd));
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.upArrowColerd));
    }


    private void deleteAllEarthquakes(){
        int rowsDeleted = deleteAllData();
        if(rowsDeleted != 0){
            Toast.makeText(getContext(),
                    "All Data Deleted",
                    Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(getContext(),
                    "Nothing to delete", Toast.LENGTH_SHORT).show();
        }
        mDataset.clear();
        mAdapter.clearData();
        recyclerView.setAdapter(mAdapter);
    }
    private int deleteAllData(){
        int rowsDeleted = 0;
        rowsDeleted = getContext().getContentResolver().delete(
                DisasterReportDbContract.EarthQuakeEntry.CONTENT_URI,
                null,
                null);
        return rowsDeleted;
    }


    private void setRefreshButton(final FloatingActionButton refreshButton,
                                  final LoaderManager.LoaderCallbacks<Cursor> callbacks){
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getLoaderManager().restartLoader(CURSOR_DATA_LOADER_ID, null, callbacks);
                if(mDataset.isEmpty()){
                    Toast.makeText(getContext(),
                            "Make sure you saved all data",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "Refreshd ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = {EarthQuakeEntry.COLUMN_LOCATION};
        return new CursorLoader(getContext(),
                EarthQuakeEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mAdapter.clearData();
        if (data.getCount() != 0){
            Map<String, Integer> locationMap = makeHashMap(data);
            makeDataSet(locationMap);
            if(!mDataset.isEmpty()){
                mAdapter.adAllData(mDataset);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setVisibility(View.VISIBLE);
            }
        }else{
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void makeDataSet(Map<String, Integer> locationMap){
        if(locationMap != null){
            if(!locationMap.isEmpty()){
                for(String currentLocation : locationMap.keySet()){
                    mDataset.add(makeStatisticsObject(currentLocation,
                            locationMap.get(currentLocation)));
                }
            }
        }
    }
    private StatisticsLocation
    makeStatisticsObject(String location, int occurance){
        return new StatisticsLocation(location, occurance);
    }


    private Map<String, Integer> makeHashMap(Cursor data){
        Map<String, Integer> locationMap = new HashMap<String, Integer>();
        int locationColumnIndex = data.getColumnIndex(EarthQuakeEntry.COLUMN_LOCATION);
        if(data.getCount() != 0){
            data.moveToFirst();
            String location;
            do {
                location = data.getString(locationColumnIndex);
                if(locationMap.containsKey(location)){
                    int count = locationMap.get(location);
                    //locationMap.remove(location);
                    count++;
                    locationMap.put(location, count);
                }else{
                    locationMap.put(location, 1);
                }
            }while (data.moveToNext());
        }
        return locationMap;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mAdapter.clearData();
        mDataset.clear();
    }
}
