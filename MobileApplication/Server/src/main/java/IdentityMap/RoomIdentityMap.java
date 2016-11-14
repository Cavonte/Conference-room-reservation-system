package IdentityMap;

import Core.Room;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Emili on 2016-10-25.
 */

public class RoomIdentityMap {

    private static Map<Integer, Room> mapOfRooms = new ConcurrentHashMap<Integer, Room>();

    public static void addRoom(Room ro){
        mapOfRooms.put(ro.getId(), ro);
    }

    public static Room getRoomFromMap(int roid){
        Room ro = mapOfRooms.get(roid);
        if (ro == null){
           return null;
        }
        return ro;
    }

    public static void delete(Room ro){
        int id = ro.getId();
        mapOfRooms.remove(id);
    }
}
