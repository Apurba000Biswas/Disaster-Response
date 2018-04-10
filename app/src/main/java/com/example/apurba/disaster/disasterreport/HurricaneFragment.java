package com.example.apurba.disaster.disasterreport;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HurricaneFragment extends Fragment {



    public HurricaneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.hurricane_fragment, container, false);

        ProgressBar loading_indicator = rootView.findViewById(R.id.loading_spinner);

        WebView wv = (WebView) rootView.findViewById(R.id.webView);
        wv.setWebViewClient(new MyBrowser(loading_indicator));
        wv.getSettings().setLoadsImagesAutomatically(true);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        wv.loadUrl("http://www.thehurricanetracker.org/live-tracker");


        return rootView;
    }

    private class MyBrowser extends WebViewClient {
        private ProgressBar loading_indicator;

        public MyBrowser(ProgressBar progressBar){
            this.loading_indicator=progressBar;
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            loading_indicator.setVisibility(View.GONE);
        }
    }

}
