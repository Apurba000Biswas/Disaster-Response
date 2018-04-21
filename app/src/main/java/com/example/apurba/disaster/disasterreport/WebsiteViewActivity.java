package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 4/21/2018.
 */

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WebsiteViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.website_activity);

        Intent intent = getIntent();
        final String urlForEarthquake = intent.getStringExtra(EarthQuakeFragment.EXTRA_MESSAGE_3);
        final String urlForSelectedEarthQuake = intent.getStringExtra(EarthQuakeDetailsActivity.EXTRA_MESSAGE);
        final String title = intent.getStringExtra(EarthQuakeDetailsActivity.EXTRA_MESSAGE2);
        final String urlForFlood = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE9);

        ProgressBar loading_indicator = findViewById(R.id.loading_spinner);
        if (isConnectedToInternet()){
            if (!TextUtils.isEmpty(urlForEarthquake)){
                setTitle("Earthquakes");
                startWebView(urlForEarthquake, loading_indicator);
            }else if (!TextUtils.isEmpty(urlForFlood)){
                setTitle("Floods");
                startWebView(urlForFlood, loading_indicator);
            }else{
                setTitle(title);
                startWebView(urlForSelectedEarthQuake, loading_indicator);
            }
        }else{
            loading_indicator.setVisibility(View.GONE);
            TextView emptyState = findViewById(R.id.empty_view);
            emptyState.setText(R.string.no_internet_connection);
        }
    }

    private void startWebView(String url, ProgressBar loading_indicator){
        WebView wv = findViewById(R.id.webView);
        wv.setWebViewClient(new MyBrowser(loading_indicator));
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setBuiltInZoomControls(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.loadUrl(url);
    }

    /**
     * Check for internet connection
     */
    private boolean isConnectedToInternet(){
        ConnectivityManager cm = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
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
