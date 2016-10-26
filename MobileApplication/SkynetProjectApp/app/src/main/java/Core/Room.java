package Core;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Emili on 2016-10-24.
 */

public class Room {

    private static final AtomicInteger countro = new AtomicInteger(1);
    private int rid;
    private String roomNumber;
    private String description;
    private int roomSize;

    public Room(String rn, String d, int rs){
        rid = countro.incrementAndGet();
        roomNumber = rn;
        description = d;
        roomSize = rs;
    }

    public int getRid() {
        return rid;
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

    public void setRid(int rid) {
        this.rid = rid;
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
                "\nRoom ID: " + rid);
    }
}
