package com.example.apurba.disaster.disasterreport;

/** EarthQuakeFragment class
 *
 * Created by Apurba on 3/13/2018.
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * creates a fragment for earthquake and also
 * implements {@link android.support.v4.app.LoaderManager.LoaderCallbacks} interface.
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

public class EarthQuakeFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<EarthQuakeItem>> {

    public static final String EXTRA_MESSAGE_1 = "location";
    public static final String EXTRA_MESSAGE_2 = "magnitude";
    public static final String EXTRA_MESSAGE_3 = "url";

    private TextView mEmptyStateTextView;
    private View loading_indicator;
    private RecyclerView recyclerView;
    private EarthQuakeItemAdapterRecycler mAdapter;

    // Empty body constructor
    public EarthQuakeFragment() {
        // Required empty public constructor
    }

    /** public View onCreateView() method
     *  This method gets called user enter into Earthquake tab in the viewPager
     *  @param inflater - used to inflate views from xml file
     *  @param container - it serve as parent of view group
     *  @param savedInstanceState - used to reconstruct the fragment from its previous state
     *  Its set up the UI for earthquak fragment, checks for internet connection and
     *  returns the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.earthquake_fragment, container, false);

        // set recycler view
        final List<EarthQuakeItem> earthquakes =  new ArrayList<EarthQuakeItem>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        setRecyclerViewWithAdapter(recyclerView, earthquakes);

        // get empty view
        mEmptyStateTextView = (TextView)rootView.findViewById(R.id.empty_view);

        // Setup FAB to open Website View Activity
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        setFloatingActionButton(fab);

        // get the loading spinner
        loading_indicator = rootView.findViewById(R.id.loading_spinner);

        // trigger the loader
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

    /** private void setFloatingActionButton
     *  This method set up the floating point button to trigger the website view activity when
     *  click happened
     * @param fab - This button get set up here
     *  If any exception occurs to opening the activity it also pop up a toast message
     */
    private void setFloatingActionButton(FloatingActionButton fab){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(getActivity(), WebsiteViewActivity.class);
                    intent.putExtra(EXTRA_MESSAGE_3, getString(R.string.usgs_url));
                    startActivity(intent);

                }catch (ActivityNotFoundException e){
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /** private void setRecyclerViewWithAdapter() method
     * @param recyclerView - this view get set up here
     * @param earthquakes - the recycler view takes a list of EarthquakeItem
     * This method setup the recyclerView with appropriate settings
     * This method also initialize the adapater with the list
     */
    private void setRecyclerViewWithAdapter(RecyclerView recyclerView, List<EarthQuakeItem> earthquakes){
        // This sittings to improve performance
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EarthQuakeItemAdapterRecycler(getActivity(), earthquakes);
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
        Uri baseUri = Uri.parse(getString(R.string.usgs_request_url));
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
