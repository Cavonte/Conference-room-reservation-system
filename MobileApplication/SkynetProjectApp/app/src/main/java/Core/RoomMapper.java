package Core;

import java.sql.SQLException;

/**
 * Created by Emili on 2016-10-25.
 */

public class RoomMapper {

    public RoomMapper(){
    }

    public static Room getRoom(int roId) throws Exception, ClassNotFoundException, SQLException{
        Room ro =  RoomIdentityMap.getRoomFromMap(roId);
        if(ro != null){
            return ro;
        }
        else{
            Room roomDB = RoomTDG.find(roId);
            RoomIdentityMap.addRoom(roomDB);
            return roomDB;
        }
    }
}
