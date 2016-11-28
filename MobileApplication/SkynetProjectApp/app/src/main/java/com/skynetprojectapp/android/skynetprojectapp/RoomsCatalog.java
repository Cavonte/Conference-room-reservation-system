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

/**
 * Created by Emili on 2016-11-17.
 */

public class RoomsCatalog {


    private static ArrayList<Room> rooms  = new ArrayList<Room>(55);

    public RoomsCatalog(){
        try {
        rooms.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = "http://" + IpConfiguration.getIp() +":8080/rooms";
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String responseEntity = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();


            JsonNode s = mapper.readValue(responseEntity, JsonNode.class);
            for(int i = 0; i < s.size(); i++){

                int roomId = s.findValues("id").get(i).asInt();;
                String roomNumber = s.findValues("roomNumber").get(i).asText();
                String description = s.findValues("description").get(i).asText();
                int roomSize = s.findValues("roomSize").get(i).asInt();

                rooms.add(new Room(roomId,roomNumber, description, roomSize));
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
    }

    public static ArrayList<Room> getRoomsFromDB(){
        try {
        rooms.clear();
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        String url = "http://" + IpConfiguration.getIp() +":8080/rooms";
        RestTemplate restTemplate = new RestTemplate();

        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        String responseEntity = restTemplate.getForObject(url, String.class);

        ObjectMapper mapper = new ObjectMapper();

            JsonNode s = mapper.readValue(responseEntity, JsonNode.class);
            for(int i = 0; i < s.size(); i++){

                int roomId = s.findValues("id").get(i).asInt();;
                String roomNumber = s.findValues("roomNumber").get(i).asText();
                String description = s.findValues("description").get(i).asText();
                int roomSize = s.findValues("roomSize").get(i).asInt();

                rooms.add(new Room(roomId,roomNumber, description, roomSize));
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
        return rooms;
    }

    public static Room getRoom(int roomid){
        return rooms.get(roomid - 1);
    }

    public static ArrayList<Room> getRoomList(){
        return rooms;
    }

}
