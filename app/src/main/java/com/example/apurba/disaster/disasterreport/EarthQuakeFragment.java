package com.example.apurba.disaster.disasterreport;


/*
 * Created by Apurba on 3/13/2018.
 */

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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * creates a fragment for earthquake
 */
public class EarthQuakeFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<EarthQuakeItem>> {

    private static final String USGS_REQUEST_URL =  "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private static final String USGS_URL = "https://earthquake.usgs.gov/earthquakes/map/";

    public static final String EXTRA_MESSAGE_1 = "location";
    public static final String EXTRA_MESSAGE_2 = "magnitude";
    public static final String EXTRA_MESSAGE_3 = "url";

   // private EarthQuakeItemAdapter mAdapter;
    private TextView mEmptyStateTextView;
    private View loading_indicator;

    private RecyclerView recyclerView;
    private EarthQuakeItemAdapterRecycler mAdapter;

    public EarthQuakeFragment() {
        // Required empty public constructor
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.earthquake_fragment, container, false);

        //List of earthquake data
        final List<EarthQuakeItem> earthquakes =  new ArrayList<EarthQuakeItem>();

        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EarthQuakeItemAdapterRecycler(getActivity(),earthquakes);
        //recyclerView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView)rootView.findViewById(R.id.empty_view);

        // Setup FAB to open Website View Activity
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getActivity(), WebsiteViewActivity.class);
                    intent.putExtra(EXTRA_MESSAGE_3, USGS_URL);
                    startActivity(intent);

                }catch (ActivityNotFoundException e){
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        loading_indicator = rootView.findViewById(R.id.loading_spinner);

        HelperClass mHelper = new HelperClass(getActivity());

        if(mHelper.isConnectedToInternet()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, EarthQuakeFragment.this).forceLoad();
        }else {
            loading_indicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
        return rootView;
    }

    // Creates a loader to load from url in background thread
    @Override
    public Loader<List<EarthQuakeItem>> onCreateLoader(int i, Bundle bundle) {
        //get settings from shared preferences
        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String minMagnitude = sharedPrefs.getString(
                getString(R.string.settings_min_magnitude_key),
                getString(R.string.settings_min_magnitude_default));
        String orderBy = sharedPrefs.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        String maxResult = sharedPrefs.getString(
                getString(R.string.settings_max_result_key),
                getString(R.string.settings_max_result_default));

        // set up the url with appropriate settings
        Uri baseUri = Uri.parse(USGS_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter("format", "geojson");
        uriBuilder.appendQueryParameter("limit", maxResult);
        uriBuilder.appendQueryParameter("minmag", minMagnitude);
        uriBuilder.appendQueryParameter("orderby", orderBy);

        return new EarthquakeLoader(getActivity(), uriBuilder.toString());
    }

    /**
     * After load finish sets the adapter with loaded arrayList
     */
    @Override
    public void onLoadFinished(Loader<List<EarthQuakeItem>> loader, List<EarthQuakeItem> earthquakes) {
        loading_indicator.setVisibility(View.GONE);

        mAdapter.clearData();

        if (earthquakes != null){
            if (earthquakes.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                mEmptyStateTextView.setVisibility(View.VISIBLE);
            }
            else {
                mAdapter.addAllData(earthquakes);
                recyclerView.setAdapter(mAdapter);
                recyclerView.setVisibility(View.VISIBLE);
                mEmptyStateTextView.setVisibility(View.GONE);
            }
        }

        mEmptyStateTextView.setText(R.string.no_earthquakes);
    }

    /**
     * when loader reset to load it clear the adapter data
     */
    @Override
    public void onLoaderReset(Loader<List<EarthQuakeItem>> loader) {
        mAdapter.clearData();
    }
}
