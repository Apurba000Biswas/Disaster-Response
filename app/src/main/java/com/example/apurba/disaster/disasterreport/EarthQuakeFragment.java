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
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.apurba.disaster.disasterreport.database.DisasterReportDbContract.EarthQuakeEntry;

import java.util.ArrayList;
import java.util.List;

public class EarthQuakeFragment extends Fragment {

    public static final String EXTRA_MESSAGE_1 = "location";
    public static final String EXTRA_MESSAGE_2 = "magnitude";
    public static final String EXTRA_MESSAGE_3 = "url";
    private static final int EARTHQUAKE_DATA_LOADER_ID = 0;
    private static final int CURSOR_DATA_LOADER_ID = 1;

    private TextView mEmptyStateTextView;
    private View loading_indicator;
    private RecyclerView recyclerView;
    private EarthQuakeItemAdapterRecycler mAdapter;

    // Empty body constructor
    public EarthQuakeFragment() {
        // Required empty public constructor
    }

    /** public View onCreateView() method
     *  This method gets called when user enter into Earthquake tab in the viewPager
     *  @param inflater - used to inflate views from xml file
     *  @param container - it serve as parent of view group
     *  @param savedInstanceState - used to reconstruct the fragment from its previous state
     *  Its set up the UI for earthquake fragment, checks for internet connection and
     *  returns the view
     */
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.earthquake_fragment, container, false);

        final List<EarthQuakeItem> earthquakes =  new ArrayList<EarthQuakeItem>();
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerView);
        setRecyclerViewWithAdapter(recyclerView, earthquakes);

        mEmptyStateTextView = (TextView)rootView.findViewById(R.id.empty_view);

        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        setFloatingActionButton(fab);

        loading_indicator = rootView.findViewById(R.id.loading_spinner);

        initializeLoader();

        return rootView;
    }


    /** private void initializeLoader() method
     *  check for internet connection and initialize a loader
     */
    private void initializeLoader(){

        HelperClass mHelper = new HelperClass(getActivity());
        if(mHelper.isConnectedToInternet()){
            LoaderManager loaderManager = getLoaderManager(); // getLoaderManager() - returns a LoaderManager for this fragment
            // initLoader() - creates a new loader with the given id or initialize the previously created loader
            loaderManager.initLoader(EARTHQUAKE_DATA_LOADER_ID,
                    null,
                    earthquakeDataLoaderListener).forceLoad();
            loaderManager.initLoader(CURSOR_DATA_LOADER_ID,
                    null,
                    cursorLoaderListener).forceLoad();
        }else {
            loading_indicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
        }
    }


    /** private void setFloatingActionButton
     *  This method set up the floating point button to trigger the website view activity when
     *  click happened
     *  @param fab - This button get set up here
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
                    Toast.makeText(getContext(), "Error: " +
                            e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /** private void setRecyclerViewWithAdapter() method
     * @param recyclerView - this view get set up here
     * @param earthquakes - the recycler view takes a list of EarthquakeItem
     * This method setup the recyclerView with appropriate settings
     * This method also initialize the adapter with the list
     */
    private void setRecyclerViewWithAdapter(RecyclerView recyclerView,
                                            List<EarthQuakeItem> earthquakes){

        recyclerView.setHasFixedSize(true); // This sittings to improve performance
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new EarthQuakeItemAdapterRecycler(getActivity(), earthquakes);
    }


    private LoaderManager.LoaderCallbacks<List<EarthQuakeItem>> earthquakeDataLoaderListener =
            new LoaderManager.LoaderCallbacks<List<EarthQuakeItem>>() {

        /** public Loader<List<EarthQuakeItem>> onCreateLoader() method
         *  This method called from the main thread
         *  first get settings from shared preference then creates a new EarthquakeLoader
         *  with those settings ( used to build url ) then returns the Loader
         *  parameter i - refers to the id ( which loader to create)
         *  parameter bundle - any arguments supplied by caller, This case its "null"
         *  - returns the loader
         */
        @Override
        public Loader<List<EarthQuakeItem>> onCreateLoader(int id, Bundle args) {

            //get settings from shared preferences
            SharedPreferences sharedPrefs =
                    PreferenceManager.getDefaultSharedPreferences(getActivity());
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


        /** public void onLoadFinished() method
         *  This method gets called from main thread after loading data in the background thread using loader
         *  first it set loading indicator to be gone and if adapter has any previous data, it clear those data
         *  if List is empty then it clears the recyclerView from UI and then set Empty state view
         *  if List is not empty then add the recyclerView with the list on the UI
         * @param loader -
         * @param earthquakes -
         */
        @Override
        public void onLoadFinished(Loader<List<EarthQuakeItem>> loader,
                                   List<EarthQuakeItem> earthquakes) {
            loading_indicator.setVisibility(View.GONE);

            mAdapter.clearData();

            if (earthquakes != null){
                if (earthquakes.isEmpty()) {
                    recyclerView.setVisibility(View.GONE);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                } else {
                    mAdapter.addAllData(earthquakes);
                    recyclerView.setAdapter(mAdapter);
                    recyclerView.setVisibility(View.VISIBLE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                }
            }
            mEmptyStateTextView.setText(R.string.no_earthquakes);
        }


        /** public void onLoaderReset() method
         *  Called when a previously created loader is being reset, and thus making its data unavailable
         *  when loader reset it clears the adapter data that was being showed earlier
         */
        @Override
        public void onLoaderReset(Loader<List<EarthQuakeItem>> loader) {
            mAdapter.clearData();
        }
    };


    private LoaderManager.LoaderCallbacks<Cursor> cursorLoaderListener =
            new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override
        public Loader<Cursor> onCreateLoader(int id, Bundle args) {

            String[] projection = {EarthQuakeEntry._ID,
                    EarthQuakeEntry.COLUMN_E_ID,
                    EarthQuakeEntry.COLUMN_LOCATION};
            return new CursorLoader(getContext(),
                    EarthQuakeEntry.CONTENT_URI,
                    projection,
                    null,
                    null,
                    null);
        }

        @Override
        public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        }

        @Override
        public void onLoaderReset(Loader<Cursor> loader) {

        }
    };
}
