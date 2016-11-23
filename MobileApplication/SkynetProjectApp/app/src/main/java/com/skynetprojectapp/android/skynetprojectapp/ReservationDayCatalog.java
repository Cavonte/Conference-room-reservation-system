package com.skynetprojectapp.android.skynetprojectapp;


import android.os.StrictMode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class ReservationDayCatalog {

    private ArrayList<ReservationObject> reservations;
    private HashMap<Integer, ReservationObject> roomReservations;

    public ReservationDayCatalog(){
        reservations = new ArrayList<ReservationObject>();
        roomReservations = new HashMap<Integer, ReservationObject>();

    }

    public ArrayList<ReservationObject> getReservationsDayDB(String day){

        //reservations.clear();
        //roomReservations.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = "http://" + IpConfiguration.getIp() +":8080/dailyReservations?weekDay=" + day;
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String responseEntity = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();


        try {

            JsonNode s = mapper.readValue(responseEntity, JsonNode.class);
            for(int i = 0; i < s.size(); i++){

                int resId = s.findValues("id").get(i).asInt();;
                int roomId = s.findValues("roomId").get(i).asInt();
                int studentId = s.findValues("studentId").get(i).asInt();
                String dayReservation = s.findValues("day").get(i).asText();
                int startTime = s.findValues("startTime").get(i).asInt();
                int endTime = s.findValues("endTime").get(i).asInt();
                int position = s.findValues("position").get(i).asInt();

                roomReservations.put(roomId, new ReservationObject(resId, roomId, studentId, dayReservation, startTime, endTime, position));
                reservations.add(new ReservationObject(resId, roomId, studentId, dayReservation, startTime, endTime, position));
            }
        }
        catch(IOException e){
            System.out.println("oh snap!");
        }
        return reservations;
    }

    public ArrayList<ReservationObject> getReservations(){
        return reservations;
    }

    public ReservationObject getRoomBasedOnReservation(int roomId){
        return roomReservations.get(roomId);
    }

    public int getHighestPosition(int roomid, int starttime){
        int max = 0;
        for(int i = 0; i < reservations.size(); i++) {
            ReservationObject r = reservations.get(i);
            if (r.getRoomId() == roomid) {
                if (r.getStartTime() == starttime) {
                    if (r.getPosition() > max)
                        max = r.getPosition();
                }
            }
        }
        return max;
    }
}
