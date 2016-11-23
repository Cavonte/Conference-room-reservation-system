package com.skynetprojectapp.android.skynetprojectapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

/**
 * This activity containts the options/settings available to the user.
 * Created by Bruce
 */
public class preferencesActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView notificationsperiodexplain;
    private CheckBox notificationcheck;
    private Toolbar toolbar;
    private TextView notifications;
    private TextView notificationsperiod;
    private TextView explainnoti;
    private TextView changePassword, match, length;
    private ViewAnimator viewAn;
    private AlertDialog alertDialog;
    private Animation slide_in_left, slide_out_right;
    private EditText cpss, npass, conpass;
    int backgroundcolor;


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

        viewAn = (ViewAnimator) findViewById(R.id.viewAn);
        slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        viewAn.setInAnimation(slide_in_left);
        viewAn.setOutAnimation(slide_out_right);

        match = (TextView) findViewById(R.id.match);
        length = (TextView) findViewById(R.id.length);


        cpss = (EditText) findViewById(R.id.previouspass);
        npass = (EditText) findViewById(R.id.newpass);
        conpass = (EditText) findViewById(R.id.newpassconfirm);
        backgroundcolor = cpss.getDrawingCacheBackgroundColor();

        npass.setBackgroundColor(Color.RED);
        npass.setAlpha((float) 0.8);
        length.setText("New Password is empty or too short");
        length.setTextColor(Color.RED);

        conpass.setBackgroundColor(Color.RED);
        conpass.setAlpha((float) 0.8);
        match.setText("New Password and Confirm Password do not match");
        match.setTextColor(Color.RED);

        npass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String npasssword = npass.getText().toString();
                String copassword = conpass.getText().toString();
                checkLength(npasssword);
                matchPassword(npasssword, copassword);
                return false;
            }
        });

        npass.setOnFocusChangeListener((new EditText.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String npasssword = npass.getText().toString();
                    String copassword = conpass.getText().toString();
                    matchPassword(npasssword, copassword);
                    checkLength(npasssword);
                }
            }
        }));

        conpass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                String npasssword = npass.getText().toString();
                String copassword = conpass.getText().toString();
                matchPassword(npasssword, copassword);
                return false;
            }
        });

        conpass.setOnFocusChangeListener((new EditText.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String npasssword = npass.getText().toString();
                    String copassword = conpass.getText().toString();
                    matchPassword(npasssword, copassword);

                }
            }
        }));
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.confirm).setOnClickListener(this);


    }

    private void matchPassword(String npasssword, String copassword) {
        if (!(npass.getText().toString().equals(conpass.getText().toString()))) {
            conpass.setBackgroundColor(Color.RED);
            conpass.setAlpha((float) 0.8);
            match.setText("New Password and Confirm Password do not match");
            match.setTextColor(Color.RED);
        } else {
            conpass.setBackgroundColor(backgroundcolor);
            match.setText("New Password and Confirm Password  match");
            match.setTextColor(getResources().getColor(R.color.chartreuse));
        }
    }

    private void checkLength(String npasssword) {
        if (npasssword.length() < 4) {
            npass.setBackgroundColor(Color.RED);
            npass.setAlpha((float) 0.8);
            length.setText("New Password is empty or too short");
            length.setTextColor(Color.RED);
        } else {
            npass.setBackgroundColor(backgroundcolor);
            length.setText("New Password length is fine");
            length.setTextColor(getResources().getColor(R.color.chartreuse));
        }

    }

    @Override
    public void onClick(View view) {
        String password, npasssword, copassword;
        switch (view.getId()) {
            case R.id.checkBoxNoti:
                Toast.makeText(preferencesActivity.this, "Able/Disable notifications", Toast.LENGTH_SHORT).show();
                break;
            case R.id.changePassword:
                Toast.makeText(preferencesActivity.this, "Change password", Toast.LENGTH_SHORT).show();
                viewAn.showPrevious();
                break;
            case R.id.notificationsperiod:
                Toast.makeText(preferencesActivity.this, "Notification fragment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.explainnoti:
                Toast.makeText(preferencesActivity.this, "Notification fragment", Toast.LENGTH_SHORT).show();
                break;
            case R.id.cancel:
                viewAn.showPrevious();
                break;
            case R.id.confirm:
                password = cpss.getText().toString();
                npasssword = npass.getText().toString();
//                 copassword = conpass.getText().toString();
                alert("Confirm change from" + password + "to" + npasssword + "? ");
        }
    }

    private void alert(String msg) {
        alertDialog = new AlertDialog.Builder(preferencesActivity.this).create();
        alertDialog.setTitle("Change password");
        alertDialog.setMessage("Are you sure you want to " + msg + " ?");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(preferencesActivity.this, "Server calls", Toast.LENGTH_LONG).show();

            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(preferencesActivity.this, "Cancel", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        Navigation.navigate(id,preferencesActivity.this,getIntent().getIntExtra("studentId",0));

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
