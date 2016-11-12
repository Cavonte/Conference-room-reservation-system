package TDG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    
    public static ResultSet findAll() throws ClassNotFoundException,SQLException {

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "1234");
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms");

        /*
        while(resultSet.next()){
            
            String roomNumber = resultSet.getString("roomNumber");
            String description = resultSet.getString("description");
            int roomSize = resultSet.getInt("roomSize");
            
            rooms.add(new Room(roomNumber, description,roomSize));
        }
        
        resultSet.close();
        statement.close();
        connection.close();
        */

        return resultSet;
        
    }
    
    public static ResultSet find(int roomId) throws ClassNotFoundException,SQLException{

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "1234");
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM rooms WHERE roomId = " + roomId);

        return resultSet;
        
    }
    
    public static void insert(Room room) throws ClassNotFoundException,SQLException{

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "1234");
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("INSERT INTO rooms " + "VALUES ( " + room.getId() + ", '" + room.getRoomNumber() + "', '" + room.getDescription() + "'," + room.getRoomSize() + ")");
        
        statement.close();
        connection.close();
    }
    
    public static void update(Room room) throws ClassNotFoundException,SQLException{

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "1234");
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("UPDATE rooms " + "SET roomNumber = '" + room.getRoomNumber() + "', description = '" + room.getDescription() + "', roomSize = " + room.getRoomSize() + " WHERE roomId = " + room.getId());

        statement.close();
        connection.close();
    }
    
    public static void delete(Room room) throws ClassNotFoundException,SQLException{

        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "1234");
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("DELETE FROM rooms " + "WHERE roomNumber = '" + room.getRoomNumber()+ "'");

        statement.close();
        connection.close();
        
    }
    
    
}
