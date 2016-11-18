package Mapper;

import Core.Room;
import IdentityMap.RoomIdentityMap;
import TDG.RoomsTDG;
import UnitOfWork.UnitOfWork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Emili on 2016-10-25.
 */

public class RoomMapper {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();


    public RoomMapper(){
    }

    public static Room getData(int roomId) throws ClassNotFoundException, SQLException
    {
        readWriteLock.readLock().lock();

        try
        {
            return getDataNoLock(roomId);
        }
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }

    private static Room getDataNoLock(int roomId) throws ClassNotFoundException, SQLException
    {
        Room room = RoomIdentityMap.getRoomFromMap(roomId);
        if (room != null)
        {
            return room;
        }
        else
        {
            ResultSet resultSet = RoomsTDG.find(roomId);

            if(resultSet.next())
            {
                int roomId1 = resultSet.getInt("roomId");
                String roomNumber1 = resultSet.getString("roomNumber");
                String description = resultSet.getString("description");
                int roomSize = resultSet.getInt("roomSize");

                Room roomDB = new Room(roomId1, roomNumber1, description, roomSize);
                RoomIdentityMap.addRoom(roomDB);

                return roomDB;
            }
            else
            {
                return null;
            }
        }
    }

    public static boolean validRoom(int roomId) throws ClassNotFoundException, SQLException
    {
        if(RoomMapper.getData(roomId) != null)
            return true;
        return false;
    }

    public static ArrayList<Room> getAllData() throws SQLException, ClassNotFoundException {

        readWriteLock.readLock().lock();

        try {
            ResultSet resultSet = RoomsTDG.findAll();
            ArrayList<Room> roomList = new ArrayList<Room>();

            if (resultSet == null)
                return null;
            else {

                while (resultSet.next()) {

                    int roomid1 = resultSet.getInt("roomId");
                    String roomNumber1 = resultSet.getString("roomNumber");
                    String description = resultSet.getString("description");
                    int roomSize = resultSet.getInt("roomSize");

                    roomList.add(new Room(roomid1, roomNumber1, description, roomSize));
                    RoomIdentityMap.addRoom(new Room(roomid1, roomNumber1, description, roomSize));
                }
                return roomList;
            }
        }
        finally{
            readWriteLock.readLock().unlock();
        }
    }

    public static void makeNew(int i, String rn, String d, int rs) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try
        {
            Room ro = new Room(i, rn, d, rs);
            RoomIdentityMap.addRoom(ro);
            UnitOfWork.registerNew(ro);
            UnitOfWork.commit();
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void set(Room room, String roomNumber, String description, int roomSize) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            if(getDataNoLock(room.getId()) == null)
                throw new IllegalArgumentException("Trying to update a room that does not exist in the map or database.");

            room.setRoomNumber(roomNumber);
            room.setDescription(description);
            room.setRoomSize(roomSize);

            UnitOfWork.registerDirty(room);
            UnitOfWork.commit();
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void erase(Room r) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            RoomIdentityMap.delete(r);
            UnitOfWork.registerDelete(r);
            UnitOfWork.commit();
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static void saveToDB(Room ro) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            RoomsTDG.insert(ro);
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static void deleteToDB(Room ro) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            RoomsTDG.delete(ro);
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static void updateToDB(Room ro) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            RoomsTDG.update(ro);
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }
}
