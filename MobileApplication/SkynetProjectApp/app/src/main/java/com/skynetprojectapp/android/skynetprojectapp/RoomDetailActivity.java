package com.skynetprojectapp.android.skynetprojectapp;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
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
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import static android.view.View.GONE;

public class RoomDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private Button okay;
    private Animation slide_in_left, slide_out_right;
    private ProgressBar progress;
    private ViewAnimator viewAnimator;
    private TextView textView6, roomMain, locMain, prezMain, sizeMain, textView5, date1, date2, dayoftheweek;
    private AppCompatTextView roomId, roomNumber, description, roomSize;
    private Button bool, append, reserve;
    private AlertDialog alertDialog;
    private boolean modifying;
    private ReservationObject modifiedReservation;
    private int id, resId, studentId, startTime, endTime, position;
    private ReservationObject[] reservationObjects;
    private String day;
    private boolean sameDayAllowed;


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
        append = (Button) findViewById(R.id.append);
        reserve = (Button) findViewById(R.id.reserve);
        okay.setOnClickListener(this);
        viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);
        slide_in_left = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left);
        slide_out_right = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right);
        viewAnimator.setInAnimation(slide_in_left);
        viewAnimator.setOutAnimation(slide_out_right);

        //getting the information passed from the previous activity

        id = getIntent().getIntExtra("RoomId", 1);
        String roomnumber = getIntent().getStringExtra("RoomNumber");
        String des = getIntent().getStringExtra("RoomDescription");
        int timeReservation = getIntent().getIntExtra("Time", 1);
        int roomsize = getIntent().getIntExtra("RoomSize", 1);
        resId = getIntent().getIntExtra("resId", 1);
        studentId = getIntent().getIntExtra("studentId", 1);
        sameDayAllowed=getIntent().getBooleanExtra("sameDayAllowed",false);
        day = getIntent().getStringExtra("day");
        startTime = getIntent().getIntExtra("startTime", 0);
        endTime = getIntent().getIntExtra("endTime", 0);
        position = getIntent().getIntExtra("position", 0);
        if (getIntent().getBooleanExtra("fromEdit", false))
            modifying = true;
        else
            modifying = false;
        modifiedReservation = (ReservationObject) getIntent().getSerializableExtra("reservation");


        txtRoomid.setText("Room Id:" + id);
        txtRoomNumber.setText("Room Number: " + roomnumber);
        txtRoomDescription.setText("Description: " + des);
        txtRoomSize.setText("Room Size: " + roomsize);

        //String timseSlotInfo = getIntent().getStringExtra("Key"); //info on the timeslot like the room so the proper can meb made to the db
        if (position >= 0) {
            //Toast.makeText(getApplicationContext(), "You will be in the waitlist", Toast.LENGTH_LONG).show();
            reserve.setVisibility(GONE);
            reserve.setOnClickListener(this);
//            findViewById(R.id.append).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                   // if(checkIfReservationExists(studentId)){
//                       // makeReservation(id,studentId,day,startTime,endTime);
//                        alert(modifying);
//                   // }
//                }
//            });
        } else {
            //Toast.makeText(getApplicationContext(), "Congratulations you can reserve it", Toast.LENGTH_LONG).show();
            append.setVisibility(GONE);
            append.setOnClickListener(this);
//            findViewById(R.id.reserve).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //makeReservation(id,studentId,day,startTime,endTime);
//                    alert(modifying);
//                }
//            });
        }

        //Second view for the view animator

        progress = (ProgressBar) findViewById(R.id.login_progress);
        viewAnimator = (ViewAnimator) findViewById(R.id.viewAnimator);
        textView6 = (TextView) findViewById(R.id.textView6);
        dayoftheweek= (TextView) findViewById(R.id.dayoftheweek);
        roomMain = (TextView) findViewById(R.id.roomMain);
        roomMain.setText("Room Number: " + roomnumber);
        locMain = (TextView) findViewById(R.id.locMain);
        if (roomnumber.charAt(0) == 'H')
            locMain.setText("Location: H-Building");
        else if (roomnumber.charAt(0) == 'V')
            locMain.setText("Location: VL-Building");
        else if (roomnumber.charAt(0) == 'L')
            locMain.setText("Location: LB-Building");
        else
            locMain.setText("Location: B-Building");
        //prezMain = (TextView) findViewById(R.id.prezMain);
        sizeMain = (TextView) findViewById(R.id.sizeMain);
        sizeMain.setText("Room Size: " + Integer.toString(roomsize));
        textView5 = (TextView) findViewById(R.id.textView5);
        dayoftheweek.setText(day);
        date1 = (TextView) findViewById(R.id.date1);
        if (timeReservation < 10)
            date1.setText("0" + timeReservation + ":00");
        else
            date1.setText(timeReservation + ":00");
        date2 = (TextView) findViewById(R.id.date2);
        if (timeReservation + 1 < 10)
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
    }

    @Override
    public void onClick(View v) {
        CheckCurrentReservations checkCurrentReservations = new CheckCurrentReservations();
        checkCurrentReservations.doInBackground();
        switch (v.getId()) {
            case R.id.roomDetails:
                viewAnimator.showPrevious();
                break;
            case R.id.append:
                if (idiotProofing(false)) alert(modifying);
                break;
            case R.id.reserve:
                if (idiotProofing(true)) alert(modifying);
                break;
            case R.id.Okay:
                viewAnimator.showNext();
                break;
        }

    }

    private void alert(boolean edit) {
        final boolean editf = edit;
        alertDialog = new AlertDialog.Builder(RoomDetailActivity.this).create();
        alertDialog.setTitle("Confirmation");
        if (editf) {
            alertDialog.setMessage("Confirm Rerservation modification ?  Reservation to be modified is " + modifiedReservation.getDay() + " at " + modifiedReservation.getStartTime() + ":00.");
        } else {
            alertDialog.setMessage("Confirm Rerservation ?");
        }
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(RoomDetailActivity.this, "Yes", Toast.LENGTH_LONG).show();
                if (!editf) makeReservation(id, studentId, day, startTime, endTime);
                else
                    modifyReservation(studentId, modifiedReservation.getResId(), id, day, startTime, endTime,getIntent().getBooleanExtra("reservationServer",true));
                //the reservation boolean is passed from the roomsActivity, true when modifyReservation is switching a reservation to another reservation and false when you are swit
                //ing to a waitlist
                finish();
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(RoomDetailActivity.this, "No", Toast.LENGTH_LONG).show();
            }
        });
        alertDialog.show();
    }

    private boolean idiotProofing(boolean reserving) {
        boolean timebookedAlready = false;
        boolean userIsOnTimeslot=false;
        int numOfReservations = 0, numberOfWaitlist = 0;
        Context c = getApplicationContext();
        //4 cases
        if (reservationObjects != null) {
        for (int l = 0; l < reservationObjects.length; l++) {
                //is the user trying to reserve or append to a waitlit that he  is already on
                if (reservationObjects[l].getDay().equalsIgnoreCase(day) &&  reservationObjects[l].getRoomId()==id  && reservationObjects[l].getStartTime()==startTime) userIsOnTimeslot=true;
                //does the user have 3 reservation already
                //check if the position of the object is equal to 0( check if it a reservation) and if it matches the day. If it the case then increase the number of res
                if (reservationObjects[l].getPosition() == 0) ++numOfReservations;
                //is the user on 3 waitlist already
                //check if the position of the object is equal to 1 and more( check if it a waitlist append) and if it matches the day. If it the case then increase the number of waitlist
                if (reservationObjects[l].getPosition() >= 1) ++numberOfWaitlist;
                //does the user have a reservation at that specific time
                //check if the starttime of the selected timeslot is equal to the starttime of the current reservation, also check if the days  are identical
                if (reservationObjects[l].getStartTime() == startTime && reservationObjects[l].getDay().equalsIgnoreCase(day) )  timebookedAlready = true;
            }
        }
        if (sameDayAllowed) {
            //the person is modifying therefore no need to check for the 3rd condition( same hour for another room)
            if ( (numberOfWaitlist >= 3 && !reserving) || userIsOnTimeslot) {
                //display error message due one of the previous conditions
                if (numOfReservations >= 3) {
                    Toast.makeText(c, "You currently have 3 reservations. Consider modifying your current ones", Toast.LENGTH_SHORT).show();
                }
                if (numberOfWaitlist >= 3) {
                    Toast.makeText(c, "You currently have 3 waitlist. Consider deleting your current ones", Toast.LENGTH_SHORT).show();
                }
                if (timebookedAlready) {
                    Toast.makeText(c, "You have already booked a room at this hour", Toast.LENGTH_SHORT).show();
                }
                if(userIsOnTimeslot){
                    Toast.makeText(c, "You are already on this reservatioon/waitlist", Toast.LENGTH_SHORT).show();
                }
                return false;
                //one the conditions above return true
            } else {
                return true;
                //the reservation can go through
            }
        }
        else {
            //regular reservation where all the conditions need to be false to allow a reservation
            if (numOfReservations >= 3 || (numberOfWaitlist >= 3 && !reserving)|| timebookedAlready || userIsOnTimeslot) {
                //display error message due one of the previous conditions
                if (numOfReservations >= 3) {
                    Toast.makeText(c, "You currently have 3 reservations. Consider modifying your current ones", Toast.LENGTH_SHORT).show();
                }
                if (numberOfWaitlist >= 3) {
                    Toast.makeText(c, "You currently have 3 waitlist. Consider deleting your current ones", Toast.LENGTH_SHORT).show();
                }
                if (timebookedAlready) {
                    Toast.makeText(c, "You have already booked a room at this hour", Toast.LENGTH_SHORT).show();
                }
                if(userIsOnTimeslot){
                    Toast.makeText(c, "You are already on this reservatioon/waitlist", Toast.LENGTH_SHORT).show();
                }
                return false;
                //one the conditions above return true
            } else {
                return true;
                //the reservation can go through
            }
        }

    }

    private class CheckCurrentReservations extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                String url = "http://" + IpConfiguration.getIp() + ":8080/userReservations?studentId=" + studentId;
                //http://172.31.49.79:8080/userReservations?studentId=studentId
                RestTemplate restTemplate = new RestTemplate();

                restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
                String responseEntity = restTemplate.getForObject(url, String.class);

                ObjectMapper mapper = new ObjectMapper();
                JsonNode s = mapper.readValue(responseEntity, JsonNode.class);
                reservationObjects = new ReservationObject[s.size()];
                int counter = 0;
                for (int i = 0; i < s.size(); i++) {
                    int id = s.findValues("id").get(i).asInt();
                    int roomId = s.findValues("roomId").get(i).asInt();
                    int sId = s.findValues("studentId").get(i).asInt();
                    String day = s.findValues("day").get(i).asText();
                    int startTime = s.findValues("startTime").get(i).asInt();
                    int endTime = s.findValues("endTime").get(i).asInt();
                    int position = s.findValues("position").get(i).asInt();

                    reservationObjects[counter] = new ReservationObject(id, roomId, sId, day, startTime, endTime, position);
                    counter++;
                }
            } catch (IOException e) {
                System.out.println("Uhhhh, It crashed again" + e.getMessage());
            } catch (HttpServerErrorException e) {
                System.out.println("oh snap!" + e.getMessage() + "" + e.getCause());
            } catch (HttpClientErrorException e) {
                System.out.print("The server crashed, prolly 401" + e.getMessage() + "" + e.getCause());
            }
            return null;
        }

    }

    // TODO: 11/28/2016 check backend for deletion of waitlist when making a reservation
    //Make a new reservation
    private void makeReservation(int roomId, int studentId, String day, int startTime, int endTime) {
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
            String timeMarker = "AM";
            if(startTime > 11)
                timeMarker = "PM";
            NotificationUtils.scheduleNotification(this.getApplicationContext(), "Upcoming reservation", ("Reservation at " + startTime + timeMarker), day, startTime-1, 0, studentId);

        }
        catch(HttpServerErrorException e){
            System.out.println("oh snap!" + e.getMessage() + "" + e.getCause());
            Toast.makeText(getApplicationContext(), "Martha, the server is at it again", Toast.LENGTH_LONG).show();
        } catch (HttpClientErrorException e) {
            System.out.print("The server crashed, prolly 401" + e.getMessage() + "" + e.getCause());
        }

    }

    //Checks if the reservation exists
    private boolean checkIfReservationExists(int studentId) {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String url = "http://" + IpConfiguration.getIp() + ":8080/userReservations?studentId=" + studentId;
            RestTemplate restTemplate = new RestTemplate();

            restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
            String responseEntity = restTemplate.getForObject(url, String.class);
            ObjectMapper mapper = new ObjectMapper();

            JsonNode s = mapper.readValue(responseEntity, JsonNode.class);
            for (int i = 0; i < s.size(); i++) {
                int resIdFromDB = s.findValues("id").get(i).asInt();
                ;
                int roomIdFromDB = s.findValues("roomId").get(i).asInt();
                int sIdFromDB = s.findValues("studentId").get(i).asInt();
                String dayReservationFromDB = s.findValues("day").get(i).asText();
                int startTimeFromDB = s.findValues("startTime").get(i).asInt();
                int endTimeFromDB = s.findValues("endTime").get(i).asInt();
                int positionFromDB = s.findValues("position").get(i).asInt();

                if (resId == resIdFromDB
                        && id == roomIdFromDB
                        && studentId == sIdFromDB
                        && day.equals(dayReservationFromDB) && startTime == startTimeFromDB
                        && endTime == endTimeFromDB) {
                    return true;
                } else {
                    return false;
                }
            }
        } catch (HttpServerErrorException e) {
            System.out.println("oh snap!" + e.getMessage() + "" + e.getCause());
            Toast.makeText(getApplicationContext(), "Martha, the server is at it again", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            System.out.print(e.getMessage() + " " + e.getCause());
        } catch (HttpClientErrorException e) {
            System.out.print("The server crashed, prolly 401" + e.getMessage() + "" + e.getCause());
        }
        return false;

    }

    private int modifyReservation(int studentId, int oldReservationId, int newRoomId, String newDay, int newStartTime, int newEndTime,boolean reservation) {

        try {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
            String url = "http://" + IpConfiguration.getIp() + ":8080/modifyReservation";
            RestTemplate restTemplate = new RestTemplate();

            MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<String, String>();
            multiValueMap.add("studentId", studentId + "");
            multiValueMap.add("oldReservationId", oldReservationId + "");
            multiValueMap.add("newRoomId", newRoomId + "");
            multiValueMap.add("newDay", newDay + "");
            multiValueMap.add("newStartTime", newStartTime + "");
            multiValueMap.add("newEndTime", newEndTime + "");
            multiValueMap.add("reservation", reservation + "");
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<MultiValueMap<String, String>>(multiValueMap, headers);

            int result = restTemplate.postForObject(url, entity, Integer.class);
            System.out.println("result is " + result);
            return result;

        } catch (HttpServerErrorException e) {
            System.out.println("oh snap!" + e.getMessage() + "" + e.getCause());
            Toast.makeText(getApplicationContext(), "Martha, the server is at it again", Toast.LENGTH_LONG).show();
        } catch (HttpClientErrorException e) {
            System.out.print("The server crashed, prolly 401" + e.getMessage() + "" + e.getCause());
        }
        return 0;
    }


}
