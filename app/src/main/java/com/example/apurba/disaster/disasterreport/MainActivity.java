
package com.example.apurba.disaster.disasterreport;
/** -------------DISASTER REPORT APP---------------------
 *
 * Group :
 *      Apurba Biswas
 *      id: 12132103082
 *      Faysal Ahmed
 *      id: 13143103041
 *      Mst.Nur Jamila Siddika
 *      id: 13143103095
 * Bangladesh University of Business and Technology (BUBT)
 * Mirpur, Dhaka, Bangladesh
 *
 * This app represents the most recent Earthquake and flood data
 * And it also offers to see other disasters on the website.
 *
 * This project is partially copied from Udacity-course project "Quake-report"
 *
 * References: -------------------------------------------
 * Udacity: https://www.udacity.com/
 * Google: https://www.google.com/
 * Android developer: https://developer.android.com/
 * Stanford(CS-106A): http://web.stanford.edu/class/cs106a/
 * Java: https://www.oracle.com/index.html
 * StackOverflow: https://stackoverflow.com/
 * Facebook: https://www.facebook.com/
 * Book: The Art and Science of Java
 * Book: Java Programming
 */

import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

/** MainActivity class:
 * created by Apurba Biswas
 *
 * A simple {@link AppCompatActivity} subclass.
 * App starts here. It triggers the UI animation with its Tab and
 * add a viewPager to load each tab
 */
public class MainActivity extends AppCompatActivity {

    /** onCreate() method
     * After starting app, this method gets called to create the views
     * This method creates views by attaching the "activity_main.xml" file
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setAppBar();

        ViewPager viewPager = findViewById(R.id.viewpager);
        disasterFragmentPagerAdapter adapter = new disasterFragmentPagerAdapter(MainActivity.this, getSupportFragmentManager());
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    /** private void setAppBar() method
     *  set up the custom app bar
     */
    private void setAppBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // disable the up arrow of the toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle(getString(R.string.app_tittle));
    }


    /** public boolean onCreateOptionsMenu() method
     * creates sittings option on the top right corner
     * returns ture if after cretaing the menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    /** public boolean onOptionsItemSelected() method
     * When sittings menue is clicked this method gets called and it takes a MenueItem
     * then it check for appropriate option and then triggers it, returns true if successfull
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
