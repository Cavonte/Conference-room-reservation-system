package com.skynetprojectapp.android.skynetprojectapp;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by Bruce on 11/21/2016.
 */

public class Navigation {
    public Navigation() {
    }

    public static void navigate(int id, Activity prev, int studentID) {
        if (id == R.id.nav_Reservations) {
//            Toast.makeText(this, "preferencesActivity", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(prev, mainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("studentId", studentID);
            prev.startActivity(intent);
        } else if (id == R.id.nav_Rooms) {

//            final ProgressDialog progressDialog = new ProgressDialog(prev,R.style.AppTheme);
//            progressDialog.setIndeterminate(true);
//            progressDialog.setMessage("Loading...");
//            progressDialog.show();
            Intent intent = new Intent(prev, roomsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("studentId", studentID);
            prev.startActivity(intent);
        } else if (id == R.id.nav_Map) {
            Intent intent = new Intent(prev, mapsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("studentId", studentID);
            prev.startActivity(intent);
        } else if (id == R.id.nav_preferences) {
            Intent intent = new Intent(prev, preferencesActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("studentId", studentID);
            prev.startActivity(intent);
        } else if (id == R.id.nav_About) {
            Intent intent = new Intent(prev, aboutActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("studentId", studentID);
            prev.startActivity(intent);
        } else if (id == R.id.nav_Help) {
            Intent intent = new Intent(prev, helpActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("studentId", studentID);
            prev.startActivity(intent);
        } else if (id == R.id.nav_Log_out) {
            Intent intent = new Intent(prev, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.putExtra("studentId", studentID);
            prev.startActivity(intent);
            Toast.makeText(prev, "Logged out", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_Waitlist) {
            Intent intent = new Intent(prev, WaitlistActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("studentId", studentID);
            prev.startActivity(intent);
        }

    }

    public static void navigate(Activity prev,Class next, int studentID) {

        Intent intent = new Intent(prev, next).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("studentId", studentID);
        prev.startActivity(intent);


    }

}
