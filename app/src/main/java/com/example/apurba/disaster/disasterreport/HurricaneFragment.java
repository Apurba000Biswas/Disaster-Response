package com.example.apurba.disaster.disasterreport;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.support.v4.app.LoaderManager;

/**
 * A simple {@link Fragment} subclass.
 */
public class HurricaneFragment extends Fragment implements LoaderManager.LoaderCallbacks<WebView>{
    private static final String HURRICANE_URL = "http://www.myfoxhurricane.com";
    private WebView mWebView ;

    public HurricaneFragment() {
        // Required empty public constructor
    }

    @Override
    public Loader<WebView> onCreateLoader(int id, Bundle args) {
        mWebView.setWebViewClient(new MyBrowser());
        mWebView.getSettings().setLoadsImagesAutomatically(true);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mWebView.loadUrl(HURRICANE_URL);
        return null;
    }

    @Override
    public void onLoadFinished(Loader<WebView> loader, WebView data) {

    }

    @Override
    public void onLoaderReset(Loader<WebView> loader) {

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
        public MyBrowser(){
            loading_indicator = null;
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
