package com.example.apurba.disaster.disasterreport;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtherFragment extends Fragment {
    private static final String TSUNAMI_URL = "https://www.tsunami.gov/";
    private static final String AVALANCHES_URL = "https://www.theguardian.com/world/avalanches";
    private static final String WILDFIRE_URL = "https://www.theguardian.com/world/wildfires";
    private static final String DROUGHT_URL = "http://www.bbc.com/news/topics/c8z0lk3537et/drought";
    private static final String TORNADO_URL = "https://weather.com/storms/tornado";


    public static final String EXTRA_MESSAGE1 = "tsunami";
    public static final String EXTRA_MESSAGE2 = "avalanches";
    public static final String EXTRA_MESSAGE3 = "wildfire";
    public static final String EXTRA_MESSAGE4 = "drought";
    public static final String EXTRA_MESSAGE5 = "tornado";


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

        setOnclickWebViewActivity(tsunamiCardView, 1);
        setOnclickWebViewActivity(avalanchesCardView, 2);
        setOnclickWebViewActivity(wildFireCardView,3);
        setOnclickWebViewActivity(droughtCardView,4);
        setOnclickWebViewActivity(tornadoCardView,5);


        return rootView;
    }

    private void setOnclickWebViewActivity(final CardView cardView , int key){
        final int mKey = key;
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), WebsiteViewActivity.class);
                switch (mKey){
                    case 1:
                        intent.putExtra(EXTRA_MESSAGE1, TSUNAMI_URL);
                        break;
                    case 2:
                        intent.putExtra(EXTRA_MESSAGE2, AVALANCHES_URL);
                        break;
                    case 3:
                        intent.putExtra(EXTRA_MESSAGE3, WILDFIRE_URL);
                        break;
                    case 4:
                        intent.putExtra(EXTRA_MESSAGE4, DROUGHT_URL);
                        break;
                    case 5:
                        intent.putExtra(EXTRA_MESSAGE5, TORNADO_URL);
                        break;
                    default:
                }
                startActivity(intent);
            }

        });
    }

}
