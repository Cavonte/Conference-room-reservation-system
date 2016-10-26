package IdentityMap;

import java.util.HashMap;
import java.util.Map;

import Core.Room;

/**
 * Created by Emili on 2016-10-25.
 */

public class RoomIdentityMap {

    private static Map<String, Room> mapOfRooms;

    public RoomIdentityMap(){
        mapOfRooms = new HashMap<String, Room>();
    }

    public static void addRoom(Room ro){
        mapOfRooms.put(ro.getRoomNumber(), ro);
    }

    public static Room getRoomFromMap(String roomNumber) throws ObjectNotFoundException {
        Room ro = mapOfRooms.get(roomNumber);
        if (ro == null){
            throw new ObjectNotFoundException("Room not found.");
        }
        return ro;
    }
}
