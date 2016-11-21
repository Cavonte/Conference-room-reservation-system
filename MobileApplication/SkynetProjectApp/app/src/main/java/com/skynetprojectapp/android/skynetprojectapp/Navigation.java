package com.skynetprojectapp.android.skynetprojectapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Bruce on 11/21/2016.
 */

public  class Navigation {
    public Navigation() {
    }

    public static void navigate(int id, Activity prev){

        if (id == R.id.nav_Reservations) {
//            Toast.makeText(this, "preferencesActivity", Toast.LENGTH_SHORT).show();
            prev.startActivity(new Intent(prev, mainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Rooms) {
            prev.startActivity(new Intent(prev, roomsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Map) {
            prev.startActivity(new Intent(prev, mapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_preferences) {
            prev.startActivity(new Intent(prev,preferencesActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_About) {
            prev.startActivity(new Intent(prev, aboutActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Help) {
            prev.startActivity(new Intent(prev, helpActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        } else if (id == R.id.nav_Log_out) {
            prev.startActivity(new Intent(prev, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK));
            Toast.makeText(prev, "Logged out", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_Waitlist) {
            prev.startActivity(new Intent(prev,WaitlistActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
        }

    }
}
