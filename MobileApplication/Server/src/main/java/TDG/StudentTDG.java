package TDG;

import Core.Student;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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

    public static void insert(ArrayList<Student> newStudents) throws SQLException, ClassNotFoundException
    {
        if(newStudents.size() > 0)
        {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();

            try
            {
                for(Student student : newStudents)
                {
                    statement.executeUpdate("INSERT INTO students(username, FullName, password) VALUES (" + student.getId() + ",'" + student.getName() + "', '" + student.getPassword() + "');");
                }
            }
            finally
            {
                statement.close();
                connection.close();
            }
        }
    }

    public static void update(ArrayList<Student> dirtyStudents) throws SQLException, ClassNotFoundException
    {
        if(dirtyStudents.size() > 0)
        {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();

            try
            {
                for(Student student : dirtyStudents)
                {
                    statement.executeUpdate("UPDATE students SET FullName = '" + student.getName() + "', password = '" + student.getPassword() + "' WHERE username = " + student.getId() + "");
                }
            }
            finally
            {
                statement.close();
                connection.close();
            }
        }
    }

    public static void delete(ArrayList<Student> removedStudents) throws SQLException, ClassNotFoundException
    {
        if(removedStudents.size() > 0)
        {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();

            try
            {
                for(Student student : removedStudents)
                {
                    statement.executeUpdate("DELETE FROM students WHERE username = " + student.getId());
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
