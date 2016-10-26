package IdentityMap;

import java.util.HashMap;
import java.util.Map;

import Core.Room;

/**
 * Created by Emili on 2016-10-25.
 */

public class RoomIdentityMap {

    private static Map<Integer, Room> mapOfRooms;

    public RoomIdentityMap(){
        mapOfRooms = new HashMap<Integer, Room>();
    }

    public static void addRoom(Room ro){
        mapOfRooms.put(ro.getRid(), ro);
    }

    public static Room getRoomFromMap(int roid) throws ObjectNotFoundException {
        Room ro = mapOfRooms.get(roid);
        if (ro == null){
            throw new ObjectNotFoundException("Room not found.");
        }
        return ro;
    }
}
