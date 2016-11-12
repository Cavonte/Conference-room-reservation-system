package TDG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Core.Student;

/**
 * Created by dias on 2016-10-25.
 */

public class StudentTDG {
    
    private static String databaseUsername = null;
    private static String databasePassword = null;
    
    
    public StudentTDG(String username, String password){
        
        this.databaseUsername = username;
        this.databasePassword = password;
        
    }
    
    public static ResultSet findAll() throws SQLException {


        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "1234");
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students");

        /*
        while(resultSet.next()){

            int username = resultSet.getInt("username");
            String name = resultSet.getString("FullName");
            String password = resultSet.getString("password");

            studentList.add(new Student(username, name,password));
        }

        resultSet.close();
        statement.close();
        connection.close();
        */

        return resultSet;
        
    }
    
    public static ResultSet find(int studentId) throws SQLException{

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "1234");
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE username = " + studentId);

        return resultSet;
        
    }
    
    public static void insert(Student student) throws SQLException{

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "1234");
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("INSERT INTO students(username, FullName, password) VALUES (" + student.getId() + ",'" + student.getName() + "', '" + student.getPassword() + "');");

        statement.close();
        connection.close();
    }
    
    public static void update(Student student) throws SQLException{

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "1234");
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("UPDATE students SET FullName = '" + student.getName() + "', password = '" + student.getPassword() + "' WHERE username = " + student.getId() + "");

        statement.close();
        connection.close();
    }
    
    public static void delete(Student student) throws SQLException{

        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/db343?characterEncoding=UTF-8&useSSL=false", "root", "1234");
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("DELETE FROM students WHERE username = " + student.getId());

        statement.close();
        connection.close();
        
    }
    
    
}
