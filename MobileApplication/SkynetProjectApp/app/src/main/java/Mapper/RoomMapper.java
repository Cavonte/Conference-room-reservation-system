package Mapper;

import java.sql.SQLException;

import Core.Room;
import Core.Student;
import IdentityMap.RoomIdentityMap;

import IdentityMap.StudentIdentityMap;
import TDG.RoomsTDG;
import TDG.StudentTDG;
import UnitOfWork.UnitOfWork;

/**
 * Created by Emili on 2016-10-25.
 */

public class RoomMapper {

    public RoomMapper(){
    }

    public static Room getData(int rid) throws Exception, ClassNotFoundException, SQLException{
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

    public void makeNew(String rn, String d, int rs) throws SQLException {
        Room ro = new Room(rn, d, rs);
        RoomIdentityMap.addRoom(ro);
        UnitOfWork.registerNew(ro);
        RoomsTDG.insert(ro);
    }

    public void set(Room r, String rn, String d, int rs) throws SQLException{
        r.setRoomNumber(rn);
        r.setDescription(d);
        r.setRoomSize(rs);
        UnitOfWork.registerDirty(r);
        UnitOfWork.commit();
        RoomsTDG.update(r);
    }

    public void erase(Room r) throws SQLException {
        RoomIdentityMap.delete(r);
        UnitOfWork.registerDelete(r);
        UnitOfWork.commit();
        RoomsTDG.delete(r);
    }

    public static void saveToMap(Room ro){
        RoomIdentityMap.addRoom(ro);
    }

    public static void deleteToMap(Room ro){
        RoomIdentityMap.delete(ro);
    }
}
