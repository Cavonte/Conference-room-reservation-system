package TDG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//Class with basic database related functions
public class DatabaseUtils {
    private final static String DATABASE_USERNAME = "root";
    private final static String DATABASE_PASSWORD = "admin";
    private final static String DATABASE_URL = "jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false";
    
    public static Connection getConnection() throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.jdbc.Driver");
        Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);

        return connection;
    }
}
