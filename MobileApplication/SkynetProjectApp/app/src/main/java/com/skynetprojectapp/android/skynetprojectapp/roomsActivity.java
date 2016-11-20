package com.skynetprojectapp.android.skynetprojectapp;


import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TextView;

import android.view.View;
import android.widget.*;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Iterator;


/**
 * This activity contains the scheduler. It is called room but it contains the interface where you can select the timeslots.
 * Created by Bruce
 */
public class roomsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private TabHost host;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ActionBarDrawerToggle toggle;
    private NavigationView naview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms__drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rooms");
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        naview = (NavigationView) findViewById(R.id.nav_view);
        naview.setNavigationItemSelectedListener(this);

        final Thread tabthread = new Thread() {
            public void run() {

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


                host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {

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
                });

//                Retrieve all the rooms from backend
//            final String url = "http://192.168.2.15:8080/rooms";
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            //Must be the room objects.
//            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class);
//            Object[] objects = responseEntity.getBody();


                // Create the adapter that will return a fragment for each of the three
                // primary sections of the activity.

                mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
                mViewPager = (ViewPager) findViewById(R.id.container);
                mViewPager.setAdapter(mSectionsPagerAdapter);
                mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    public void onPageScrollStateChanged(int state) {
                    }

                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    public void onPageSelected(int position) {
                        host.setCurrentTab(position);
                    }
                });

            }
        };
        tabthread.start();


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

    public static class FragSunday extends Fragment implements AdapterView.OnItemSelectedListener {
        private Spinner spinner;
        private static final String[] buildings = {"LB Building", "H Building", "VL Building", "B Building"};
        private TableRow row, row1;


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
            spinner.setOnItemSelectedListener(this);

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


    }

    public static class FragMonday extends Fragment implements AdapterView.OnItemSelectedListener {
        private Spinner spinner;
        private static final String[] buildings = {"LB Building", "H Building", "VL Building", "B Building"};
        private TableLayout table;
        HashMap<String, Timeslot> map;
        HashMap<String, TableRow> mapRow;
        HashMap<Integer, Integer> rooms;
        private String building;

        public FragMonday() {
            map = new HashMap<String, Timeslot>();
            mapRow = new HashMap<String, TableRow>();
            rooms = new HashMap<Integer, Integer>();
            building = "LB-building";
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
            final View rootView = inflater.inflate(R.layout.rooms_frag, container, false);
            for (int i = 1; i <= 20; i++) {
                for (int j = 7; j <= 23; j++) {
                    String key = i + "u" + j;
                    String id = "timeslot" + key;
                    int resID = getResources().getIdentifier(id, "id", getContext().getPackageName());
                    map.put(key, (Timeslot) rootView.findViewById(resID));
                }
            }

            spinner = (Spinner) rootView.findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity().getApplicationContext(), android.R.layout.simple_spinner_item, buildings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);
            spinner.setOnItemSelectedListener(this);

            Thread listeners = new Thread() {
                public void run() {
                    Iterator<String> keySetIterator = map.keySet().iterator();
                    while (keySetIterator.hasNext()) {
                        final String key = keySetIterator.next();
                        System.out.println("key: " + key + " value: " + map.get(key));
                        final String id = "timeslot" + key;
                        Timeslot temp = map.get(key);
                        temp.setIndex(id);
                        temp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Timeslot temp = (Timeslot) v;
                                temp.setPassed(Color.GREEN);
                                temp.postInvalidate();
                                Intent intent = new Intent(getActivity(), RoomDetailActivity.class);
                                intent.putExtra("Key", temp.getIndex());
                                startActivity(intent);
                            }
                        });
                    }
                }
            };
            listeners.start();
//

            table = (TableLayout) rootView.findViewById(R.id.table);
            for (int i = 1; i <= 20; i++) {
                String id = "row" + (i);
                int resID = getResources().getIdentifier(id, "id", getContext().getPackageName());
                mapRow.put((i) + "", (TableRow) rootView.findViewById(resID));
            }

            rooms= roomMaps(building, rootView,rooms);

//            roomMaps("LB-building",rootView);

//            //Retrieve all the reservations from backend
//            final String url = "http://192.168.2.15:8080/dailyReservations?weekDay=monday";
//            RestTemplate restTemplate = new RestTemplate();
//            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//            //Must be the reservations objects.
//            ResponseEntity<Object[]> responseEntity = restTemplate.getForEntity(url, Object[].class);
//            Object[] objects = responseEntity.getBody();

            ReservationObject[] res = new ReservationObject[5];
            res[0] = new ReservationObject(1, 1, 1, "Monday", 12, 13, 0);
            res[1] = new ReservationObject(2, 2, 3, "Monday", 12, 13, 1);
            res[2] = new ReservationObject(3, 3, 4, "Monday", 12, 13, 2);
            res[3] = new ReservationObject(4, 2, 5, "Monday", 17, 16, 3);
            res[4] = new ReservationObject(5, 4, 9, "Monday", 12, 13, 4);


            for (int i = 0; i < res.length; i++) {
                int row = rooms.get(res[i].getRoomId()); //get the row of the timeslot that is to be modified
                String key = row + "u" + res[i].getStartTime();
                Timeslot temp = map.get(key);
                temp.setPassed(Color.RED);
                temp.postInvalidate();
                map.put(key, temp);

            }
            return rootView;
        }

        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {
            for (int i = 1; i <= 20; i++) {
                mapRow.get((i) + "").setVisibility(View.GONE);
            }
            switch (position) {
                case 0:
                    roomMaps("LB-building", getView(),rooms);
                    for (int i = 1; i <= 12; i++) {
                        mapRow.get((i) + "").setVisibility(View.VISIBLE);
                    }
//                    for (int i = 13; i <= 20; i++) {
//                        mapRow.get((i) + "").setVisibility(View.GONE);
//                    }
                    break;
                case 1:
                    roomMaps("H-building", getView(),rooms);
                    for (int i = 1; i <= 20; i++) {
                        mapRow.get((i) + "").setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:
                    roomMaps("VL-building", getView(),rooms);
                    for (int i = 1; i <= 9; i++) {
                        mapRow.get((i) + "").setVisibility(View.VISIBLE);
                    }
//                    for (int i = 10; i <= 20; i++) {
//                        mapRow.get((i) + "").setVisibility(View.GONE);
//                    }

                    break;
                case 3:
                    roomMaps("B-building", getView(),rooms);
                    for (int i = 1; i <= 14; i++) {
                        mapRow.get((i) + "").setVisibility(View.VISIBLE);
                    }
//                    for (int i = 15; i <= 20; i++) {
//                        mapRow.get((i) + "").setVisibility(View.GONE);
//                    }

                    break;
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
//            for (int i = 0; i < 56; i++) {
//                mapRow.get((i + 1) + "").setVisibility(View.GONE);
//            }
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
            View rootView = inflater.inflate(R.layout.content_room_detail, container, false);
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
                    notifyDataSetChanged();
                    return FragSunday.newInstance();
                case 1:
                    notifyDataSetChanged();
                    return FragMonday.newInstance();
                case 2:
                    notifyDataSetChanged();
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


    private static HashMap<Integer, Integer> roomMaps(String building, View v, HashMap<Integer, Integer> rooms) {
        rooms.clear();
        Room[] rms = new Room[20];
        rms[0] = new Room(1, "LH-234", "NOTHING TO DESCRIBE", 4);
        rms[1] = new Room(2, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[2] = new Room(3, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[3] = new Room(4, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[4] = new Room(5, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[5] = new Room(6, "LH-234", "NOTHING TO DESCRIBE", 4);
        rms[6] = new Room(7, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[7] = new Room(8, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[8] = new Room(9, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[9] = new Room(10, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[10] = new Room(11, "LH-234", "NOTHING TO DESCRIBE", 4);
        rms[11] = new Room(12, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[12] = new Room(13, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[13] = new Room(14, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[14] = new Room(15, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[15] = new Room(16, "LH-234", "NOTHING TO DESCRIBE", 4);
        rms[16] = new Room(17, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[17] = new Room(18, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[18] = new Room(19, "LH-244", "NOTHING TO DESCRIBE2", 3);
        rms[19] = new Room(20, "LH-244", "NOTHING TO DESCRIBE2", 3);

        switch (building) {

            case "H-building":
                for (int i = 0; i < rms.length; i++) {
                    if (rms[i].getRoomNumber().charAt(0) == 'H') {
                        rooms.put(rms[i].getRoomId(), i + 1);
                        String id = "rowtext" + (i + 1);
                        int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                        TextView rowText = (TextView) v.findViewById(resID);
                        rowText.setText(rms[i].getRoomNumber());
                    }
                }
                break;
            case "LB-building":
                for (int i = 0; i < rms.length; i++) {
                    if (rms[i].getRoomNumber().charAt(0) == 'L') {
                        rooms.put(rms[i].getRoomId(), i + 1);
                        String id = "rowtext" + (i + 1);
                        int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                        TextView rowText = (TextView) v.findViewById(resID);
                        rowText.setText(rms[i].getRoomNumber());
                    }
                }
                break;
            case "VL-building":
                for (int i = 0; i < rms.length; i++) {
                    if (rms[i].getRoomNumber().charAt(0) == 'V') {
                        rooms.put(rms[i].getRoomId(), i + 1);
                        String id = "rowtext" + (i + 1);
                        int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                        TextView rowText = (TextView) v.findViewById(resID);
                        rowText.setText(rms[i].getRoomNumber());
                    }
                }
                break;
            case "B-building":
                for (int i = 0; i < rms.length; i++) {
                    if (rms[i].getRoomNumber().charAt(0) == 'B') {
                        rooms.put(rms[i].getRoomId(), i + 1);
                        String id = "rowtext" + (i + 1);
                        int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                        TextView rowText = (TextView) v.findViewById(resID);
                        rowText.setText(rms[i].getRoomNumber());
                    }
                }
                break;
        }
        return rooms;
    }


}

