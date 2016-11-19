package IdentityMap;

import Core.Room;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Emili on 2016-10-25.
 */

public class RoomIdentityMap {

    private static Map<Integer, Room> mapOfRooms = new ConcurrentHashMap<Integer, Room>();

    public static void addRoom(Room room)
    {
        mapOfRooms.put(room.getId(), room);
    }

    public static Room getRoomFromMap(int roomId)
    {
        Room room = mapOfRooms.get(roomId);
        if (room == null){
           return null;
        }
        return room;
    }

    public static void delete(Room room)
    {
        int id = room.getId();
        mapOfRooms.remove(id);
    }
}