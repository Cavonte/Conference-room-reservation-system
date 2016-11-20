package com.skynetprojectapp.android.skynetprojectapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ViewAnimator;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RoomDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button okay;
     private ViewAnimator viewAnimator;
    private Animation slide_in_left, slide_out_right;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_room_detail);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        AppCompatTextView txtRoomid = (AppCompatTextView) findViewById(R.id.roomId);
        AppCompatTextView txtRoomNumber = (AppCompatTextView) findViewById(R.id.RoomNumber);
        AppCompatTextView txtRoomDescription = (AppCompatTextView) findViewById(R.id.Description);
        AppCompatTextView txtRoomSize = (AppCompatTextView) findViewById(R.id.RoomSize);
        okay=(Button) findViewById(R.id.Okay);
        okay.setOnClickListener(this);

        viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);

        slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        viewAnimator.setInAnimation(slide_in_left);
        viewAnimator.setOutAnimation(slide_out_right);

        txtRoomid.setText("Room Id: 1");
        txtRoomNumber.setText("Room Number:LH-340");
        txtRoomDescription.setText("Description: 1 TV available. Using the following Timeslot    " +  getIntent().getStringExtra("Key"));
        txtRoomSize.setText("Room Size: 5 max");

        String timseSlotInfo=  getIntent().getStringExtra("Key"); //info on the timeslot like the room so the proper can meb made to the db
        Thread db = new Thread() {
            public void run() {
                //start();
            }
        };
        db.start();
    }

    private void start() {
        final String url = "http://192.168.2.15:8080/rooms";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Object reservation = (Object) restTemplate.getForObject(url, Object.class);
        AppCompatTextView textView = (AppCompatTextView) findViewById(R.id.RoomSize);
        textView.setText(reservation.toString());
    }
    @Override
    public void onClick(View v) {
        if (v.getId()==okay.getId()){
            finish();
        }
//
//        viewAnimator.showPrevious();
//        viewAnimator.showNext();

    }



}
