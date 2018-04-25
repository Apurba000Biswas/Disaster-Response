package com.example.apurba.disaster.disasterreport;


import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {
    private static final String TSUNAMI_URL = "";
    private static final String AVALANCHES_URL = "";
    private static final String WILDFIRE_URL = "";
    private static final String DROUGHT_URL = "";
    private static final String TORNADO_URL = "";

    public OtherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.others_fragment, container, false);

        CardView tsunamiCardView = rootView.findViewById(R.id.tsunami_card);
        CardView avalanchesCardView = rootView.findViewById(R.id.avalanches_card);
        CardView wildFireCardView = rootView.findViewById(R.id.wildfire_card);
        CardView droughtCardView = rootView.findViewById(R.id.drought_card);
        CardView tornadoCardView = rootView.findViewById(R.id.tornado_card);

        startWebViewActivity(tsunamiCardView,TSUNAMI_URL);
        startWebViewActivity(avalanchesCardView,AVALANCHES_URL);
        startWebViewActivity(wildFireCardView,WILDFIRE_URL);
        startWebViewActivity(droughtCardView,DROUGHT_URL);
        startWebViewActivity(tornadoCardView,TORNADO_URL);


        /**
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
         **/

        return rootView;
    }

    private void startWebViewActivity(CardView cardView, String url){
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "Ok i am on" ,Toast.LENGTH_SHORT).show();
            }
        });
    }

}
