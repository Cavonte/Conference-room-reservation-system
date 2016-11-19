package com.skynetprojectapp.android.skynetprojectapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
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
import android.widget.Toast;


/**
 * This activity is the reservation pages where the current reservations/ wait list are being displayed.
 * Created by Bruce
 */
public class mainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,View.OnClickListener {

    private Button reserve;
    private ImageButton del1,del2,del3, ed1,ed2,ed3;
    private  Reservation r1,r2,r3;
    private AlertDialog alertDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nav__drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Reservations");
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        r1 = (Reservation) findViewById(R.id.reservation);
        r1.setRoomNumber("LB1");
        //r1.setOnClickListener(mainActivity.this);


        r2 = (Reservation) findViewById(R.id.reservation2);
        r2.setRoomNumber("LB2");
        r2.setDay("Tuesday");
        //r2.setOnClickListener(mainActivity.this);


        r3 = (Reservation) findViewById(R.id.reservation3);
        r3.setRoomNumber("LB3");
        r3.setDay("Monday");




        reserve = (Button) findViewById(R.id.reserveroom);
        reserve.setOnClickListener(mainActivity.this);

        del1 = (ImageButton) findViewById(R.id.delres1);
        del1.setOnClickListener(mainActivity.this);
        del2 = (ImageButton) findViewById(R.id.delres2);
        del2.setOnClickListener(mainActivity.this);
        del3 = (ImageButton) findViewById(R.id.delres3);
        del3.setOnClickListener(mainActivity.this);

        ed1 = (ImageButton) findViewById(R.id.editres1);
        ed1.setOnClickListener(mainActivity.this);
        ed2 = (ImageButton) findViewById(R.id.editres2);
        ed2.setOnClickListener(mainActivity.this);
        ed3 = (ImageButton) findViewById(R.id.editres3);
        ed3.setOnClickListener(mainActivity.this);




    }

    private void alert(String msg){
        alertDialog = new AlertDialog.Builder(mainActivity.this).create();
        alertDialog.setTitle("Confirmation");
        alertDialog.setMessage("Are you sure you want to " + msg + " ?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mainActivity.this, "YES", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(mainActivity.this, "NO", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();
    }

    @Override
    public void onClick(View view){
        switch (view.getId()) {
            case R.id.reserveroom:
               Toast.makeText(mainActivity.this, "Reserve a room" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.reservation:
                r1.setDay("Day");
                r1.postInvalidate();
                break;
            case R.id.reservation2:
                r2.setDay("Day");
                r2.postInvalidate();
                break;
            case R.id.reservation3:
                r3.setDay("Day");
                r3.postInvalidate();
                break;
            case R.id.delres1:
                Toast.makeText(mainActivity.this, "Delete res 1" , Toast.LENGTH_SHORT).show();
                alert("delete reservation");
                break;
            case R.id.editres1:
                Toast.makeText(mainActivity.this, "Edit res 1" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.delres2:
                Toast.makeText(mainActivity.this, "Delete res 2" , Toast.LENGTH_SHORT).show();
                alert("delete reservation");
                break;
            case R.id.editres2:
                Toast.makeText(mainActivity.this, "Edit res 2" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.editres3:
                Toast.makeText(mainActivity.this, "Edit res 3" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.delres3:
                Toast.makeText(mainActivity.this, "Delete res 3" , Toast.LENGTH_SHORT).show();
                alert("delete reservation");
                break;
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

        if (id == R.id.nav_Reservations) {
//            Toast.makeText(this, "preferencesActivity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(mainActivity.this, mainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        else if(id == R.id.nav_Waitlist){
            startActivity(new Intent(mainActivity.this, WaitlistActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }
        else if (id == R.id.nav_Rooms) {
            startActivity(new Intent(mainActivity.this, roomsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Map) {
            startActivity(new Intent(mainActivity.this, mapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_preferences) {
            startActivity(new Intent(mainActivity.this, preferencesActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_About) {
            startActivity(new Intent(mainActivity.this, aboutActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Help) {
            startActivity(new Intent(mainActivity.this, helpActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Log_out) {
            startActivity(new Intent(mainActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
