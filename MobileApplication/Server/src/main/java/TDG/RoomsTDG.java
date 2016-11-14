package TDG;

import Core.Room;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by dias on 2016-10-26.
 */

public class RoomsTDG {
    public static ResultSet findAll() throws ClassNotFoundException,SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms");

        return resultSet;
    }
    
    public static ResultSet find(int roomId) throws ClassNotFoundException,SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms WHERE roomId = " + roomId);

        return resultSet;
    }
    
    public static void insert(Room room) throws ClassNotFoundException,SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("INSERT INTO rooms " + "VALUES ( " + room.getId() + ", '" + room.getRoomNumber() + "', '" + room.getDescription() + "'," + room.getRoomSize() + ")");
        
        statement.close();
        connection.close();
    }
    
    public static void update(Room room) throws ClassNotFoundException,SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("UPDATE rooms " + "SET roomNumber = '" + room.getRoomNumber() + "', description = '" + room.getDescription() + "', roomSize = " + room.getRoomSize() + " WHERE roomId = " + room.getId());

        statement.close();
        connection.close();
    }
    
    public static void delete(Room room) throws ClassNotFoundException,SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("DELETE FROM rooms " + "WHERE roomNumber = '" + room.getRoomNumber()+ "'");

        statement.close();
        connection.close();
    }
}