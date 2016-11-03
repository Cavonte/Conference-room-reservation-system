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

    public void makeNew(String rn, String d, int rs) throws ClassNotFoundException,SQLException {
        Room ro = new Room(rn, d, rs);
        RoomIdentityMap.addRoom(ro);
        UnitOfWork.registerNew(ro);
        UnitOfWork.commit();
    }

    public void set(Room r, String rn, String d, int rs) throws ClassNotFoundException,SQLException{
        r.setRoomNumber(rn);
        r.setDescription(d);
        r.setRoomSize(rs);
        RoomIdentityMap.addRoom(r);
        UnitOfWork.registerDirty(r);
        UnitOfWork.commit();

    }

    public void erase(Room r) throws ClassNotFoundException,SQLException {
        RoomIdentityMap.delete(r);
        UnitOfWork.registerDelete(r);
        UnitOfWork.commit();
    }

    public static void saveToDB(Room ro) throws ClassNotFoundException,SQLException{
        RoomsTDG.insert(ro);
    }

    public static void deleteToDB(Room ro) throws ClassNotFoundException,SQLException{
        RoomsTDG.delete(ro);
    }

    public static void updateToDB(Room ro) throws ClassNotFoundException,SQLException{
        RoomsTDG.update(ro);
    }
}
