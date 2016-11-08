package com.skynetprojectapp.android.skynetprojectapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import android.widget.TextView;


public class preferencesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar toolbar;
    private TextView notifications;
    private TextView notificationsperiodexplain;
    private TextView notificationsperiod;
    private TextView changePassword;
    private CheckBox notificationcheck;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences__drawer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Preferences");
        setSupportActionBar(toolbar);

        DrawerLayout drawerP = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggleP = new ActionBarDrawerToggle(
                this, drawerP, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerP.addDrawerListener(toggleP);
        toggleP.syncState();

        NavigationView view = (NavigationView) findViewById(R.id.nav_view);
        view.setNavigationItemSelectedListener(this);

        notifications = (TextView) findViewById(R.id.notifications);

        notificationsperiod = (TextView) findViewById(R.id.notificationsperiod);
        notificationsperiod.setOnClickListener(preferencesActivity.this);

        notificationsperiodexplain = (TextView) findViewById(R.id.explainnoti);
        notificationsperiodexplain.setOnClickListener(preferencesActivity.this);

        changePassword = (TextView) findViewById(R.id.changePassword);
        changePassword.setOnClickListener(preferencesActivity.this);

        notificationcheck = (CheckBox) findViewById(R.id.checkBoxNoti);
        notificationcheck.setOnClickListener(preferencesActivity.this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.checkBoxNoti:
                Toast.makeText(preferencesActivity.this, "Able/Disable notifications", Toast.LENGTH_SHORT).show();
                break;
            case R.id.changePassword:
                Toast.makeText(preferencesActivity.this, "Change password fragment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.notificationsperiod:
                Toast.makeText(preferencesActivity.this, "Notification fragment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.explainnoti:
                Toast.makeText(preferencesActivity.this, "Notification fragment", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_Reservations) {
            Toast.makeText(this, "preferencesActivity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(preferencesActivity.this, mainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Rooms) {
            startActivity(new Intent(preferencesActivity.this, roomsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Map) {
            startActivity(new Intent(preferencesActivity.this, mapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_preferences) {
            startActivity(new Intent(preferencesActivity.this, preferencesActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_About) {
            startActivity(new Intent(preferencesActivity.this, aboutActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Help) {
            startActivity(new Intent(preferencesActivity.this, helpActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Log_out) {
            startActivity(new Intent(preferencesActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
