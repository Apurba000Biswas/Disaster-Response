package com.example.apurba.disaster.disasterreport;

/** disasterFragmentPagerAdapter class
 *
 * A simple {@link android.support.v4.app.FragmentPagerAdapter} subclass.
 * Created by Apurba on 3/12/2018.
 * creates four diiferent fragment :
 * 1. EarthQuakeFragment
 * 2. FloodFragment
 * 3. HurricanFragment
 * 4. OtherFragment
 * and attach them in four differnt positions with apropriate Tittle
 */

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class disasterFragmentPagerAdapter extends FragmentPagerAdapter {

    // requested activity
    private Context mContext;

    /* constructor:
     * Takes activity as Context and FragmentManager
     * then initialize them
     */
    public disasterFragmentPagerAdapter(Context context, FragmentManager fm){
        super(fm);
        mContext = context;
    }

    /** public Fragment getItem() method
     * Returns fragments for specific position
     */
    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new EarthQuakeFragment();
        }else if (position == 1){
            return new StatisticsFragment();
        }else if (position == 2){
            return new FloodFragment();
        }else {
            return new OtherFragment();
        }
    }

    /** getCount() method
     *  returns how many fragment to create for this viewPager
     */
    @Override
    public int getCount() {
        return 4;
    }

    /** getPageTitle() method
     *  returns page tittle for each tab and fragment
     */
    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0){
            return mContext.getString(R.string.category_earthquake);
        }else if (position == 1){
            return mContext.getString(R.string.category_statistics);
        }else if(position == 2){
            return mContext.getString(R.string.category_floods);
        }else {
            return mContext.getString(R.string.category_others);
        }
    }
}
