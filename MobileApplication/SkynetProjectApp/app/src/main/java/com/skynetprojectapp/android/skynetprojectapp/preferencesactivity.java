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
import android.widget.Toast;


public class preferencesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preferences__drawer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Preferences");
        setSupportActionBar(toolbar);

        DrawerLayout drawerP = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggleP = new ActionBarDrawerToggle(
                this, drawerP, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerP.addDrawerListener(toggleP);
        toggleP.syncState();

        NavigationView view = (NavigationView) findViewById(R.id.nav_view);
        view.setNavigationItemSelectedListener(this);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_Reservations) {
            Toast.makeText(this, "preferencesActivity", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(preferencesActivity.this, mainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) );
        } else if (id == R.id.nav_Rooms) {
            Toast.makeText(this, "Rooms", Toast.LENGTH_SHORT).show();
            //startActivity(new Intent(mainActivity.this,mapsActivity.class));
        } else if (id == R.id.nav_Map) {
            startActivity(new Intent(preferencesActivity.this, mapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) );
        } else if (id == R.id.nav_preferences) {
            startActivity(new Intent(preferencesActivity.this, preferencesActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) );
        } else if (id == R.id.nav_About) {
            Toast.makeText(this, "About", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_Help) {
            Toast.makeText(this, "Help", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_Log_out) {
            startActivity(new Intent(preferencesActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK) );
            Toast.makeText(this, "Logged out", Toast.LENGTH_SHORT).show();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
