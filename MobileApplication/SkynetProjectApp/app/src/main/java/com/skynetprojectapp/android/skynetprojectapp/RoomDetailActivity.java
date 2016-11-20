package com.skynetprojectapp.android.skynetprojectapp;

import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.ViewAnimator;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

public class RoomDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button okay;
    private Animation slide_in_left, slide_out_right;
    private LinearLayout contentRoomDetail;
    private ProgressBar progress;
    private ViewAnimator viewAnimator;
    private TextView textView6;
    private TextView roomMain;
    private TextView locMain;
    private TextView prezMain;
    private TextView sizeMain;
    private TextView textView5;
    private TextView date1;
    private TextView date2;
    private AppCompatTextView roomId;
    private AppCompatTextView roomNumber;
    private AppCompatTextView description;
    private AppCompatTextView roomSize;
    private Button bool,append, reserve;

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
        okay = (Button) findViewById(R.id.Okay);
        okay.setOnClickListener(this);

        viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);

        slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        viewAnimator.setInAnimation(slide_in_left);
        viewAnimator.setOutAnimation(slide_out_right);

        txtRoomid.setText("Room Id: 1");
        txtRoomNumber.setText("Room Number:LH-340");
        txtRoomDescription.setText("Description: 1 TV available. Using the following Timeslot    " + getIntent().getStringExtra("Key"));
        txtRoomSize.setText("Room Size: 5 max");

        String timseSlotInfo = getIntent().getStringExtra("Key"); //info on the timeslot like the room so the proper can meb made to the db
        Thread db = new Thread() {
            public void run() {
                //start();
            }
        };
        db.start();


        //Second view for the view animator

        contentRoomDetail = (LinearLayout) findViewById(R.id.content_room_detail);
        progress= (ProgressBar) findViewById(R.id.login_progress);
        viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);
        textView6 = (TextView) findViewById(R.id.textView6);
        roomMain = (TextView) findViewById(R.id.roomMain);
        locMain = (TextView) findViewById(R.id.locMain);
        prezMain = (TextView) findViewById(R.id.prezMain);
        sizeMain = (TextView) findViewById(R.id.sizeMain);
        textView5 = (TextView) findViewById(R.id.textView5);
        date1 = (TextView) findViewById(R.id.date1);
        date2 = (TextView) findViewById(R.id.date2);
        bool =(Button) findViewById(R.id.bool);
        append=  (Button) findViewById(R.id.append);
        reserve= (Button) findViewById(R.id.reserve);
        findViewById(R.id.bool).setOnClickListener(this);
        findViewById(R.id.roomDetails).setOnClickListener(this);
        findViewById(R.id.append).setOnClickListener(this);
        findViewById(R.id.reserve).setOnClickListener(this);
        roomId = (AppCompatTextView) findViewById(R.id.roomId);
        roomNumber = (AppCompatTextView) findViewById(R.id.RoomNumber);
        description = (AppCompatTextView) findViewById(R.id.Description);
        roomSize = (AppCompatTextView) findViewById(R.id.RoomSize);
        findViewById(R.id.Okay).setOnClickListener(this);


    }

    private void start() {
//        final String url = "http://192.168.2.15:8080/rooms";
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        Object reservation = (Object) restTemplate.getForObject(url, Object.class);
//        AppCompatTextView textView = (AppCompatTextView) findViewById(R.id.RoomSize);
//        textView.setText(reservation.toString());
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.bool:
                reserve.setBackgroundColor(Color.GRAY);
                append.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
            case R.id.roomDetails:
                viewAnimator.showPrevious();
                break;
            case R.id.append:
                //TODO implement
                break;
            case R.id.reserve:
                //TODO implement
                break;
            case R.id.Okay:
                viewAnimator.showNext();
                append.setBackgroundColor(Color.GRAY);
                reserve.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
        }

    }


}
