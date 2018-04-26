package com.example.apurba.disaster.disasterreport;

/*
 * Created by Apurba on 3/12/2018.
 * disasterFragmentPagerAdapater:
 * set different disaster types in the view pager
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class disasterFragmentPagerAdapater extends FragmentPagerAdapter {

    private Context mContext;

    public disasterFragmentPagerAdapater(Context context, FragmentManager fm){
        super(fm);
        mContext = context;
    }

    /**
     * Returns new fragments to create depending on position of the tab layout
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new EarthQuakeFragment();
        }else if (position == 1){
            return new FloodFragment();
        }else if (position == 2){
            return new HurricanFragment();
        }else {
            return new OtherFragment();
        }
    }

    /**
     *  returns how many fragment to create
     */
    @Override
    public int getCount() {
        return 4;
    }

    /**
     * returns page tittle for each tab
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.category_earthquake);
        }else if (position == 1){
            return mContext.getString(R.string.category_floods);
        }else if(position == 2){
            return mContext.getString(R.string.category_hurricanes);
        }else {
            return mContext.getString(R.string.category_others);
        }
    }
}
