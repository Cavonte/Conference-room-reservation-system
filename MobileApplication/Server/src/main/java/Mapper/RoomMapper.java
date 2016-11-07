package Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Core.Room;
import TDG.RoomsTDG;
import UnitOfWork.UnitOfWork;
import IdentityMap.RoomIdentityMap;

/**
 * Created by Emili on 2016-10-25.
 */

public class RoomMapper {

    public RoomMapper(){
    }

    public static Room getData(int rid) throws ClassNotFoundException, SQLException{
        Room ro =  RoomIdentityMap.getRoomFromMap(rid);
        if(ro != null){
            return ro;
        }
        else{

            ResultSet resultSet = RoomsTDG.find(rid);

            if(resultSet.next()){
                int roomid1 = resultSet.getInt("roomId");
                String roomNumber1 = resultSet.getString("roomNumber");
                String description = resultSet.getString("description");
                int roomSize = resultSet.getInt("roomSize");

                Room roomDB = new Room(roomid1, roomNumber1, description, roomSize);
                RoomIdentityMap.addRoom(roomDB);

                return roomDB;
            }
            else{
                throw new SQLException("Error: empty result");
            }

        }
    }

    public static ArrayList<Room> getAllData() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = RoomsTDG.findAll();
        ArrayList<Room> roomList = new ArrayList<Room>();

        if (resultSet == null)
            return null;
        else {

            while(resultSet.next()){

                int roomid1 = resultSet.getInt("roomId");
                String roomNumber1 = resultSet.getString("roomNumber");
                String description = resultSet.getString("description");
                int roomSize = resultSet.getInt("roomSize");

                roomList.add(new Room(roomid1, roomNumber1, description,roomSize));
                RoomIdentityMap.addRoom(new Room(roomid1, roomNumber1, description,roomSize));
            }
            return roomList;
        }
    }

    public static void makeNew(int i, String rn, String d, int rs) throws ClassNotFoundException, SQLException{
        Room ro = new Room(i, rn, d, rs);
        RoomIdentityMap.addRoom(ro);
        UnitOfWork.registerNew(ro);
        UnitOfWork.commit();
    }

    public static void set(Room r, String rn, String d, int rs) throws ClassNotFoundException, SQLException{
        r.setRoomNumber(rn);
        r.setDescription(d);
        r.setRoomSize(rs);
        UnitOfWork.registerDirty(r);
        UnitOfWork.commit();

    }

    public static void erase(Room r) throws ClassNotFoundException, SQLException{
        RoomIdentityMap.delete(r);
        UnitOfWork.registerDelete(r);
        UnitOfWork.commit();
    }

    public static void saveToDB(Room ro) throws ClassNotFoundException, SQLException{
        RoomsTDG.insert(ro);
    }

    public static void deleteToDB(Room ro) throws ClassNotFoundException, SQLException{
        RoomsTDG.delete(ro);
    }

    public static void updateToDB(Room ro) throws ClassNotFoundException, SQLException{
        RoomsTDG.update(ro);
    }
}
