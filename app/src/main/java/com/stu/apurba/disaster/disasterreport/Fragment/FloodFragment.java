package com.stu.apurba.disaster.disasterreport.Fragment;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.stu.apurba.disaster.disasterreport.Activities.WebsiteViewActivity;
import com.stu.apurba.disaster.disasterreport.DataModel.FloodItem;
import com.stu.apurba.disaster.disasterreport.Loader.FloodLoader;
import com.stu.apurba.disaster.disasterreport.Adapter.FoodItemAdapterRecycler;
import com.stu.apurba.disaster.disasterreport.Utils.HelperClass;
import com.stu.apurba.disaster.disasterreport.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Creates a fragment for flood
 */
public class FloodFragment extends Fragment implements
        LoaderManager.LoaderCallbacks<List<FloodItem>> {
    private static final String ENVIRONMENT_DATA_URL =
            "https://environment.data.gov.uk/flood-monitoring/id/floods?";
    private static final String ENVIRONMENT_WEBSITE_URL =
            "https://flood-warning-information.service.gov.uk/warnings";

    public static final String EXTRA_MESSAGE1 = "eaAreaName";
    public static final String EXTRA_MESSAGE2 = "county";
    public static final String EXTRA_MESSAGE3 = "riverOrSea";
    public static final String EXTRA_MESSAGE4 = "severity";
    public static final String EXTRA_MESSAGE5 = "severityLevel";
    public static final String EXTRA_MESSAGE6 = "timeRaised";
    public static final String EXTRA_MESSAGE7 = "message";
    public static final String EXTRA_MESSAGE8 = "severityLevelInt";
    public static final String EXTRA_MESSAGE9 = "uri for web view";

    private TextView mEmptyStateTextView;
    private View loading_indicator;
    private List<FloodItem> mDataset;

    private FoodItemAdapterRecycler mAdapter;
    private RecyclerView recyclerView;

    public FloodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flood_fragment,
                container,
                false);

        mDataset = new ArrayList<>();

        // set the recycler view with grid layout manager
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager =
                new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FoodItemAdapterRecycler(getActivity(), mDataset);
        mEmptyStateTextView = (TextView)rootView.findViewById(R.id.empty_Text_view);

        loading_indicator = rootView.findViewById(R.id.loading_spinner);

        // Setup FAB to open Website View Activity
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getActivity(), WebsiteViewActivity.class);
                    intent.putExtra(EXTRA_MESSAGE9, ENVIRONMENT_WEBSITE_URL);
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    //Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        HelperClass mHelper = new HelperClass(getActivity());

        // check for internet connection and load data form url in background thread
        if (mHelper.isConnectedToInternet()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0,
                    null,
                    FloodFragment.this).forceLoad();
        }else{
            mAdapter.clearData();
            recyclerView.setAdapter(mAdapter);
            loading_indicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        return rootView;
    }

    /**
     * Inittialize the loader with appropriate url
     */
    @Override
    public Loader<List<FloodItem>> onCreateLoader(int id, Bundle args) {
        // get settings from shared preferences
        SharedPreferences sharedPrefs =
                PreferenceManager.getDefaultSharedPreferences(getActivity());
        String minSeverityLevel = sharedPrefs.getString(
                getString(R.string.settings_min_severity_level_key),
                getString(R.string.settings_min_severity_level_default));

        String maxResult = sharedPrefs.getString(
                getString(R.string.settings_max_result_key),
                getString(R.string.settings_max_result_default));

        //set up appropriate url with settings
        Uri baseUri = Uri.parse(ENVIRONMENT_DATA_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("_limit", maxResult);
        uriBuilder.appendQueryParameter("min-severity", minSeverityLevel);

        return new FloodLoader(getActivity(),uriBuilder.toString());
    }

    /**
     * After load finish sets the adapter with loaded arrayList
     */
    @Override
    public void onLoadFinished(Loader<List<FloodItem>> loader, List<FloodItem> flood) {
        loading_indicator.setVisibility(View.GONE);

        mAdapter.clearData();
        recyclerView.setAdapter(mAdapter);

        HelperClass mHelper = new HelperClass(getActivity());
        if(mHelper.isConnectedToInternet()){
            if(flood != null){
                if (flood.isEmpty()) {
                   // recyclerView.setVisibility(View.GONE);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                }
                else {
                    mAdapter.addAllData(flood);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                }
            }
            mEmptyStateTextView.setText(R.string.no_floods);
        }else{
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }

    /**
     * when loader reset to load it clear the adapter data
     */
    @Override
    public void onLoaderReset(Loader<List<FloodItem>> loader) {
        mAdapter.clearData();
    }
}
