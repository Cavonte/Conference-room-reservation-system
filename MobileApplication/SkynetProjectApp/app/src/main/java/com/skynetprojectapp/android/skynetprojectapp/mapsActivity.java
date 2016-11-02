package com.skynetprojectapp.android.skynetprojectapp;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.Toast;
import android.view.Menu;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import android.widget.TextView;

public class mapsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabHost host;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Maps");
        setSupportActionBar(toolbar);

        DrawerLayout drawerM = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggleM = new ActionBarDrawerToggle(
                this, drawerM, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerM.addDrawerListener(toggleM);
        toggleM.syncState();

        NavigationView view = (NavigationView) findViewById(R.id.nav_view);
        view.setNavigationItemSelectedListener(this);

        host = (TabHost) findViewById(R.id.mapTabs);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("T1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("B1");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("T2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("B2");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("T3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("B3");
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("T4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("B4");
        host.addTab(spec);

        //Tab 5
        spec = host.newTabSpec("T5");
        spec.setContent(R.id.tab5);
        spec.setIndicator("B5");
        host.addTab(spec);

        //Tab 6
        spec = host.newTabSpec("T6");
        spec.setContent(R.id.tab6);
        spec.setIndicator("B6");
        host.addTab(spec);

        host.setOnTabChangedListener(mapsActivity.this);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(mapsActivity.this);



    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
       }

    @Override
    public void onPageSelected(int position) {
        host.setCurrentTab(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @Override
    public void onTabChanged(String tabId) {
        if ("T1".equals(tabId)) {
            mViewPager.setCurrentItem(0);
        }
        if ("T2".equals(tabId)) {
            mViewPager.setCurrentItem(1);
        }
        if ("T3".equals(tabId)) {
            mViewPager.setCurrentItem(2);
        }
        if ("T4".equals(tabId)) {
            mViewPager.setCurrentItem(3);
        }
        if ("T5".equals(tabId)) {
            mViewPager.setCurrentItem(4);
        }
        if ("T6".equals(tabId)) {
            mViewPager.setCurrentItem(5);

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_maps, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            ImageView map = (ImageView) rootView.findViewById(R.id.cmap);
            switch (getArguments().getInt(ARG_SECTION_NUMBER)) {
                case 1:
                    map.setImageResource(R.drawable.lb3);
                case 2:
                    map.setImageResource(R.drawable.lb3);
                case 3:
                    map.setImageResource(R.drawable.logo);
                case 4:
                    map.setImageResource(R.drawable.lb3);
                case 5:
                    map.setImageResource(R.drawable.lb3);
                case 6:
                    map.setImageResource(R.drawable.lb3);
            }
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            //Total pages count.
            return 6;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Map 1";
                case 1:
                    return "Map 2";
                case 2:
                    return "Map 3";
                case 4:
                    return "Map 4";
                case 5:
                    return "Map 5";
                case 6:
                    return "Map 6";
            }
            return null;
        }

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_Reservations) {
            Toast.makeText(this, "preferencesActivity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mapsActivity.this, mainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Rooms) {
            startActivity(new Intent(mapsActivity.this, roomsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Map) {
            startActivity(new Intent(mapsActivity.this, mapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_preferences) {
            startActivity(new Intent(mapsActivity.this, preferencesActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_About) {
            startActivity(new Intent(mapsActivity.this, aboutActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Help) {
            startActivity(new Intent(mapsActivity.this, helpActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Log_out) {
            startActivity(new Intent(mapsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
