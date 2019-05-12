package com.stu.apurba.disaster.disasterreport.Fragment;

/** EarthQuakeFragment class
 *
 * Created by Apurba on 3/13/2018.
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * creates a fragment for earthquake
 * implements {@link android.support.v4.app.LoaderManager.LoaderCallbacks} interface.
 */

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.stu.apurba.disaster.disasterreport.DataModel.EarthQuakeItem;
import com.stu.apurba.disaster.disasterreport.Adapter.EarthQuakeItemAdapterRecycler;
import com.stu.apurba.disaster.disasterreport.Loader.EarthquakeLoader;
import com.stu.apurba.disaster.disasterreport.Utils.HelperClass;
import com.stu.apurba.disaster.disasterreport.R;
import com.stu.apurba.disaster.disasterreport.database.DisasterDatabaseLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EarthQuakeFragment extends Fragment {

    public static final String EXTRA_MESSAGE_1 = "location";
    public static final String EXTRA_MESSAGE_2 = "magnitude";
    public static final String EXTRA_MESSAGE_3 = "url";
    private static final int EARTHQUAKE_DATA_LOADER_ID = 0;

    private TextView mEmptyStateTextView;
    private ImageView mEmptyStateImageView;
    private View loading_indicator;
    private RecyclerView recyclerView;
    private EarthQuakeItemAdapterRecycler mAdapter;
    private HelperClass mHelper;

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
        View rootView = inflater.inflate(R.layout.earthquake_fragment,
                container,
                false);

        List<EarthQuakeItem> earthquakesList =  new ArrayList<>();
        mHelper = new HelperClass(getActivity());
        recyclerView =  rootView.findViewById(R.id.recyclerView);
        setRecyclerViewWithAdapter(recyclerView, earthquakesList);

        mEmptyStateTextView = rootView.findViewById(R.id.empty_Text_view);
        mEmptyStateImageView = rootView.findViewById(R.id.empty_image_view);
        mEmptyStateImageView.setVisibility(View.GONE);

        LoaderManager loaderManager = getLoaderManager();
        DisasterDatabaseLoader databaseLoader =
                DisasterDatabaseLoader.getObject(getContext());
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        setSaveButton(fab, databaseLoader);

        loading_indicator = rootView.findViewById(R.id.loading_spinner);

        FloatingActionButton search = rootView.findViewById(R.id.search_button);
        FloatingActionButton close = rootView.findViewById(R.id.close_button);
        EditText searchTextView = rootView.findViewById(R.id.search_text);
        Button searchIndicator = rootView.findViewById(R.id.search_indicator);

        initializeLoader(loaderManager);
        setSearchButton(search, close, searchTextView, searchIndicator);
        setCloseButton(close, searchTextView, searchIndicator, loaderManager);
        setSearchIndicator(searchIndicator, searchTextView,loaderManager);

        return rootView;
    }

    private void setSearchIndicator(Button searchIndicator,
                                    final EditText searchTextView,
                                    final LoaderManager loaderManager){

        searchIndicator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mHelper.isConnectedToInternet()){
                    Loader loader = loaderManager.getLoader(EARTHQUAKE_DATA_LOADER_ID);
                    mEmptyStateImageView.setVisibility(View.GONE);
                    mEmptyStateTextView.setVisibility(View.GONE);
                    EarthquakeLoader earthquakeLoader = (EarthquakeLoader) loader;
                    Map<String, List<EarthQuakeItem>> earthquakeMap =
                            earthquakeLoader.getEarthquakeMap();
                    if (earthquakeMap != null){
                        if ( !earthquakeMap.isEmpty()){
                            String location = searchTextView
                                    .getText()
                                    .toString()
                                    .trim()
                                    .toUpperCase();
                            doSearch(location, earthquakeMap);
                        }else{
                            Toast.makeText(getContext(),
                                    "No Data Found",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        mEmptyStateTextView.setText(R.string.no_earthquakes);
                        mEmptyStateTextView.setVisibility(View.VISIBLE);
                        mEmptyStateImageView.setVisibility(View.GONE);
                        loading_indicator.setVisibility(View.GONE);
                    }

                }else{
                    mEmptyStateTextView.setText(R.string.no_internet_connection);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                    mEmptyStateImageView.setVisibility(View.VISIBLE);
                    mAdapter.clearData();
                    recyclerView.setAdapter(mAdapter);
                }
            }
        });
    }


    private void doSearch(String inputedLocation,
                          Map<String, List<EarthQuakeItem>> earthquakeMap){
        if (earthquakeMap.containsKey(inputedLocation)){
            List<EarthQuakeItem> list = earthquakeMap.get(inputedLocation);
            mAdapter.addAllData(list);
            recyclerView.setAdapter(mAdapter);
            mEmptyStateTextView.setVisibility(View.GONE);
        }else{
            mAdapter.clearData();
            recyclerView.setAdapter(mAdapter);
            mEmptyStateTextView.setText(R.string.no_earthquakes);
            mEmptyStateTextView.setVisibility(View.VISIBLE);
            mEmptyStateImageView.setVisibility(View.GONE);
        }
    }

    private void setSearchButton(FloatingActionButton searchButton,
                                 final FloatingActionButton closeButton,
                                 final EditText searchTextView,
                                 final Button searchIndicator){
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (searchTextView.getVisibility() == View.GONE){
                    searchTextView.setVisibility(View.VISIBLE);
                    closeButton.setVisibility(View.VISIBLE);
                    searchIndicator.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setCloseButton(final FloatingActionButton closeButton,
                                final EditText searchText,
                                final Button searchIndicator,
                                final LoaderManager loaderManager){
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchText.setVisibility(View.GONE);
                closeButton.setVisibility(View.GONE);
                searchIndicator.setVisibility(View.GONE);
                mEmptyStateImageView.setVisibility(View.GONE);

                Loader loader = loaderManager.getLoader(EARTHQUAKE_DATA_LOADER_ID);
                EarthquakeLoader earthquakeLoader = (EarthquakeLoader) loader;
                if(earthquakeLoader != null){
                    List<EarthQuakeItem> list = earthquakeLoader.getEarthQuakeList();
                    if (list != null){
                        if ( !list.isEmpty() ){
                            mAdapter.clearData();
                            mAdapter.addAllData(list);
                            recyclerView.setAdapter(mAdapter);
                            mEmptyStateTextView.setVisibility(View.GONE);
                            mEmptyStateImageView.setVisibility(View.GONE);
                        }else{
                            mEmptyStateTextView.setText(R.string.no_earthquakes);
                            mEmptyStateImageView.setVisibility(View.GONE);
                            loading_indicator.setVisibility(View.GONE);
                        }
                    }else{
                        mEmptyStateTextView.setText(R.string.no_internet_connection);
                        mEmptyStateImageView.setVisibility(View.VISIBLE);
                        loading_indicator.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    /** private void initializeLoader() method
     *  check for internet connection and initialize a loader
     */
    private void initializeLoader(LoaderManager loaderManager){
        if(mHelper.isConnectedToInternet()){
            loading_indicator.setVisibility(View.VISIBLE);
            loaderManager.initLoader(EARTHQUAKE_DATA_LOADER_ID,
                    null,
                    earthquakeDataLoaderListener).forceLoad();
        }else {
            mAdapter.clearData();
            recyclerView.setAdapter(mAdapter);
            loading_indicator.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_internet_connection);
            mEmptyStateImageView.setVisibility(View.VISIBLE);
        }
    }


    /** private void setSaveButton
     *  This method set up the floating point save button to
     *  save all earthquake in the database. This Saving operation
     *  happens in a background thread when click happened
     *  @param fab - This button get set up here
     */
    private void setSaveButton(FloatingActionButton fab,
                               final DisasterDatabaseLoader dbLoader){
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                List<EarthQuakeItem> earthquakesList = mAdapter.getDataset();
                if(earthquakesList.size() != 0){
                    dbLoader.insertListIntoDatabase(earthquakesList);
                    Toast.makeText(getContext(), "Saving Data ...",
                            Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(),
                            "Make sure you are connected to the Internet",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    /** private void setRecyclerViewWithAdapter() method
     * @param recyclerView - this view get set up here
     * @param earthquakes - the recycler view takes a list of StatisticsLocation
     * This method setup the recyclerView with appropriate settings
     * Also initialize the adapter with the list
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
            loading_indicator.setVisibility(View.VISIBLE);
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
            recyclerView.setAdapter(mAdapter);
            if(mHelper.isConnectedToInternet()){
                if (earthquakes != null){
                    if (earthquakes.isEmpty()) {
                        mEmptyStateTextView.setVisibility(View.VISIBLE);
                        mEmptyStateTextView.setText(R.string.no_earthquakes);
                    } else {
                        mAdapter.addAllData(earthquakes);
                        recyclerView.setAdapter(mAdapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        mEmptyStateTextView.setVisibility(View.GONE);
                    }
                }else{
                    mEmptyStateTextView.setText(R.string.no_earthquakes);
                    mEmptyStateTextView.setVisibility(View.VISIBLE);
                }
                mEmptyStateImageView.setVisibility(View.GONE);
            }else {
                mEmptyStateTextView.setText(R.string.no_internet_connection);
                mEmptyStateTextView.setVisibility(View.VISIBLE);
                mEmptyStateImageView.setVisibility(View.VISIBLE);
            }
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
}
