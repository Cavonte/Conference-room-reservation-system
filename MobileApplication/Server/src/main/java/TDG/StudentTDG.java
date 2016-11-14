package TDG;

import Core.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by dias on 2016-10-25.
 */

public class StudentTDG
{
    public static ResultSet findAll() throws SQLException, ClassNotFoundException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

        return resultSet;
    }
    
    public static ResultSet find(int studentId) throws SQLException, ClassNotFoundException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE username = " + studentId);

        return resultSet;
    }
    
    public static void insert(Student student) throws SQLException, ClassNotFoundException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("INSERT INTO students(username, FullName, password) VALUES (" + student.getId() + ",'" + student.getName() + "', '" + student.getPassword() + "');");

        statement.close();
        connection.close();
    }
    
    public static void update(Student student) throws SQLException, ClassNotFoundException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("UPDATE students SET FullName = '" + student.getName() + "', password = '" + student.getPassword() + "' WHERE username = " + student.getId() + "");

        statement.close();
        connection.close();
    }
    
    public static void delete(Student student) throws SQLException, ClassNotFoundException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("DELETE FROM students WHERE username = " + student.getId());

        statement.close();
        connection.close();
    }
}
