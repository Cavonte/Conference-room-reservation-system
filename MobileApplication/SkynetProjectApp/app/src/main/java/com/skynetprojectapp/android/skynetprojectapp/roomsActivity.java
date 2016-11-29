package com.skynetprojectapp.android.skynetprojectapp;


import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.Nullable;
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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
    private boolean fromEdit;
    private ReservationObject modifiedReservation;
    private NavigationView naview;
    private boolean sameDayAllowed;
    private ProgressDialog progressDialog;
    private int studentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rooms__drawer);

        fromEdit = getIntent().getBooleanExtra("fromEdit", false); //false is the default value
        modifiedReservation = (ReservationObject) getIntent().getSerializableExtra("reservation");
        ; // (Unserialized) reservation that is to be modified to be passed to the fragment


        studentId = getIntent().getIntExtra("studentId", 0);
        sameDayAllowed = getIntent().getBooleanExtra("sameDayAllowed", false);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Rooms");
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        naview = (NavigationView) findViewById(R.id.nav_view);
        naview.setNavigationItemSelectedListener(this);


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
//        DoCalculationTask task= new DoCalculationTask();
//        task.doInBackground();

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
        Navigation.navigate(id, roomsActivity.this, getIntent().getIntExtra("studentId", 0));
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


    public static class FragMonday extends Fragment implements AdapterView.OnItemSelectedListener, View.OnClickListener {
        private Spinner spinner;
        private static final String[] buildings = {"LB Building", "H Building", "VL Building", "B Building"};
        private TableLayout table;
        private HashMap<String, Timeslot> map;
        private HashMap<String, TableRow> mapRow;
        private HashMap<Integer, Integer> rooms;
        private HashMap<Integer, Integer> rowUroomID;
        private String building;
        private int dayPosition;
        private TextView textView;
        private MyReceiver r;
        private boolean fragfromedit, sameDayAllowed;
        private ReservationObject modifiedReservation;
        private RoomsCatalog roomscat;
        private ArrayList<ReservationObject> res;
        //private ReservationDayCatalog resCatalog;
        private int studentId;

        private static FragMonday fragMonday;
        private ProgressDialog progressDialog;


        //TODO: make studentId point to the user id that is currently logged in.
        public FragMonday() {
            map = new HashMap<String, Timeslot>();
            mapRow = new HashMap<String, TableRow>();
            rooms = new HashMap<Integer, Integer>();
            rowUroomID = new HashMap<Integer, Integer>();
            building = "LB-building";
            dayPosition = 0;
            //roomscat = new RoomsCatalog();
            //resCatalog = new ReservationDayCatalog();
        }

        public static FragMonday newInstance() {
            FragMonday fragment = new FragMonday();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        public static FragMonday newInstance(boolean fromEdit, boolean sameDayAllowed, ReservationObject reservationObject, int studentId) {
            FragMonday fragment = new FragMonday();
            Bundle args = new Bundle();
            args.putBoolean("sameDayAllowed", sameDayAllowed);
            args.putBoolean("fromEdit", fromEdit);
            args.putSerializable("reservation", reservationObject);
            args.putInt("studentId", studentId);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            progressDialog = ProgressDialog.show(getActivity(), null, "loading, please wait...");

            View rootView = inflater.inflate(R.layout.rooms_frag, container, false);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                fragfromedit = bundle.getBoolean("fromEdit", false);
                modifiedReservation = (ReservationObject) bundle.getSerializable("reservation");
                studentId = bundle.getInt("studentId");
                sameDayAllowed = bundle.getBoolean("sameDayAllowed", false);

            }
//            textView = (TextView) rootView.findViewById(R.id.section_label);
//            if(!(modifiedReservation==null)) textView.setText("This is " + modifiedReservation.getDay()); else textView.setText("Reconsider your life choices");

//            textView = (TextView) rootView.findViewById(R.id.section_label);
//            textView.setText();

            return rootView;
        }


        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);

            spinner = (Spinner) getView().findViewById(R.id.spinner);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, buildings);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setSelection(0);
            spinner.setOnItemSelectedListener(this);

            for (int i = 1; i <= 20; i++) {
                for (int j = 7; j <= 23; j++) {
                    String key = i + "u" + j;
                    String id = "timeslot" + key;
                    int resID = getResources().getIdentifier(id, "id", getContext().getPackageName());
                    Timeslot temp = (Timeslot) getView().findViewById(resID);
                    String[] splitString = key.split("u");
                    int start = Integer.parseInt(splitString[1]);
                    int row = Integer.parseInt(splitString[0]);
                    temp.setIndex(id);
                    temp.setRow(row);
                    temp.setStarttime(start);
                    temp.setOnClickListener(this);
                    map.put(key, temp);
                }
            }

//            final ArrayList<Room> rooms = RoomsCatalog.getRoomList();
//            Iterator<String> keySetIterator = map.keySet().iterator();
//            while (keySetIterator.hasNext()) {
//                final String key = keySetIterator.next();
//                //System.out.println("key: " + key + " value: " + map.get(key));
//                final String id = "timeslot" + key;
//                Timeslot temp = map.get(key);
//
//            }

            DoCalculationTask doCalculationTask = new DoCalculationTask();
            doCalculationTask.doInBackground();

            table = (TableLayout) getView().findViewById(R.id.table);
            for (int i = 1; i <= 20; i++) {
                String id = "row" + (i);
                int resID = getResources().getIdentifier(id, "id", getContext().getPackageName());
                mapRow.put((i) + "", (TableRow) getView().findViewById(resID));
            }
            progressDialog.dismiss();
        }

        @Override
        public void onClick(View v) {
            Timeslot temp = (Timeslot) v;
            temp.setAlpha((float) 0.8);
            // String[] splitString = key.split("u");
            // int i = Integer.parseInt(splitString[0]);
            Room room = RoomsCatalog.getRoom(rowUroomID.get(temp.getRow()));
            // ReservationObject res = ReservationDayCatalog.getRoomBasedOnReservation(room.getRoomId()+"u"+splitString[1]);
            ReservationObject res = ReservationDayCatalog.getRoomBasedOnReservation(room.getRoomId() + "u" + temp.getStartTime());

            Intent intent = new Intent(getActivity(), RoomDetailActivity.class);
            intent.putExtra("Key", temp.getIndex());
            intent.putExtra("fromEdit", fragfromedit);
            intent.putExtra("sameDayAllowed", sameDayAllowed);
            intent.putExtra("reservation", modifiedReservation);  //provide database with reservation that needs to be modified or canceled
            intent.putExtra("RoomId", room.getRoomId());
            intent.putExtra("RoomNumber", room.getRoomNumber());
            intent.putExtra("RoomDescription", room.getDescription());
            intent.putExtra("RoomSize", room.getRoomSize());
            intent.putExtra("Time", temp.getStartTime());

            //if the reservation exists then it will be passed to the intent
            if (res != null) {
                //you have to join the waitlist
                intent.putExtra("resId", res.getResId());
                intent.putExtra("studentId", studentId);
                intent.putExtra("day", res.getDay());
                intent.putExtra("startTime", res.getStartTime());
                intent.putExtra("endTime", res.getEndTime());
                intent.putExtra("position", res.getPosition());
                intent.putExtra("reservationServer", false);
            } else {
                //there are no reservation at this timeslot
                intent.putExtra("studentId", studentId);
                intent.putExtra("day", checkDayPosition(dayPosition));
                intent.putExtra("startTime", temp.getStartTime());
                intent.putExtra("endTime", temp.getStartTime() + 1);
                intent.putExtra("position", -1);
                intent.putExtra("reservationServer", true);
            }
            startActivity(intent);
            //refresh(dayPosition);
        }

        private class DoCalculationTask extends AsyncTask<Void, Void, Void> {

            @Override
            protected Void doInBackground(Void... arg0) {
                roomMaps(building, getView());
                resetTimeslots();
                setTimeslotStatus(dayPosition);
                return null;
            }

            //set the roomnumber in the individual timeslots
            protected void onPostExecute(Void result) {


            }
        }

        public int getRoomId(int rowNumber) {
            for (int i = 1; i <= 55; i++) {
                if (rooms.get(i) == rowNumber)
                    return i;
            }
            return 0;
        }


        private String checkDayPosition(int dayPosition) {
            String day = "";
            if (dayPosition == 0) {
                day = "Sunday";
            } else if (dayPosition == 1) {
                day = "Monday";
            } else if (dayPosition == 2) {
                day = "Tuesday";
            } else if (dayPosition == 3) {
                day = "Wednesday";
            } else if (dayPosition == 4) {
                day = "Thursday";
            } else if (dayPosition == 5) {
                day = "Friday";
            } else if (dayPosition == 6) {
                day = "Saturday";
            }
            return day;
        }

        //this method set the color of the timeslots based on if they taken or not. A server call is made through the  Reservation day Catalot static class then the timeslots are modified based on their availability
        public void refresh(int daypos) {
            dayPosition = daypos;
            spinner.setSelection(0);
            //roomMaps(building, getView());
            //resetTimeslots();
            //setTimeslotStatus(daypos);
            DoCalculationTask doCalculationTask = new DoCalculationTask();
            doCalculationTask.doInBackground();
        }

        private void roomMaps(String building, View v) {
            rowUroomID.clear();
            rooms.clear();

            ArrayList<Room> rms = RoomsCatalog.getRoomList();

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
            setRoomNumbers();
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

        //this method allows the fragment to be refreshed. I.e. the timeslots needs to be recolored based on the color that is selected. Due to the static nature of the fragmen a local broadcast manager is needed to handle this change
        private class MyReceiver extends BroadcastReceiver {
            @Override
            public void onReceive(Context context, Intent intent) {
                FragMonday.this.refresh(intent.getExtras().getInt("pos"));
            }
        }

        //this method reset the color of the timelots for later reuse.
        private void resetTimeslots() {
            Iterator<String> keySetIterator = map.keySet().iterator();
            while (keySetIterator.hasNext()) {
                String key = keySetIterator.next();
                Timeslot temp = map.get(key);
                temp.setPassed(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                //temp.setPassed(Color.GRAY);
                temp.postInvalidate();
            }
        }

        //this method set the room number in each individual timeslot
        private void setRoomNumbers() {
            final ArrayList<Room> rooms = RoomsCatalog.getRoomList();
            Iterator<String> iteratorRoomNumber = map.keySet().iterator();

            while (iteratorRoomNumber.hasNext()) {
                final String key = iteratorRoomNumber.next();
                //System.out.println("key: " + key + " value: " + map.get(key));
                final String id = "timeslot" + key;
                Timeslot temp = map.get(key);
                temp.setIndex(id);
                String[] splitString = key.split("u");
                int j = Integer.parseInt(splitString[0]);
                if (rowUroomID.containsKey(j)) {
                    Room room2 = RoomsCatalog.getRoom(rowUroomID.get(j));
                    temp.setTimeSlotText(room2.getRoomNumber());
                    temp.postInvalidate();
                }
            }
        }

        private void setTimeslotStatus(int dayPosition) {
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
            if (res == null) {
                Toast.makeText(getContext(), "Might want to check yer connection there mate.", Toast.LENGTH_LONG);
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

            Calendar calendar = Calendar.getInstance();
            int day = calendar.get(Calendar.DAY_OF_WEEK);
            int hour= calendar.get(Calendar.HOUR_OF_DAY);

            if((dayPosition+1)<= day){ //if the day is passed
                Iterator<String> keySetIterator = map.keySet().iterator();
                while (keySetIterator.hasNext()) {
                    String key = keySetIterator.next();
                    Timeslot temp = map.get(key);
                    //temp.setPassed(ContextCompat.getColor(getContext(), R.color.colorPrimary));
                    if(temp.getStartTime()<(hour)) {
                        temp.setPassed(Color.GRAY);
                        temp.postInvalidate();
                    }
                }
            }

//            switch (day) {
//                case Calendar.SUNDAY:
//                    break;
//                case Calendar.MONDAY:
//                    break;
//
//                case Calendar.TUESDAY:
//                    break;
//                case Calendar.WEDNESDAY:
//                    break;
//
//                case Calendar.THURSDAY:
//                    break;
//
//                case Calendar.FRIDAY:
//                    break;
//
//                case Calendar.SATURDAY:
//                    break;
//
//            }
        }


        //Listeners

        //this method render the  the rows visible/invisible based on the amount that need to be displayed. For smaller building like the Vl building for instance there will be only 9 rows becasue rooms.
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
//            Iterator<String> keySetIterator = map.keySet().iterator();
//            while (keySetIterator.hasNext()) {
//                String key = keySetIterator.next();
//                Timeslot temp = map.get(key);
//                temp.setPassed(ContextCompat.getColor(getContext(), R.color.colorPrimary));
//                temp.postInvalidate();
//            }
            resetTimeslots();
            setTimeslotStatus(dayPosition);
        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
        //populates the room based on the building that was selected. this method  will be called usually in the refresh method or a the creation of the framgent.


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
            return FragMonday.newInstance(fromEdit, sameDayAllowed, modifiedReservation, studentId);
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

