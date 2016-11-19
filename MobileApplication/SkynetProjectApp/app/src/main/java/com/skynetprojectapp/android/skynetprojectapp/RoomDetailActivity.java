package com.skynetprojectapp.android.skynetprojectapp;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RoomDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Room Information");



        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        AppCompatTextView txtRoomid = (AppCompatTextView) this.findViewById(R.id.roomId);
        AppCompatTextView txtRoomNumber = (AppCompatTextView) this.findViewById(R.id.RoomNumber);
        AppCompatTextView txtRoomDescription = (AppCompatTextView) this.findViewById(R.id.Description);
        AppCompatTextView txtRoomSize = (AppCompatTextView) this.findViewById(R.id.RoomSize);
        txtRoomid.setText("Room Id: 1");
        txtRoomNumber.setText("Room Number:LH-340");
        txtRoomDescription.setText("Description: 1 TV available");
        txtRoomSize.setText("Room Size: 5 max");
        //start();

    }

    private void start(){
        final String url = "http://192.168.2.15:8080/rooms";
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        Object reservation = (Object)restTemplate.getForObject(url, Object.class);
        AppCompatTextView textView = (AppCompatTextView) this.findViewById(R.id.RoomSize);
        textView.setText(reservation.toString());
    }



}
