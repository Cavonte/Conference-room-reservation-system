package TDG;

import Core.Room;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by dias on 2016-10-26.
 */

public class RoomsTDG
{
    public static ResultSet findAll() throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms");

        return resultSet;
    }

    public static ResultSet find(int roomId) throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms WHERE roomId = " + roomId);

        return resultSet;
    }

    public static void insert(ArrayList<Room> newRooms) throws ClassNotFoundException, SQLException
    {
        if(newRooms.size() > 0)
        {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            try
            {
                for(Room room : newRooms)
                {
                    statement.executeUpdate("INSERT INTO rooms " + "VALUES ( " + room.getId() + ", '" + room.getRoomNumber() + "', '" + room.getDescription() + "'," + room.getRoomSize() + ")");
                }
            }
            finally
            {
                statement.close();
                connection.close();
            }
        }
    }

    public static void update(ArrayList<Room> dirtyRooms) throws ClassNotFoundException, SQLException
    {
        if(dirtyRooms.size() > 0)
        {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();

            try
            {
                for(Room room : dirtyRooms)
                {
                    statement.executeUpdate("UPDATE rooms " + "SET roomNumber = '" + room.getRoomNumber() + "', description = '" + room.getDescription() + "', roomSize = " + room.getRoomSize() + " WHERE roomId = " + room.getId());
                }
            }
            finally
            {
                statement.close();
                connection.close();
            }
        }
    }

    public static void delete(ArrayList<Room> removedRooms) throws ClassNotFoundException, SQLException
    {
        if(removedRooms.size()>0)
        {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();

            try
            {
                for(Room room : removedRooms)
                {
                    statement.executeUpdate("DELETE FROM rooms " + "WHERE roomNumber = '" + room.getRoomNumber() + "'");
                }
            }
            finally
            {
                statement.close();
                connection.close();
            }
        }
    }
}