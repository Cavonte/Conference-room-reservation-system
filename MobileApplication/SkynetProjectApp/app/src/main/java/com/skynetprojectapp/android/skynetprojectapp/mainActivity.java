package com.skynetprojectapp.android.skynetprojectapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;


/**
 * This activity is the reservation pages where the current reservations  are being displayed.
 * Created by Bruce
 */
public class mainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Button reserve;
    private ImageButton del1, del2, del3, ed1, ed2, ed3, refresh;
    private Reservation r1, r2, r3;
    private Reservation[] arrReservationsView;
    private AlertDialog alertDialog;
    private ReservationObject[] reservationObjects;
    private int amountOfReservation;
    private RoomsCatalog rc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Reservations");
        setSupportActionBar(toolbar);

        rc = new RoomsCatalog();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        r1 = (Reservation) findViewById(R.id.r1);
        r1.setVisibility(View.GONE);
        // r1.setOnClickListener(mainActivity.this);

        r2 = (Reservation) findViewById(R.id.r2);
        r2.setVisibility(View.GONE);


        r3 = (Reservation) findViewById(R.id.r3);
        r3.setVisibility(View.GONE);

        reserve = (Button) findViewById(R.id.reserveroom);
        reserve.setOnClickListener(mainActivity.this);

        del1 = (ImageButton) findViewById(R.id.delres1);
        del1.setOnClickListener(mainActivity.this);
        del1.setVisibility(View.GONE);
        del2 = (ImageButton) findViewById(R.id.delres2);
        del2.setOnClickListener(mainActivity.this);
        del2.setVisibility(View.GONE);
        del3 = (ImageButton) findViewById(R.id.delres3);
        del3.setOnClickListener(mainActivity.this);
        del3.setVisibility(View.GONE);

        ed1 = (ImageButton) findViewById(R.id.editres1);
        ed1.setOnClickListener(mainActivity.this);
        ed1.setVisibility(View.GONE);
        ed2 = (ImageButton) findViewById(R.id.editres2);
        ed2.setOnClickListener(mainActivity.this);
        ed2.setVisibility(View.GONE);
        ed3 = (ImageButton) findViewById(R.id.editres3);
        ed3.setOnClickListener(mainActivity.this);
        ed3.setVisibility(View.GONE);

        refresh = (ImageButton) findViewById(R.id.refresh);
        refresh.setOnClickListener(this);

        requestReservationList(27526711);
        arrReservationsView = new Reservation[3];
        int counter = 1;
        for (int i = 0; i < reservationObjects.length; i++) {
            if (reservationObjects[i] != null && reservationObjects[i].getPosition() == 0 && i < arrReservationsView.length) {
                String id = "r" + (counter);
                int resID = getResources().getIdentifier(id, "id", getPackageName());
                arrReservationsView[i] = (Reservation) findViewById(resID);
                // arrReservationsView[i] = (Reservation) findViewById(R.id.reservation+i);
                if (!(arrReservationsView[i] == null)) {
                    arrReservationsView[i].setRoomNumber(reservationObjects[i].getRoomId() + "");
                    arrReservationsView[i].setDay(reservationObjects[i].getDay());
                    arrReservationsView[i].setResI(reservationObjects[i].getResId());
                    Room room = rc.getRoom((reservationObjects[i].getRoomId()));
                    if (!(room == null)) {
                        arrReservationsView[i].setLocation(room.getRoomNumber());
                    }
                    arrReservationsView[i].setHours(reservationObjects[i].getStartTime() + ":00 to " + reservationObjects[i].getEndTime() + ":00 ");
                    arrReservationsView[i].setVisibility(View.VISIBLE);
                    String del = "delres" + (counter);
                    int delID = getResources().getIdentifier(del, "id", getPackageName());
                    findViewById(delID).setVisibility(View.VISIBLE);
                    String edit = "editres" + (counter);
                    int ediID = getResources().getIdentifier(edit, "id", getPackageName());
                    findViewById(ediID).setVisibility(View.VISIBLE);
                }
                counter++;
            }


        }
        amountOfReservation = reservationObjects.length;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.refresh:
                //refresh the reservations and their data. For now they are back to visible, but they should be visible or  not based on the user reservation
                for (int j = 0; j < reservationObjects.length; j++) {
                    reservationObjects[j] = null;
                }
                for (int k = 0; k < arrReservationsView.length; k++) {
                    arrReservationsView[k] = null;
                }
                r1.setVisibility(View.GONE);
                r2.setVisibility(View.GONE);
                r3.setVisibility(View.GONE);

                del1.setVisibility(View.GONE);
                del2.setVisibility(View.GONE);
                del3.setVisibility(View.GONE);

                ed1 = (ImageButton) findViewById(R.id.editres1);
                ed1.setVisibility(View.GONE);
                ed2.setVisibility(View.GONE);
                ed3.setVisibility(View.GONE);

                requestReservationList(27526711);
//                = new Reservation[reservationObjects.length];
                int counter = 1;
                for (int i = 0; i < reservationObjects.length; i++) {
                    if (reservationObjects[i] != null && reservationObjects[i].getPosition() == 0 && i < arrReservationsView.length) {
                        String id = "r" + (counter);
                        int resID = getResources().getIdentifier(id, "id", getPackageName());
                        arrReservationsView[i] = (Reservation) findViewById(resID);
                        arrReservationsView[i].setVisibility(View.VISIBLE);
                        String del = "delres" + (counter);
                        int delID = getResources().getIdentifier(del, "id", getPackageName());
                        findViewById(delID).setVisibility(View.VISIBLE);
                        String edit = "editres" + (counter);
                        int ediID = getResources().getIdentifier(edit, "id", getPackageName());
                        findViewById(ediID).setVisibility(View.VISIBLE);
                        // arrReservationsView[i] = (Reservation) findViewById(R.id.reservation+i);

                        if (!(arrReservationsView[i] == null)) {
                            arrReservationsView[i].setRoomNumber(reservationObjects[i].getRoomId() + "");
                            arrReservationsView[i].setDay(reservationObjects[i].getDay());
                            arrReservationsView[i].setResI(reservationObjects[i].getResId());
                            Room room = rc.getRoom((reservationObjects[i].getRoomId()));
                            if (!(room == null)) {
                                arrReservationsView[i].setLocation(room.getRoomNumber());
                            }
                            arrReservationsView[i].setHours(reservationObjects[i].getStartTime() + ":00 to " + reservationObjects[i].getEndTime() + ":00 ");
                        }
                        counter++;
                    }

                }
                amountOfReservation = reservationObjects.length;
                break;

            case R.id.reserveroom:
                if (amountOfReservation == 3) {
                    Toast.makeText(mainActivity.this, "Leave some for the others", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mainActivity.this, "RoomsActivity", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.delres1:
                alert("delete reservation", 1, arrReservationsView[0].getResI());
                break;
            case R.id.editres1:
                Toast.makeText(mainActivity.this, "Edit res 1", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mainActivity.this, roomsActivity.class);
                i.putExtra("fromEdit", true); //tell the room activity that we are modifying
                if (!(reservationObjects[0] == null))
                    i.putExtra("reservation", reservationObjects[0]);
                else i.putExtra("reservation", new ReservationObject(4, 1, 1, "Monday", 12, 13, 0));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                break;

            case R.id.delres2:
                alert("delete reservation", 2, arrReservationsView[1].getResI());
                break;
            case R.id.editres2:
                Toast.makeText(mainActivity.this, "Edit res 2", Toast.LENGTH_SHORT).show();
                Intent k = new Intent(mainActivity.this, roomsActivity.class);
                k.putExtra("fromEdit", true); //tell the room activity that we are modifying
                if (!(reservationObjects[1] == null))
                    k.putExtra("reservation", reservationObjects[1]);
                else
                    k.putExtra("reservation", new ReservationObject(4, 1, 1, "Tuesday", 12, 13, 0));
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(k);
                break;

            case R.id.editres3:
                Toast.makeText(mainActivity.this, "Edit res 3", Toast.LENGTH_SHORT).show();
                Intent j = new Intent(mainActivity.this, roomsActivity.class);
                j.putExtra("fromEdit", true); //tell the room activity that we are modifying
                if (!(reservationObjects[2] == null))
                    j.putExtra("reservation", reservationObjects[2]);
                else
                    j.putExtra("reservation", new ReservationObject(4, 1, 1, "Wednesday", 12, 13, 0));
                j.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(j);
                break;
            case R.id.delres3:
                alert("delete reservation", 3, arrReservationsView[2].getResI());
                break;

        }
    }

    private void alert(String msg, int del, final int reservationId) {
        final int but = del;
        alertDialog = new AlertDialog.Builder(mainActivity.this).create();
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Are you sure you want to " + msg + " ?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mainActivity.this, "YES", Toast.LENGTH_LONG).show();
                requestDeleteReservation(27526711, reservationId);
                if (but == 1) {
                    r1.setVisibility(View.GONE);
                    del1.setVisibility(View.GONE);
                    ed1.setVisibility(View.GONE);
                    amountOfReservation--;
                    reservationObjects[0] = null;
                }
                if (but == 2) {
                    r2.setVisibility(View.GONE);
                    del2.setVisibility(View.GONE);
                    ed2.setVisibility(View.GONE);
                    amountOfReservation--;
                    reservationObjects[1] = null;
                }
                if (but == 3) {
                    r3.setVisibility(View.GONE);
                    del3.setVisibility(View.GONE);
                    ed3.setVisibility(View.GONE);
                    amountOfReservation--;
                    reservationObjects[2] = null;
                }
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mainActivity.this, "Please Reconsider your life choices", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();
    }

    private void requestReservationList(int studentId) {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String url = "http://" + IpConfiguration.getIp() + ":8080/userReservations?studentId=" + studentId;
            //http://172.31.49.79:8080/userReservations?studentId=27526711
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            String responseEntity = restTemplate.getForObject(url, String.class);

            ObjectMapper mapper = new ObjectMapper();


            JsonNode s = mapper.readValue(responseEntity, JsonNode.class);
            reservationObjects = new ReservationObject[s.size()];
            int counter = 0;
            for (int i = 0; i < s.size(); i++) {
                if (s.findValues("position").get(i).asInt() == 0) { //if the position is  0 meaning  that it is a reservation
                    int id = s.findValues("id").get(i).asInt();
                    int roomId = s.findValues("roomId").get(i).asInt();
                    int sId = s.findValues("studentId").get(i).asInt();
                    String day = s.findValues("day").get(i).asText();
                    int startTime = s.findValues("startTime").get(i).asInt();
                    int endTime = s.findValues("endTime").get(i).asInt();
                    int position = s.findValues("position").get(i).asInt();


                    reservationObjects[i] = new ReservationObject(id, roomId, sId, day, startTime, endTime, position);
                    counter++;
                }
            }
            }catch(IOException e){
                System.out.println("Uhhhh, It crashed again" + e.getMessage());
            }
        }

    private void requestDeleteReservation(int studentId, int reservationId) {

        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String url = "http://" + IpConfiguration.getIp() + ":8080/deleteReservation";
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
            multiValueMap.add("studentId", studentId + "");
            multiValueMap.add("reservationId", reservationId + "");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(multiValueMap, headers);

            Boolean bool = restTemplate.postForObject(url, entity, Boolean.class);
            System.out.println("Delete is " + bool);

            NotificationUtils.cancelNotification(this.getApplicationContext(), reservationId);

        } catch (Exception e) {
            System.out.println("Sigh" + e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.nav__dawer, menu);
//        return true;
//    }

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Navigation.navigate(id, mainActivity.this, getIntent().getIntExtra("studentId", 0));
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
