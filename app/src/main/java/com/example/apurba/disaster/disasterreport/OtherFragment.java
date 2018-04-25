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
public class OtherFragment extends Fragment {
    private static final String TSUNAMI_URL = "https://www.google.com/search?ei=1qTgWs63GofgvgSYhZaQCA&q=disaster+list&oq=disaster+li&gs_l=psy-ab.3.0.0l10.2061.7332.0.8388.15.13.2.0.0.0.265.1817.0j8j2.10.0....0...1.1.64.psy-ab..4.11.1619...35i39k1j0i22i30k1j0i22i10i30k1j0i67k1j0i20i263k1.0.yZoAfC0YAzY";


    public OtherFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.others_fragment, container, false);

        return rootView;
    }

}
