package Mapper;

import java.sql.SQLException;

import Core.Room;
import IdentityMap.RoomIdentityMap;

import TDG.RoomsTDG;
/**
 * Created by Emili on 2016-10-25.
 */

public class RoomMapper {

    public RoomMapper(){
    }

    public static Room getRoom(int rid) throws Exception, ClassNotFoundException, SQLException{
        Room ro =  RoomIdentityMap.getRoomFromMap(rid);
        if(ro != null){
            return ro;
        }
        else{
            Room roomDB = RoomsTDG.find(rid);
            RoomIdentityMap.addRoom(roomDB);
            return roomDB;
        }
    }
}
