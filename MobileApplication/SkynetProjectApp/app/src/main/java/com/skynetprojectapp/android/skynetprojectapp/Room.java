package com.skynetprojectapp.android.skynetprojectapp;

/**
 * Created by Emili on 2016-11-19.
 */

public class Room {
    private int roomId;
    private String roomNumber;
    private String description;
    private int roomSize;

    public Room(int roomId, String roomNumber, String description, int roomSize){
        this.roomId = roomId;
        this.roomNumber = roomNumber;
        this.description = description;
        this.roomSize = roomSize;
    }

    public int getRoomId() {
        return roomId;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getDescription() {
        return description;
    }

    public int getRoomSize() {
        return roomSize;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setRoomSize(int roomSize) {
        this.roomSize = roomSize;
    }

    public String toString(){
        return ("Room Number: " + roomNumber +
                "\nDescription: " + description +
                "\nSize " + roomSize +
                "\nRoom ID: " + roomId);
    }
}