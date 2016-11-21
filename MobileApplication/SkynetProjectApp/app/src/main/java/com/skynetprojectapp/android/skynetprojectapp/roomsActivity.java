package com.skynetprojectapp.android.skynetprojectapp;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
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
    private boolean fromEdit;
    private ReservationObject modifiedReservation;
    private NavigationView naview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms__drawer);

        fromEdit = getIntent().getBooleanExtra("fromEdit", false); //false is the default value
        modifiedReservation= (ReservationObject) getIntent().getSerializableExtra("reservation");; // (Unserialized) reservation that is to be modified to be passed to the fragment


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
        Navigation.navigate(id, roomsActivity.this);
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
        private String building;
        private int dayPosition;
        private TextView textView;
        private MyReceiver r;
        private boolean fragfromedit;
        private ReservationObject modifiedReservation;


        public FragMonday() {
            map = new HashMap<String, Timeslot>();
            mapRow = new HashMap<String, TableRow>();
            rooms = new HashMap<Integer, Integer>();
            building = "LB-building";
            dayPosition = 0;
        }

        public static FragMonday newInstance() {
            FragMonday fragment = new FragMonday();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        public static FragMonday newInstance(boolean fromEdit,ReservationObject reservationObject) {
            FragMonday fragment = new FragMonday();
            Bundle args = new Bundle();
            args.putBoolean("fromEdit", fromEdit);
            args.putSerializable("reservation",reservationObject);
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

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                fragfromedit = bundle.getBoolean("fromEdit", false);
                modifiedReservation= (ReservationObject) bundle.getSerializable("reservation");
            }

            textView = (TextView) rootView.findViewById(R.id.section_label);
            if(!(modifiedReservation==null)) textView.setText("This is " + modifiedReservation.getDay()); else textView.setText("Reconsider your life choices");




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
                                temp.setPassed(Color.GREEN);
                                temp.postInvalidate();
                                Intent intent = new Intent(getActivity(), RoomDetailActivity.class);
                                intent.putExtra("Key", temp.getIndex());
                                intent.putExtra("fromEdit",fragfromedit );
                                intent.putExtra("reservation",modifiedReservation);  //provide database with reservation that needs to be modified or canceled


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


        private void setReservations(int dayPosition) {
            ArrayList<ReservationObject> res = new ArrayList<ReservationObject>();
            switch (dayPosition) {
                case 0:
                    res.add(new ReservationObject(1, 1, 1, "Sunday", 12, 13, 0));
                    res.add(new ReservationObject(2, 2, 3, "Sunday", 12, 13, 1));
                    res.add(new ReservationObject(3, 3, 4, "Sunday", 12, 13, 2));
                    break;
                case 1:
                    res.add(new ReservationObject(4, 1, 1, "Monday", 12, 13, 0));
                    res.add(new ReservationObject(5, 2, 3, "Monday", 12, 13, 1));
                    res.add(new ReservationObject(6, 3, 4, "Monday", 12, 13, 2));
                    res.add(new ReservationObject(7, 2, 5, "Monday", 17, 16, 3));
                    res.add(new ReservationObject(8, 4, 9, "Monday", 12, 13, 4));
                    res.add(new ReservationObject(4, 11, 1, "Monday", 12, 13, 0));
                    res.add(new ReservationObject(5, 12, 3, "Monday", 12, 13, 1));
                    res.add(new ReservationObject(6, 13, 4, "Monday", 12, 13, 2));
                    res.add(new ReservationObject(7, 12, 5, "Monday", 17, 16, 3));
                    res.add(new ReservationObject(8, 14, 9, "Monday", 12, 13, 4));
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
            }

            if (res.size() != 0) {
                for (int i = 0; i < res.size(); i++) {
                    if (!(rooms.get(res.get(i).getRoomId()) == null)) {
                        int row = rooms.get(res.get(i).getRoomId());
                        //get the row of the timeslot that is to be modified
                        String key = row + "u" + res.get(i).getStartTime();
                        Timeslot temp = map.get(key);
                        temp.setPassed(Color.BLUE);
                        temp.postInvalidate();
                        map.put(key, temp);
                    }


                }
            }
        }

        public void refresh(int daypos) {
            dayPosition = daypos;
            spinner.setSelection(0);
            roomMaps(building, getView());
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
            rooms.clear();
            Room[] rms = new Room[20];

            rms[0] = new Room(1, "LB-234", "NOTHING TO DESCRIBE", 4);
            rms[1] = new Room(2, "LB-244", "NOTHING TO DESCRIBE2", 3);
            rms[2] = new Room(3, "LB-244", "NOTHING TO DESCRIBE2", 3);
            rms[3] = new Room(4, "LB-244", "NOTHING TO DESCRIBE2", 3);
            rms[4] = new Room(5, "LB-244", "NOTHING TO DESCRIBE2", 3);
            rms[5] = new Room(6, "LB-234", "NOTHING TO DESCRIBE", 4);
            rms[6] = new Room(7, "LB-244", "NOTHING TO DESCRIBE2", 3);
            rms[7] = new Room(8, "LB-244", "NOTHING TO DESCRIBE2", 3);
            rms[8] = new Room(9, "LB-244", "NOTHING TO DESCRIBE2", 3);
            rms[9] = new Room(10, "H-244", "NOTHING TO DESCRIBE2", 3);
            rms[10] = new Room(11, "H-234", "NOTHING TO DESCRIBE", 4);
            rms[11] = new Room(12, "H-244", "NOTHING TO DESCRIBE2", 3);
            rms[12] = new Room(13, "H-244", "NOTHING TO DESCRIBE2", 3);
            rms[13] = new Room(14, "H-244", "NOTHING TO DESCRIBE2", 3);
            rms[14] = new Room(15, "B-244", "NOTHING TO DESCRIBE2", 3);
            rms[15] = new Room(16, "B-234", "NOTHING TO DESCRIBE", 4);
            rms[16] = new Room(17, "B-244", "NOTHING TO DESCRIBE2", 3);
            rms[17] = new Room(18, "B-244", "NOTHING TO DESCRIBE2", 3);
            rms[18] = new Room(19, "VL-244", "NOTHING TO DESCRIBE2", 3);
            rms[19] = new Room(20, "VL-244", "NOTHING TO DESCRIBE2", 3);

            switch (building) {

                case "H-building":
                    for (int i = 0; i < rms.length; i++) {
                        if (rms[i].getRoomNumber().charAt(0) == 'H') {
                            rooms.put(rms[i].getRoomId(), (i + 1));
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
                            rooms.put(rms[i].getRoomId(), (i + 1));
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
                            rooms.put(rms[i].getRoomId(), (i + 1));
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
                            rooms.put(rms[i].getRoomId(), (i + 1));
                            String id = "rowtext" + (i + 1);
                            int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                            TextView rowText = (TextView) v.findViewById(resID);
                            rowText.setText(rms[i].getRoomNumber());
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
                    return FragMonday.newInstance(fromEdit,modifiedReservation);
                case 1:
                    return FragMonday.newInstance(fromEdit,modifiedReservation);
                case 2:
                    return FragMonday.newInstance(fromEdit,modifiedReservation);
                case 3:
                    return FragMonday.newInstance(fromEdit,modifiedReservation);
                case 4:
                    return FragMonday.newInstance(fromEdit,modifiedReservation);
                case 5:
                    return FragMonday.newInstance(fromEdit,modifiedReservation);
                case 6:
                    return FragMonday.newInstance(fromEdit,modifiedReservation);
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
        ArrayList<Room> rms = RoomsCatalog.getRoomsFromDB();



        switch (building) {

            case "H-building":
                for (int i = 0; i < rms.size(); i++) {
                    if (rms.get(i).getRoomNumber().charAt(0) == 'H') {
                        rooms.put(rms.get(i).getRoomId(), i + 1);
                        String id = "rowtext" + (i + 1);
                        int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                        TextView rowText = (TextView) v.findViewById(resID);
                        rowText.setText(rms.get(i).getRoomNumber());
                    }
                }
                break;
            case "LB-building":
                for (int i = 0; i < rms.size(); i++) {
                    if (rms.get(i).getRoomNumber().charAt(0) == 'L') {
                        rooms.put(rms.get(i).getRoomId(), i + 1);
                        String id = "rowtext" + (i + 1);
                        int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                        TextView rowText = (TextView) v.findViewById(resID);
                        rowText.setText(rms.get(i).getRoomNumber());
                    }
                }
                break;
            case "VL-building":
                for (int i = 0; i < rms.size(); i++) {
                    if (rms.get(i).getRoomNumber().charAt(0) == 'V') {
                        rooms.put(rms.get(i).getRoomId(), i + 1);
                        String id = "rowtext" + (i + 1);
                        int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                        TextView rowText = (TextView) v.findViewById(resID);
                        rowText.setText(rms.get(i).getRoomNumber());
                    }
                }
                break;
            case "B-building":
                for (int i = 0; i < rms.size(); i++) {
                    if (rms.get(i).getRoomNumber().charAt(0) == 'B') {
                        rooms.put(rms.get(i).getRoomId(), i + 1);
                        String id = "rowtext" + (i + 1);
                        int resID = v.getResources().getIdentifier(id, "id", v.getContext().getPackageName());
                        TextView rowText = (TextView) v.findViewById(resID);
                        rowText.setText(rms.get(i).getRoomNumber());
                    }
                }
                break;
        }
        return rooms;
    }

}

