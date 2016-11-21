package com.skynetprojectapp.android.skynetprojectapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Interpolator;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import static android.R.attr.key;


/**
 * This activity contains the scheduler. It is called room but it contains the interface where you can select the timeslots.
 * Created by Bruce
 */
public class roomsActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, TabHost.OnTabChangeListener {

    public TabHost host;
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

//        final Thread tabthread = new Thread() {
//            public void run() {

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

        host.setOnTabChangedListener(this);

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

    @Override
    public void onTabChanged(String tabId) {
        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(this);
        Intent i = new Intent("TAG_REFRESH");
        if ("T1".equals(tabId)) {
            mViewPager.setCurrentItem(0);
            i.putExtra("pos", 0);
        }
        if ("T2".equals(tabId)) {
            mViewPager.setCurrentItem(1);
            i.putExtra("pos", 1);
        }
        if ("T3".equals(tabId)) {
            mViewPager.setCurrentItem(2);
            i.putExtra("pos", 2);
        }
        if ("T4".equals(tabId)) {
            mViewPager.setCurrentItem(3);
            i.putExtra("pos", 3);
        }
        if ("T5".equals(tabId)) {
            mViewPager.setCurrentItem(4);
            i.putExtra("pos", 4);
        }
        if ("T6".equals(tabId)) {
            mViewPager.setCurrentItem(5);
            i.putExtra("pos", 5);
        }
        if ("T7".equals(tabId)) {
            mViewPager.setCurrentItem(6);
            i.putExtra("pos", 6);
        }

        lbm.sendBroadcast(i);


    }

    public static class FragMonday extends Fragment implements AdapterView.OnItemSelectedListener {
        private Spinner spinner;
        private static final String[] buildings = {"LB Building", "H Building", "VL Building", "B Building"};
        private TableLayout table;
        private HashMap<String, Timeslot> map;
        private HashMap<String, TableRow> mapRow;
        private HashMap<Integer, Integer> rooms;
        private HashMap<Integer,Integer> rowUroomID;
        private String building;
        private int dayPosition;
        private TextView textView;
        private MyReceiver r;
        private RoomsCatalog roomscat;



        public FragMonday() {
            map = new HashMap<String, Timeslot>();
            mapRow = new HashMap<String, TableRow>();
            rooms = new HashMap<Integer, Integer>();
            rowUroomID = new HashMap<Integer, Integer>();
            building = "LB-building";
            dayPosition = 0;
            roomscat = new RoomsCatalog();
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

            View rootView = inflater.inflate(R.layout.rooms_frag, container, false);

            for (int i = 1; i <= 20; i++) {
                for (int j = 7; j <= 23; j++) {
                    String key = i + "u" + j;
                    String id = "timeslot" + key;
                    int resID = getResources().getIdentifier(id, "id", getContext().getPackageName());
                    map.put(key, (Timeslot) rootView.findViewById(resID));
                }
            }


            spinner = (Spinner) rootView.findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, buildings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);
            spinner.setOnItemSelectedListener(this);

            textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText("");
            final ArrayList<Room> rooms = roomscat.getRoomList();
            Thread listeners = new Thread() {
                public void run() {
                    Iterator<String> keySetIterator = map.keySet().iterator();
                    while (keySetIterator.hasNext()) {
                        final String key = keySetIterator.next();
                        System.out.println("key: " + key + " value: " + map.get(key));
                        final String id = "timeslot" + key;
                        Timeslot temp = map.get(key);
                        temp.setIndex(id);
                        temp.setTimeSlotText(key);
                        temp.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Timeslot temp = (Timeslot) v;
                                temp.postInvalidate();
                                temp.setPassed(Color.GREEN);
                                String [] splitString = key.split("u");
                                int i = Integer.parseInt(splitString[0]);
                                Room room = roomscat.getRoom(rowUroomID.get(i));
                                Intent intent = new Intent(getActivity(), RoomDetailActivity.class);
                                intent.putExtra("Key", temp.getIndex());
                                intent.putExtra("RoomId", room.getRoomId());
                                intent.putExtra("RoomNumber",room.getRoomNumber());
                                intent.putExtra("RoomDescription",room.getDescription());
                                intent.putExtra("RoomSize",room.getRoomSize());
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

            roomMaps(building, rootView);

            setReservations(this.dayPosition);

            return rootView;
        }

        public int getRoomId(int rowNumber){



            for (int i = 1; i <= 55; i++){
                if(rooms.get(i) == rowNumber)
                    return i;
            }
            return 0;
        }

        private void setReservations(int dayPosition){
            ArrayList<ReservationObject> res = new ArrayList<ReservationObject>();
            switch (dayPosition) {
                case 0:
                    res = ReservationDayCatalog.getReservationsDayDB("sunday");
                    break;
                case 1:
                    res = ReservationDayCatalog.getReservationsDayDB("monday");
                    break;
                case 2:
                    res = ReservationDayCatalog.getReservationsDayDB("tuesday");
                    break;
                case 3:
                    res = ReservationDayCatalog.getReservationsDayDB("wednesday");
                    break;
                case 4:
                    res = ReservationDayCatalog.getReservationsDayDB("thursday");
                    break;
                case 5:
                    res = ReservationDayCatalog.getReservationsDayDB("friday");
                    break;
                case 6:
                    res = ReservationDayCatalog.getReservationsDayDB("saturday");
                    break;
            }

            if (res.size() != 0) {
                for (int i = 0; i < res.size(); i++) {
                    int row = rooms.get(res.get(i).getRoomId()); //get the row of the timeslot that is to be modified
                    String key = row + "u" + res.get(i).getStartTime();
                    Timeslot temp = map.get(key);
                    temp.setPassed(Color.BLUE);
                    temp.postInvalidate();
                    map.put(key, temp);

                }
            }
        }

        public void refresh(int daypos) {

            dayPosition = daypos;
            spinner.setSelection(0);
            roomMaps(building, getView());


            //textView.setText("");
            //refresh content
            Iterator<String> keySetIterator = map.keySet().iterator();
            while (keySetIterator.hasNext()) {
                String key = keySetIterator.next();
                Timeslot temp = map.get(key);
                temp.setPassed(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                temp.postInvalidate();
            }
            setReservations(daypos);
        }

        public void onPause() {
            super.onPause();
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(r);
        }

        public void onResume() {
            super.onResume();
            r = new MyReceiver();
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(r,
                    new IntentFilter("TAG_REFRESH"));
        }

        private class MyReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                FragMonday.this.refresh(intent.getExtras().getInt("pos"));
            }
        }


        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int position,
                                   long arg3) {
            for (int i = 1; i <= 20; i++) {
                mapRow.get((i) + "").setVisibility(View.GONE);
            }

            switch (position) {
                case 0:

                    roomMaps("LB-building", getView());
                    for (int i = 1; i <= 20; i++) {
                        mapRow.get((i) + "").setVisibility(View.VISIBLE);
                    }
                    for (int i = 13; i <= 20; i++) {
                        mapRow.get((i) + "").setVisibility(View.GONE);
                    }
                    break;
                case 1:
                    roomMaps("H-building", getView());
                    for (int i = 1; i <= 20; i++) {
                        mapRow.get((i) + "").setVisibility(View.VISIBLE);
                    }
                    break;
                case 2:

                    roomMaps("VL-building", getView());
                    for (int i = 1; i <= 20; i++) {
                        mapRow.get((i) + "").setVisibility(View.VISIBLE);
                    }
                    for (int i = 10; i <= 20; i++) {
                        mapRow.get((i) + "").setVisibility(View.GONE);
                    }

                    break;
                case 3:
                    roomMaps("B-building", getView());
                    for (int i = 1; i <= 20; i++) {
                        mapRow.get((i) + "").setVisibility(View.VISIBLE);
                    }
                    for (int i = 15; i <= 20; i++) {
                        mapRow.get((i) + "").setVisibility(View.GONE);
                    }
                    break;
            }
            Iterator<String> keySetIterator = map.keySet().iterator();
            while (keySetIterator.hasNext()) {
                String key = keySetIterator.next();
                Timeslot temp = map.get(key);
                temp.setPassed(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                temp.postInvalidate();
                }
            setReservations(dayPosition);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }

        private void roomMaps(String building, View v) {
            rowUroomID.clear();
            rooms.clear();

            ArrayList<Room> rms = roomscat.getRoomList();



            switch (building) {

                case "H-building":
                    int counterH = 1;
                    for (int i = 0; i < rms.size(); i++) {
                        if (rms.get(i).getRoomNumber().charAt(0) == 'H') {
                            rooms.put(rms.get(i).getRoomId(), counterH);
                            rowUroomID.put(counterH, rms.get(i).getRoomId());
                            String id = "rowtext" + counterH;
                            int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                            TextView rowText = (TextView) v.findViewById(resID);
                            rowText.setText(rms.get(i).getRoomNumber());
                            counterH++;
                        }
                    }
                    break;
                case "LB-building":
                    int counterLB = 1;
                    for (int i = 0; i < rms.size(); i++) {
                        if (rms.get(i).getRoomNumber().charAt(0) == 'L') {
                            rooms.put(rms.get(i).getRoomId(), counterLB);
                            rowUroomID.put(counterLB, rms.get(i).getRoomId());
                            String id = "rowtext" + counterLB;
                            int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                            TextView rowText = (TextView) v.findViewById(resID);
                            rowText.setText(rms.get(i).getRoomNumber());
                            counterLB++;
                        }
                    }
                    break;
                case "VL-building":
                    int counterVL = 1;
                    for (int i = 0; i < rms.size(); i++) {
                        if (rms.get(i).getRoomNumber().charAt(0) == 'V') {
                            rooms.put(rms.get(i).getRoomId(), counterVL);
                            rowUroomID.put(counterVL, rms.get(i).getRoomId());
                            String id = "rowtext" + counterVL;
                            int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                            TextView rowText = (TextView) v.findViewById(resID);
                            rowText.setText(rms.get(i).getRoomNumber());
                            counterVL++;
                        }
                    }
                    break;
                case "B-building":
                    int counterB = 1;
                    for (int i = 0; i < rms.size(); i++) {
                        if (rms.get(i).getRoomNumber().charAt(0) == 'B') {
                            rooms.put(rms.get(i).getRoomId(), counterB);
                            rowUroomID.put(counterB, rms.get(i).getRoomId());
                            String id = "rowtext" + counterB;
                            int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                            TextView rowText = (TextView) v.findViewById(resID);
                            rowText.setText(rms.get(i).getRoomNumber());
                            counterB++;
                        }
                    }
                    break;
            }
        }

    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return FragMonday.newInstance();
                case 1:
                    return FragMonday.newInstance();
                case 2:
                    return FragMonday.newInstance();
                case 3:
                    return FragMonday.newInstance();
                case 4:
                    return FragMonday.newInstance();
                case 5:
                    return FragMonday.newInstance();
                case 6:
                    return FragMonday.newInstance();
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

