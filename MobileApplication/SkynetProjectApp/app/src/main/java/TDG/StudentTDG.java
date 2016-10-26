package TDG;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
    
    public ArrayList<Student> findAll() throws SQLException {
        
        ArrayList<Student> studentList = new ArrayList<>(30);
        
        Connection connection = DriverManager.getConnection("jdbc:mysql:db343.sql", databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * " + "FROM students");
        
        while(resultSet.next()){
            
            String username = resultSet.getString("username");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            
            studentList.add(new Student(username, name,password));
        }
        
        resultSet.close();
        statement.close();
        connection.close();
        
        return studentList;
        
    }
    
    public static Student find(int studentId) throws SQLException{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql:db343.sql", databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        
        ResultSet resultSet = statement.executeQuery("SELECT * FROM students WHERE studentId = " + studentId);
        
        if(resultSet.next()){
            String username = resultSet.getString("username");
            String name = resultSet.getString("name");
            String password = resultSet.getString("password");
            
            resultSet.close();
            connection.close();
            
            Student student = new Student(username, name, password);
            
            return student;
        }
        else{
            throw new SQLException("Error: empty result");
        }
        
        
        
    }
    
    public static void insert(Student student) throws SQLException{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql:db343.sql", databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("INSERT INTO students " + "VALUES ( " + student.getSid() + ",'" + student.getUsername() + "','" + student.getName() + "','" + student.getPassword() + "')");
        
        statement.close();
        connection.close();
    }
    
    public static void update(Student student) throws SQLException{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql:db343.sql", databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("UPDATE students " + "SET name = '" + student.getName() + "', password = '" + student.getPassword() + "'");
        statement.close();
        connection.close();
    }
    
    public static void delete(Student student) throws SQLException{
        
        Connection connection = DriverManager.getConnection("jdbc:mysql:db343.sql", databaseUsername, databasePassword);
        Statement statement = connection.createStatement();
        
        statement.executeUpdate("DELETE FROM students " + "WHERE studentId = " + student.getSid());
        statement.close();
        connection.close();
        
    }
    
    
}
