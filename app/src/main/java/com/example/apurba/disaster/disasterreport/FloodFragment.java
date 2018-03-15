package com.example.apurba.disaster.disasterreport;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;
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
    private String LOG_TAG = FloodFragment.class.getSimpleName();

    private TextView mEmptyStateTextView;
    private View loading_indicator;
    private static final String URL = "https://earthquake.usgs.gov/fdsnws/event/1/query";
    private FloodItemAdapter mAdapter;
    private GridView gridview;


    public FloodFragment() {
        // Required empty public constructor
    }

    /*---------------------------------------------------------------Methods---------------------------------------------------------------*/
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.flood_fragment, container, false);

        List<FloodItem> floods = new ArrayList<FloodItem>();
        //floods.add(new FloodItem(3, "Flood Alert", "Maxico"));

        gridview = (GridView) rootView.findViewById(R.id.gridview);
        mAdapter = new FloodItemAdapter(getActivity(), floods);

        mEmptyStateTextView = (TextView)rootView.findViewById(R.id.empty_view);
        gridview.setEmptyView(mEmptyStateTextView);
        loading_indicator = rootView.findViewById(R.id.loading_spinner);

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                Toast.makeText(getActivity(), "" + position,
                        Toast.LENGTH_SHORT).show();
            }
        });

        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null, FloodFragment.this).forceLoad();

        return rootView;
    }

    @Override
    public Loader<List<FloodItem>> onCreateLoader(int id, Bundle args) {
        return new FloodLoader(getActivity(),URL);
    }

    @Override
    public void onLoadFinished(Loader<List<FloodItem>> loader, List<FloodItem> flood) {
        loading_indicator.setVisibility(View.GONE);
        mAdapter.clear();

        if (flood != null && !flood.isEmpty()) {
            mAdapter.addAll(flood);
            gridview.setAdapter(mAdapter);
        }
        mEmptyStateTextView.setText(R.string.no_floods);

    }

    @Override
    public void onLoaderReset(Loader<List<FloodItem>> loader) {

    }
}
