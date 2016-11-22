package com.skynetprojectapp.android.skynetprojectapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

/**
 * This activity contains the interface that allows you look at the different maps.
 * Created by Bruce
 */

public class mapsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;


    private ViewPager mViewPager;
    private TabHost host;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

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
        spec.setIndicator("Capstone");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("T2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("GN");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("T3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("LB 3rd F");
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("T4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("LB 4rd F");
        host.addTab(spec);

        //Tab 5
        spec = host.newTabSpec("T5");
        spec.setContent(R.id.tab5);
        spec.setIndicator("LB 5rd F");
        host.addTab(spec);

        //Tab 6
        spec = host.newTabSpec("T6");
        spec.setContent(R.id.tab6);
        spec.setIndicator("VL 3rd F");
        host.addTab(spec);

        host.setOnTabChangedListener(mapsActivity.this);


        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(mapsActivity.this);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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


    public static class Frag1 extends Fragment {
        public Frag1() {
        }

        public static Frag1 newInstance() {
            Frag1 fragment = new Frag1();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maps, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Hi Frag1");
            return rootView;
        }
    }

    public static class Frag2 extends Fragment {
        public Frag2() {
        }

        public static Frag2 newInstance() {
            Frag2 fragment = new Frag2();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maps2, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Hi Frag2");
            return rootView;
        }
    }

    public static class Frag3 extends Fragment {
        public Frag3() {
        }

        public static Frag3 newInstance() {
            Frag3 fragment = new Frag3();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maps3, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Hi Frag3");
            return rootView;
        }
    }

    public static class Frag4 extends Fragment {
        public Frag4() {
        }

        public static Frag4 newInstance() {
            Frag4 fragment = new Frag4();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maps4, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Hi Frag4");
            return rootView;
        }
    }


    public static class Frag5 extends Fragment {
        public Frag5() {
        }

        public static Frag5 newInstance() {
            Frag5 fragment = new Frag5();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maps5, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Hi Frag5");
            return rootView;
        }
    }

    public static class Frag6 extends Fragment {
        public Frag6() {
        }

        public static Frag6 newInstance() {
            Frag6 fragment = new Frag6();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_maps6, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Hi Frag6");
            return rootView;
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return Frag1.newInstance();
                case 1:
                    return Frag2.newInstance();
                case 2:
                    return Frag3.newInstance();
                case 3:
                    return Frag4.newInstance();
                case 4:
                    return Frag5.newInstance();
                case 5:
                    return Frag6.newInstance();
            }
            return null;
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
        Navigation.navigate(id,mapsActivity.this,getIntent().getIntExtra("studentId",0));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
