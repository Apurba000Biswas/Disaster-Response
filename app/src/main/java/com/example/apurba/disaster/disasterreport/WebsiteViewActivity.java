package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 4/21/2018.
 *
 *  WebsiteViewActivity:
 *  creates a website view activity
 */

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
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

        // receive intent and with its all possible extra messages
        Intent intent = getIntent();
        String urlForEarthquake = intent.getStringExtra(EarthQuakeFragment.EXTRA_MESSAGE_3);
        String urlForSelectedEarthQuake = intent.getStringExtra(EarthQuakeDetailsActivity.EXTRA_MESSAGE);
        String title = intent.getStringExtra(EarthQuakeDetailsActivity.EXTRA_MESSAGE2);
        String urlForFlood = intent.getStringExtra(FloodFragment.EXTRA_MESSAGE9);

        String urlForTsunami = intent.getStringExtra(OtherFragment.EXTRA_MESSAGE1);
        String urlForAvalanches = intent.getStringExtra(OtherFragment.EXTRA_MESSAGE2);
        String urlForWildFire = intent.getStringExtra(OtherFragment.EXTRA_MESSAGE3);
        String urlForDrought = intent.getStringExtra(OtherFragment.EXTRA_MESSAGE4);
        String urlForTornado = intent.getStringExtra(OtherFragment.EXTRA_MESSAGE5);

        // finds which activity sends the intent to open a website
        final String mUrl = getValidUrl(
                urlForEarthquake,
                urlForFlood,
                urlForSelectedEarthQuake,
                urlForTsunami,
                urlForAvalanches,
                urlForWildFire,
                urlForDrought,
                urlForTornado,
                title);

        ProgressBar loading_indicator = findViewById(R.id.loading_spinner);
        if (isConnectedToInternet()){
            // if connected then load the website
            startWebView(mUrl, loading_indicator);
        }else{
            // not connected then show empty view
            loading_indicator.setVisibility(View.GONE);
            TextView emptyState = findViewById(R.id.empty_view);
            emptyState.setText(R.string.no_internet_connection);
        }
        // Setup FAB to open Website in a browser
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mUrl));
                startActivity(intent);
            }
        });
    }

    /**
     * find out which activity sends the intent and return its corresponding
     * valid url. Also set up the bar tittle
     */
    private String getValidUrl(String urlForEarthquake,
                               String urlForFlood,
                               String urlForSelectedEarthQuake,
                               String urlForTsunami,
                               String urlForAvalanches,
                               String urlForWildFire,
                               String urlForDrought,
                               String urlForTornado,
                               String title){
        if (!TextUtils.isEmpty(urlForEarthquake)){
            setbarStyle("Earthquakes");
            return urlForEarthquake;
        }else if (!TextUtils.isEmpty(urlForFlood)){
            setbarStyle("Floods");
            return urlForFlood;
        }else if (!TextUtils.isEmpty(urlForTsunami)){
            setbarStyle("Tsunami");
            return urlForTsunami;
        }else if(!TextUtils.isEmpty(urlForAvalanches)){
            setbarStyle("Avalanches");
            return urlForAvalanches;
        }else if (!TextUtils.isEmpty(urlForWildFire)){
            setbarStyle("Wild Fire");
            return urlForWildFire;
        }else if (!TextUtils.isEmpty(urlForDrought)){
            setbarStyle("Drought");
            return urlForDrought;
        }else if (!TextUtils.isEmpty(urlForTornado)){
            setbarStyle("Tornado");
            return urlForTornado;
        } else {
            setbarStyle(title);
            return urlForSelectedEarthQuake ;
        }
    }

    /**
     * set up customized bar style
     */
    private void setbarStyle(String title){

        // make custome tittle
        SpannableString s = new SpannableString(title);
        s.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.appLevel)),
                0, title.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        getSupportActionBar().setTitle(s);

        // make custom barstyle
        final Drawable upArrow = getResources().getDrawable(R.drawable.back_arrow);
        upArrow.setColorFilter(getResources().getColor(R.color.appLevel), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);
    }

    // creates a browser and load the url
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
