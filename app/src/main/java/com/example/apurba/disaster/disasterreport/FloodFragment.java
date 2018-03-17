package com.example.apurba.disaster.disasterreport;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
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
public class FloodFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<FloodItem>> {
    private static final String LOG_TAG = FloodFragment.class.getSimpleName();
    private static final String ENVIRONMENT_DATA_URL = "https://environment.data.gov.uk/flood-monitoring/id/floods?";//min-severity=3&_limit=50

    public static final String EXTRA_MESSAGE1 = "eaAreaName";
    public static final String EXTRA_MESSAGE2 = "county";
    public static final String EXTRA_MESSAGE3 = "riverOrSea";
    public static final String EXTRA_MESSAGE4 = "severity";
    public static final String EXTRA_MESSAGE5 = "severityLevel";
    public static final String EXTRA_MESSAGE6 = "timeRaised";
    public static final String EXTRA_MESSAGE7 = "message";
    public static final String EXTRA_MESSAGE8 = "severityLevelInt";

    private TextView mEmptyStateTextView;
    private View loading_indicator;
    private List<FloodItem> floods;

    private FloodItemAdapter mAdapter;
    private GridView gridview;


    public FloodFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flood_fragment, container, false);

        floods = new ArrayList<FloodItem>();
        //floods.add(new FloodItem(3, "Flood Alert", "Maxico","XX","XX","XX","XX","XX"));

        gridview = (GridView) rootView.findViewById(R.id.gridview);
        mAdapter = new FloodItemAdapter(getActivity(), floods);
        gridview.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView)rootView.findViewById(R.id.empty_view);
        gridview.setEmptyView(mEmptyStateTextView);
        loading_indicator = rootView.findViewById(R.id.loading_spinner);


        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                FloodItem currentFlood = floods.get(position);

                //Toast.makeText(getActivity(), "" + /*position*/floods.get(position).getSeverity(), Toast.LENGTH_SHORT).show();

                String eaAreaName = currentFlood.getEaAreaName();
                String county = currentFlood.getCounty();
                String riverOrSea = currentFlood.getRiverOrSea();
                String severity = currentFlood.getSeverity();
                String severityLevel = currentFlood.getSeverityLevel();
                String timeRaised = currentFlood.getTimeRaised();
                String message = currentFlood.getMessage();
                int severityLevelInt = currentFlood.getSevertyLevelInt();

                Intent intent = new Intent(getActivity(), FloodDetailsActivity.class);
                intent.putExtra(EXTRA_MESSAGE1, eaAreaName);
                intent.putExtra(EXTRA_MESSAGE2, county);
                intent.putExtra(EXTRA_MESSAGE3, riverOrSea);
                intent.putExtra(EXTRA_MESSAGE4, severity);
                intent.putExtra(EXTRA_MESSAGE5, severityLevel);
                intent.putExtra(EXTRA_MESSAGE6, timeRaised);
                intent.putExtra(EXTRA_MESSAGE7, message);
                intent.putExtra(EXTRA_MESSAGE8, severityLevelInt);

                startActivity(intent);
            }
        });

        if (isConnectedToInternet()){
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, FloodFragment.this).forceLoad();
        }else{
            loading_indicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }

        return rootView;
    }

    /**
     * Check for internet connection
     */
    private boolean isConnectedToInternet(){
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }

    /**
     * Inittialize the loader with loader manager with appropriate url
     */
    @Override
    public Loader<List<FloodItem>> onCreateLoader(int id, Bundle args) {

        SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String minSeverityLevel = sharedPrefs.getString(
                getString(R.string.settings_min_severity_level_key),
                getString(R.string.settings_min_severity_level_default));

        String maxResult = sharedPrefs.getString(
                getString(R.string.settings_max_result_key),
                getString(R.string.settings_max_result_default));

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
        mAdapter.clear();

        if (flood != null && !flood.isEmpty()) {
            floods = flood;
            mAdapter.addAll(floods);
            gridview.setAdapter(mAdapter);
        }
        mEmptyStateTextView.setText(R.string.no_floods);

    }

    /**
     * when loader reset to load it clear the adapter data
     */
    @Override
    public void onLoaderReset(Loader<List<FloodItem>> loader) {
        mAdapter.clear();
    }
}
