package com.skynetprojectapp.android.skynetprojectapp;


import android.os.StrictMode;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

//This class is a static class that handles the reservation server call that get the reservations from the database.

public class ReservationDayCatalog {

    private static ArrayList<ReservationObject> reservations=new ArrayList<ReservationObject>();
    private static HashMap<String, ReservationObject> roomReservations=new HashMap<String, ReservationObject>();

    public ReservationDayCatalog(){
//        reservations = ;
//        roomReservations = ;

    }

    public static ArrayList<ReservationObject> getReservationsDayDB(String day){

        try {
        reservations.clear();
        roomReservations.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = "http://" + IpConfiguration.getIp() +":8080/dailyReservations?weekDay=" + day;
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String responseEntity = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
            JsonNode s = mapper.readValue(responseEntity, JsonNode.class);
            for(int i = 0; i < s.size(); i++){

                int resId = s.findValues("id").get(i).asInt();;
                int roomId = s.findValues("roomId").get(i).asInt();
                int studentId = s.findValues("studentId").get(i).asInt();
                String dayReservation = s.findValues("day").get(i).asText();
                int startTime = s.findValues("startTime").get(i).asInt();
                int endTime = s.findValues("endTime").get(i).asInt();
                int position = s.findValues("position").get(i).asInt();

                String key = roomId  + "u" + startTime;

                roomReservations.put(key, new ReservationObject(resId, roomId, studentId, dayReservation, startTime, endTime, position));
                reservations.add(new ReservationObject(resId, roomId, studentId, dayReservation, startTime, endTime, position));
            }
        }
        catch(HttpServerErrorException e){
            System.out.println("oh snap!" + e.getMessage() + "" + e.getCause());
        }
        catch (IOException e) {
            System.out.print(e.getMessage() + " " + e.getCause());
        }
        catch (HttpClientErrorException e){
            System.out.print("The server crashed, prolly 401" + e.getMessage() + "" + e.getCause() );
        }

        return reservations;
    }

    public static ArrayList<ReservationObject> getReservations(){
        return reservations;
    }

    public static ReservationObject getRoomBasedOnReservation(String key){
        return roomReservations.get(key);
    }

    public static int getHighestPosition(int roomid, int starttime){
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
