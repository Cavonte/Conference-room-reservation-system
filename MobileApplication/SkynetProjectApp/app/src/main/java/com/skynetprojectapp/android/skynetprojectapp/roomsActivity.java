package com.skynetprojectapp.android.skynetprojectapp;


import android.content.Intent;
import android.graphics.Color;
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
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TextView;

import android.view.View;
import android.widget.*;


/**
 * This activity contains the scheduler. It is called room but it contains the interface where you can select the timeslots.
 * Created by Bruce
 */
public class roomsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {

    private TabHost host;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms__drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rooms");
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView view = (NavigationView) findViewById(R.id.nav_view);
        view.setNavigationItemSelectedListener(this);

        host = (TabHost) findViewById(R.id.roomTabs);
        host.setup();

        //Tab 1
        TabHost.TabSpec spec = host.newTabSpec("T1");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Su");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("T2");
        spec.setContent(R.id.tab2);
        spec.setIndicator("M");
        host.addTab(spec);

        //Tab 3
        spec = host.newTabSpec("T3");
        spec.setContent(R.id.tab3);
        spec.setIndicator("Tu");
        host.addTab(spec);

        //Tab 4
        spec = host.newTabSpec("T4");
        spec.setContent(R.id.tab4);
        spec.setIndicator("W");
        host.addTab(spec);

        //Tab 5
        spec = host.newTabSpec("T5");
        spec.setContent(R.id.tab5);
        spec.setIndicator("Th");
        host.addTab(spec);

        //Tab 6
        spec = host.newTabSpec("T6");
        spec.setContent(R.id.tab6);
        spec.setIndicator("F");
        host.addTab(spec);

        //Tab 7
        spec = host.newTabSpec("T7");
        spec.setContent(R.id.tab7);
        spec.setIndicator("Sa");
        host.addTab(spec);

        host.setOnTabChangedListener(roomsActivity.this);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(this);


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
        if ("T7".equals(tabId)) {
            mViewPager.setCurrentItem(6);
        }
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_Reservations) {
            Toast.makeText(this, "preferencesActivity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(roomsActivity.this, mainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Rooms) {
            startActivity(new Intent(roomsActivity.this, roomsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Map) {
            startActivity(new Intent(roomsActivity.this, mapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_preferences) {
            startActivity(new Intent(roomsActivity.this, preferencesActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_About) {
            startActivity(new Intent(roomsActivity.this, aboutActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Help) {
            startActivity(new Intent(roomsActivity.this, helpActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Log_out) {
            startActivity(new Intent(roomsActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public static class FragSunday extends Fragment implements View.OnClickListener, AdapterView.OnItemSelectedListener {
        private Spinner spinner;
        private static final String[] buildings = {"LB Building", "H Building", "VL Building", "B Building"};
        private TableRow row, row1;
        private Timeslot t021, t031, t041, t051;
        public FragSunday() {
        }

        public static FragSunday newInstance() {
            FragSunday fragment = new FragSunday();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.sunday_frag, container, false); //inflate the layout

            //Adapter
            spinner = (Spinner) rootView.findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, buildings);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);//
            row = (TableRow) rootView.findViewById(R.id.row2);
            row1 = (TableRow) rootView.findViewById(R.id.row3);

            spinner.setOnItemSelectedListener(this);

            t021 = (Timeslot) rootView.findViewById(R.id.timeslot021);
            t021.setOnClickListener(this);
            t021.setPassed(Color.GRAY);

            t031 = (Timeslot) rootView.findViewById(R.id.timeslot031);
            t031.setOnClickListener(this);
            t031.setPassed(Color.GRAY);

            t041 = (Timeslot) rootView.findViewById(R.id.timeslot041);
            t041.setOnClickListener(this);
            t041.setPassed(Color.GRAY);

            t051 = (Timeslot) rootView.findViewById(R.id.timeslot051);
            t051.setOnClickListener(this);
            t051.setPassed(Color.GRAY);


            return rootView;
        }


        // beginning of the listeners
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {
            switch (position) {
                case 0:
                    break;
                case 1:
                    row1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    row.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    row.setVisibility(View.GONE);
                    row1.setVisibility(View.GONE);
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            //optionally do something here
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.timeslot91:
                   // t91.setPassed(Color.BLUE);
                    //t91.postInvalidate();
                    Toast.makeText(getActivity(), "Opening reservation framgment and subsequent server calls", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.timeslot92:
                   // t92.setPassed(Color.BLUE);
                    //t92.postInvalidate();
                    Toast.makeText(getActivity(), "Opening reservation framgment and subsequent server calls", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.timeslot93:
                    //t93.setPassed(Color.BLUE);
                    //t93.postInvalidate();
                    Toast.makeText(getActivity(), "Opening reservation framgment and subsequent server calls", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.timeslot94:
                    //t94.setPassed(Color.BLUE);
                    //t94.postInvalidate();
                    Toast.makeText(getActivity(), "Opening reservation framgment and subsequent server calls", Toast.LENGTH_SHORT).show();
                    break;
            }

        }

        //end listeners

    }

    public static class FragMonday extends Fragment  implements AdapterView.OnItemSelectedListener , View.OnClickListener {
        private Spinner spinner;
        private static final String[] buildings = {"LB Building", "H Building", "VL Building", "B Building"};
        private TableRow row, row1;
        private Timeslot t91, t92, t93, t94;

        public FragMonday() {
        }

        public static FragMonday newInstance() {
            FragMonday fragment = new FragMonday();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {


            View rootView = inflater.inflate(R.layout.monday_frag, container, false);
            spinner = (Spinner) rootView.findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, buildings);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);//
            row = (TableRow) rootView.findViewById(R.id.row2);
            row1 = (TableRow) rootView.findViewById(R.id.row3);

            spinner.setOnItemSelectedListener(this);

            t91 = (Timeslot) rootView.findViewById(R.id.timeslot91);
            t91.setOnClickListener(this);
            t91.setPassed(Color.GRAY);

            t92 = (Timeslot) rootView.findViewById(R.id.timeslot92);
            t92.setOnClickListener(this);
            t92.setPassed(Color.GRAY);

            t93 = (Timeslot) rootView.findViewById(R.id.timeslot93);
            t93.setOnClickListener(this);
            t93.setPassed(Color.GRAY);

            t94 = (Timeslot) rootView.findViewById(R.id.timeslot94);
            t94.setOnClickListener(this);
            t94.setPassed(Color.GRAY);

            return rootView;
        }

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {
            switch (position) {
                case 0:
                    row.setVisibility(View.GONE);
                    row1.setVisibility(View.GONE);
                    break;
                case 1:
                    row1.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    row.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
            //optionally do something here
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.timeslot91:
                    t91.setPassed(Color.BLUE);
                    t91.postInvalidate();
                    Toast.makeText(getActivity(), "Opening reservation framgment and subsequent server calls", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.timeslot92:
                    t92.setPassed(Color.BLUE);
                    t92.postInvalidate();
                    Toast.makeText(getActivity(), "Opening reservation framgment and subsequent server calls", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.timeslot93:
                    t93.setPassed(Color.BLUE);
                    t93.postInvalidate();
                    Toast.makeText(getActivity(), "Opening reservation framgment and subsequent server calls", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.timeslot94:
                    t94.setPassed(Color.BLUE);
                    t94.postInvalidate();
                    Toast.makeText(getActivity(), "Opening reservation framgment and subsequent server calls", Toast.LENGTH_SHORT).show();
                    break;
            }

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
            View rootView = inflater.inflate(R.layout.tuesday_frag, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Tuesday");
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
            View rootView = inflater.inflate(R.layout.wednesday_frag, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Wednesday");
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
            View rootView = inflater.inflate(R.layout.thursday_frag, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Thursday");
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
            View rootView = inflater.inflate(R.layout.friday_frag, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Friday");
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
            View rootView = inflater.inflate(R.layout.saturday_frag, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("Saturday");
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
                    return FragSunday.newInstance();
                case 1:
                    return FragMonday.newInstance();
                case 2:
                    return Frag2.newInstance();
                case 3:
                    return Frag3.newInstance();
                case 4:
                    return Frag4.newInstance();
                case 5:
                    return Frag5.newInstance();
                case 6:
                    return Frag6.newInstance();
            }
            return null;
        }

        @Override
        public int getCount() {
            //Total pages count.
            return 7;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Sunday";
                case 1:
                    return "Monday";
                case 2:
                    return "Tuesday";
                case 3:
                    return "Wednesday";
                case 4:
                    return "Thursday";
                case 5:
                    return "Friday";
                case 6:
                    return "Saturday";
            }
            return null;
        }


    }

}

