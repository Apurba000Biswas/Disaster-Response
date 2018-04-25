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
public class HurricaneFragment extends Fragment{
    private static final String HURRICANE_URL = "https://www.weatherbug.com/alerts/hurricane/";
    private WebView mWebView ;

    public HurricaneFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hurricane_fragment, container, false);

        ProgressBar loading_indicator = rootView.findViewById(R.id.loading_spinner);

        if (isConnectedToInternet()){
            WebView wv = rootView.findViewById(R.id.webView);
            wv.setWebViewClient(new MyBrowser(loading_indicator));
            wv.getSettings().setLoadsImagesAutomatically(true);
            wv.getSettings().setJavaScriptEnabled(true);
            wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            wv.loadUrl(HURRICANE_URL);
        }else{
            loading_indicator.setVisibility(View.GONE);
            TextView emptyState = rootView.findViewById(R.id.empty_view);
            emptyState.setText(R.string.no_internet_connection);
        }

        // Setup FAB to open Website View Activity
        FloatingActionButton fab = rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(HURRICANE_URL));
                    startActivity(intent);
                }catch (ActivityNotFoundException e){
                    Toast.makeText(getContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

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
     * Creates a new browser to load the web pages
     */
    private class MyBrowser extends WebViewClient {
        private ProgressBar loading_indicator;

        public MyBrowser(ProgressBar progressBar){
            this.loading_indicator = progressBar;
            loading_indicator.setVisibility(View.VISIBLE);
        }
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (loading_indicator != null) loading_indicator.setVisibility(View.GONE);
        }
    }
}
