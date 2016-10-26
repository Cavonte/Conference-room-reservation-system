package Core;

import java.util.HashMap;
import java.util.Map;

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

    public static Room getRoomFromMap(int roomID) throws ObjectNotFoundException{
        Room ro = mapOfRooms.get(roomID);
        if (ro == null){
            throw new ObjectNotFoundException();
        }
        return ro;
    }
}
