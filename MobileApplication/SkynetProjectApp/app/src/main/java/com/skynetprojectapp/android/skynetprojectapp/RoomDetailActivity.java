package com.skynetprojectapp.android.skynetprojectapp;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewAnimator;

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

import static android.view.View.GONE;

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
    private Button bool, append, reserve;
    private AlertDialog alertDialog;
    private boolean modifying;
    private ReservationObject modifiedReservation;
    private int id,resId,studentId,startTime,endTime,position;
    private String day;


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
        append=  (Button) findViewById(R.id.append);
        reserve= (Button) findViewById(R.id.reserve);
        okay.setOnClickListener(this);

        viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);

        slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        viewAnimator.setInAnimation(slide_in_left);
        viewAnimator.setOutAnimation(slide_out_right);

        id = getIntent().getIntExtra("RoomId", 1);
        String roomnumber = getIntent().getStringExtra("RoomNumber");
        String des = getIntent().getStringExtra("RoomDescription");
        int timeReservation = getIntent().getIntExtra("Time", 1);
        int roomsize = getIntent().getIntExtra("RoomSize", 1);
        resId = getIntent().getIntExtra("resId", 1);
        studentId = getIntent().getIntExtra("studentId", 1);
        day = getIntent().getStringExtra("day");
        startTime = getIntent().getIntExtra("startTime",0);
        endTime = getIntent().getIntExtra("endTime",0);
        position = getIntent().getIntExtra("position",0);


        txtRoomid.setText("Room Id:" + id);
        txtRoomNumber.setText("Room Number: " + roomnumber);
        txtRoomDescription.setText("Description: " + des + ". Using the following Timeslot  " + getIntent().getStringExtra("Key"));
        txtRoomSize.setText("Room Size: " + roomsize);



        String timseSlotInfo = getIntent().getStringExtra("Key"); //info on the timeslot like the room so the proper can meb made to the db
        if(isPositionWaitlist(position)){
            reserve.setVisibility(GONE);
            findViewById(R.id.append).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkIfReservationExists(studentId)){
                        makeReservation(id,studentId,day,startTime,endTime);
                    }
                }
            });
        }
        else{
            append.setVisibility(GONE);
            findViewById(R.id.reserve).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    makeReservation(id,studentId,day,startTime,endTime);
                }
            });
        }

        //Second view for the view animator

        contentRoomDetail = (LinearLayout) findViewById(R.id.content_room_detail);
        progress = (ProgressBar) findViewById(R.id.login_progress);
        viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);
        textView6 = (TextView) findViewById(R.id.textView6);
        roomMain = (TextView) findViewById(R.id.roomMain);
        roomMain.setText("Room Number: " + roomnumber);
        locMain = (TextView) findViewById(R.id.locMain);
        if (roomnumber.charAt(0) == 'H')
            locMain.setText("Location: H-Building");
        else if(roomnumber.charAt(0) == 'V')
            locMain.setText("Location: VL-Building");
        else if(roomnumber.charAt(0) == 'L')
            locMain.setText("Location: LB-Building");
        else
            locMain.setText("Location: B-Building");
        //prezMain = (TextView) findViewById(R.id.prezMain);
        sizeMain = (TextView) findViewById(R.id.sizeMain);
        sizeMain.setText("Room Size: " + Integer.toString(roomsize));
        textView5 = (TextView) findViewById(R.id.textView5);
        date1 = (TextView) findViewById(R.id.date1);
        if (timeReservation < 10)
            date1.setText("0" + timeReservation + ":00");
        else
            date1.setText(timeReservation + ":00");
        date2 = (TextView) findViewById(R.id.date2);
        if(timeReservation + 1 < 10)
            date2.setText("0" + (timeReservation + 1) + ":00");
        else
            date2.setText((timeReservation + 1) + ":00");
       // bool = (Button) findViewById(R.id.bool);
       // findViewById(R.id.bool).setOnClickListener(this);
        findViewById(R.id.roomDetails).setOnClickListener(this);
        findViewById(R.id.append).setOnClickListener(this);
        findViewById(R.id.reserve).setOnClickListener(this);
        roomId = (AppCompatTextView) findViewById(R.id.roomId);
        roomNumber = (AppCompatTextView) findViewById(R.id.RoomNumber);
        description = (AppCompatTextView) findViewById(R.id.Description);
        roomSize = (AppCompatTextView) findViewById(R.id.RoomSize);
        findViewById(R.id.Okay).setOnClickListener(this);


        if (getIntent().getBooleanExtra("fromEdit", false))
            modifying = true;
        else
            modifying = false;
        modifiedReservation= (ReservationObject) getIntent().getSerializableExtra("reservation");


    }

    private void start() {
//        final String url = "http://192.168.2.15:8080/rooms";
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
//        Object reservation = (Object) restTemplate.getForObject(url, Object.class);
//        AppCompatTextView textView = (AppCompatTextView) findViewById(R.id.RoomSize);
//        textView.setText(reservation.toString());
    }

    public boolean isPositionWaitlist(int position){

        if(position >= 0){
            Toast.makeText(getApplicationContext(), "You will be in the waitlist", Toast.LENGTH_LONG).show();
            return true;
        }
        else{
            Toast.makeText(getApplicationContext(), "Congratulations you can reserve it", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    //Make a new reservation
    private void makeReservation(int roomId, int studentId, String day, int startTime, int endTime){
        try {

            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String url = "http://" + IpConfiguration.getIp() + ":8080/reservation";
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
            multiValueMap.add("roomId", roomId + "");
            multiValueMap.add("studentId", studentId + "");
            multiValueMap.add("day", day + "");
            multiValueMap.add("startTime", startTime + "");
            multiValueMap.add("endTime", endTime + "");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(multiValueMap, headers);

            int result = restTemplate.postForObject(url, entity, Integer.class);
            System.out.println("result is " + result);
        }    catch(Exception e){
            System.out.println(e.getMessage() + "oh snap!");
        }
    }

    //Checks if the reservation exists
    private boolean checkIfReservationExists(int studentId){

        try{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = "http://" + IpConfiguration.getIp() +":8080/userReservations?studentId=" + studentId;
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String responseEntity = restTemplate.getForObject(url, String.class);
        ObjectMapper mapper = new ObjectMapper();

            JsonNode s = mapper.readValue(responseEntity, JsonNode.class);
            for(int i = 0; i < s.size(); i++){
                int resIdFromDB = s.findValues("id").get(i).asInt();;
                int roomIdFromDB = s.findValues("roomId").get(i).asInt();
                int sIdFromDB = s.findValues("studentId").get(i).asInt();
                String dayReservationFromDB = s.findValues("day").get(i).asText();
                int startTimeFromDB = s.findValues("startTime").get(i).asInt();
                int endTimeFromDB = s.findValues("endTime").get(i).asInt();
                int positionFromDB = s.findValues("position").get(i).asInt();

                if(     resId == resIdFromDB
                        && id == roomIdFromDB
                        && studentId == sIdFromDB
                        && day.equals(dayReservationFromDB) && startTime == startTimeFromDB
                        && endTime == endTimeFromDB){
                    return true;
                }
                else{
                    return false;
                }
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage() + "oh snap!");
        }
        return false;

    }

    private int modifyReservation(int studentId,int oldReservationId,int newRoomId,String newDay,int newStartTime, int newEndTime,boolean reservation){

        try{
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = "http://" + IpConfiguration.getIp() + ":8080/modifyReservation";
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String,String>();
        multiValueMap.add("studentId", studentId+"");
        multiValueMap.add("oldReservationId", oldReservationId+"");
        multiValueMap.add("newRoomId", newRoomId+"");
        multiValueMap.add("newDay", newDay+"");
        multiValueMap.add("newStartTime", newStartTime+"");
        multiValueMap.add("newEndTime", newEndTime+"");
        multiValueMap.add("reservation", true+"");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(multiValueMap, headers);

        int result = restTemplate.postForObject(url, entity, Integer.class);
        System.out.println("result is " + result);
            return result;

        }
        catch(Exception  e){
            System.out.println(e.getMessage() + "oh snap!");
        }
        return 0;
    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.roomDetails:
                viewAnimator.showPrevious();
                break;
            case R.id.append:
                alert(modifying);
                break;
            case R.id.reserve:
                alert(modifying);
                break;
            case R.id.Okay:
                viewAnimator.showNext();
                append.setBackgroundColor(Color.GRAY);
                reserve.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                break;
        }

    }

    private void alert(boolean edit) {
        alertDialog = new AlertDialog.Builder(RoomDetailActivity.this).create();
        alertDialog.setTitle("Confirmation");
        if (edit) {
            alertDialog.setMessage("Confirm Rerservation modification ?  Reservation to be modified is " + modifiedReservation.getDay() + " " + modifiedReservation.getStartTime());
        }
        else{
            makeReservation(id,studentId,day,startTime,endTime);
            alertDialog.setMessage("Confirm Rerservation ?");
        }

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RoomDetailActivity.this, "Yes", Toast.LENGTH_LONG).show();
                modifyReservation(modifiedReservation.getStudentId(),modifiedReservation.getResId(),id,day,startTime,endTime,true);
                finish();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(RoomDetailActivity.this, "No", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();
    }


}
