package Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import Core.Room;
import Core.Student;
import IdentityMap.ObjectNotFoundException;
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

    public static Room getData(int rid) throws ObjectNotFoundException,ClassNotFoundException, SQLException{
        Room ro =  RoomIdentityMap.getRoomFromMap(rid);
        if(ro != null){
            return ro;
        }
        else{

        ResultSet resultSet = RoomsTDG.find(rid);

        if(resultSet.next()){

            String roomNumber1 = resultSet.getString("roomNumber");
            String description = resultSet.getString("description");
            int roomSize = resultSet.getInt("roomSize");

            Room roomDB = new Room(roomNumber1, description, roomSize);
            RoomIdentityMap.addRoom(roomDB);

            return roomDB;
        }
        else{
            throw new SQLException("Error: empty result");
        }

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
