package TDG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import Core.Room;

/**
 * Created by dias on 2016-10-26.
 */

public class RoomsTDG {
    
    private static String databaseUsername = null;
    private static String databasePassword = null;
    
    
    public RoomsTDG(String username, String password){
        
        this.databaseUsername = username;
        this.databasePassword = password;
        
    }
    
    public ArrayList<Room> findAll() throws SQLException {
        
        ArrayList<Room> rooms = new ArrayList<>(30);
        
        Connection connection = DriverManager.getConnection("jdbc:mysql:db343.sql", databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * " + "FROM rooms");
        
        while(resultSet.next()){
            
            String roomNumber = resultSet.getString("roomNumber");
            String description = resultSet.getString("description");
            int roomSize = resultSet.getInt("roomSize");
            
            rooms.add(new Room(roomNumber, description,roomSize));
        }
        
        resultSet.close();
        statement.close();
        connection.close();
        
        return rooms;
        
    }
    
    public static Room find(int roomId) throws SQLException{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql:db343.sql", databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms WHERE roomId = " + roomId);
        
        if(resultSet.next()){
            String roomNumber1 = resultSet.getString("roomNumber");
            String description = resultSet.getString("description");
            int roomSize = resultSet.getInt("roomSize");
            
            resultSet.close();
            connection.close();
            
            Room room = new Room(roomNumber1, description, roomSize);
            
            return room;
        }
        else{
            throw new SQLException("Error: empty result");
        }
        
        
        
    }
    
    public static void insert(Room room) throws SQLException{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql:db343.sql", databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        
        //statement.executeUpdate("INSERT INTO rooms " + "VALUES ( " + room.getRid() + ", '" + room.getRoomNumber() + "', '" + room.getDescription() + "'," + room.getRoomSize() + ")");
        
        statement.close();
        connection.close();
    }
    
    public static void update(Room room) throws SQLException{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql:db343.sql", databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("UPDATE rooms " + "SET roomNumber = '" + room.getRoomNumber() + "', description = '" + room.getDescription() + "', roomSize = " + room.getRoomSize());
        statement.close();
        connection.close();
    }
    
    public static void delete(Room room) throws SQLException{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql:db343.sql", databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("DELETE FROM rooms " + "WHERE roomNumber = " + room.getRoomNumber());
        statement.close();
        connection.close();
        
    }
    
    
}
