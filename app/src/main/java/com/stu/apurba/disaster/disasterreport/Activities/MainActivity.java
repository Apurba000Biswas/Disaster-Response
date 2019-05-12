
package com.stu.apurba.disaster.disasterreport.Activities;
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
 * This project is Directly supported from Udacity-course project "Quake-report"
 * Quake-report link: https://github.com/udacity/ud843-QuakeReport
 *
 * Its an open source project. If you want to contribute,
 * please fork the repository from GitHub.
 * Repository link: https://github.com/Apurba000Biswas/Disaster-Response
 *
 *
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

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.stu.apurba.disaster.disasterreport.R;
import com.stu.apurba.disaster.disasterreport.Adapter.disasterFragmentPagerAdapter;

/** MainActivity class:
 * created by Apurba Biswas
 *
 * A simple {@link AppCompatActivity} subclass.
 * App starts here. It triggers the UI animation with its Tab and
 * add a viewPager to load each tab
 */
public class MainActivity extends AppCompatActivity {

    private static final int DIALOG_ABOUT_US = 0;

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
        disasterFragmentPagerAdapter adapter =
                new disasterFragmentPagerAdapter(MainActivity.this, getSupportFragmentManager());
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        CollapsingToolbarLayout collapsingToolbar = findViewById(R.id.collapse_toolbar);
        collapsingToolbar.setTitle(getString(R.string.app_tittle));
        collapsingToolbar.setCollapsedTitleTextColor(getResources().getColor(R.color.appLevel));
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
     * When sittings menu is clicked this method gets called and it takes a MenuItem
     * then it check for appropriate option and then triggers it, returns true if successful
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this,
                        SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.about_us:
                showDialog(DIALOG_ABOUT_US);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        LayoutInflater factory = LayoutInflater.from(this);

        switch (id){
            case DIALOG_ABOUT_US:
                final View aboutView = factory.inflate(R.layout.about_us, null);
                return createInfoDialog(aboutView);
        }

        return null;
    }

    private Dialog createInfoDialog(View aboutView){
        TextView support = aboutView.findViewById(R.id.support);
        setWebsiteToView(support, getString(R.string.uda_quake_report));

        TextView projectTypeInfo = aboutView.findViewById(R.id.project_info);
        setWebsiteToView(projectTypeInfo, getString(R.string.bubtWebsiteHomePage));

        TextView superVisorInfo = aboutView.findViewById(R.id.supervisor_details);
        setEmailToSend(superVisorInfo, getString(R.string.projectSuperEmail));

        TextView firstDevInfo = aboutView.findViewById(R.id.first_developer);
        setEmailToSend(firstDevInfo, getString(R.string.firstDeveloperEmail));


        TextView sourceCodeLink = aboutView.findViewById(R.id.source_code);
        setWebsiteToView(sourceCodeLink, getString(R.string.sourceCodeLink));

        return new AlertDialog.Builder(this, R.style.myDialogTheme)
                .setIcon(R.mipmap.ic_launcher_round)
                .setTitle(R.string.app_tittle)
                .setView(aboutView)
                .setPositiveButton("OK", null)
                .create();
    }

    private void setEmailToSend(TextView textView, final String emailAddress){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailAddress});
                try {
                    startActivity(Intent.createChooser(intent, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getApplicationContext(),
                            "There are no email clients installed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setWebsiteToView(View textView, final String url){
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(url));
                startActivity(intent);
            }
        });
    }
}
