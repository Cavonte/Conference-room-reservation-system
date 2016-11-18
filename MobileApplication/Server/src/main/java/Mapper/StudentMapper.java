package Mapper;

import Core.Student;
import IdentityMap.StudentIdentityMap;
import TDG.StudentTDG;
import UnitOfWork.UnitOfWork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Emili on 2016-10-25.
 */

public class StudentMapper {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public StudentMapper(){
    }

    public static Student getData(int studentId) throws ClassNotFoundException, SQLException{

        readWriteLock.readLock().lock();

        try {
            Student student = StudentIdentityMap.getStudentFromMap(studentId);
            if(student != null)
            {
                return student;
            }
            else
            {
                ResultSet resultSet = StudentTDG.find(studentId);

                if(resultSet.next())
                {
                    int username = resultSet.getInt("username");
                    String name = resultSet.getString("FullName");
                    String password = resultSet.getString("password");

                    Student studentDB = new Student(username, name, password);
                    StudentIdentityMap.addStudent(studentDB);

                    return studentDB;
                }
                else
                {
                    return null;
                }
            }
        }
        finally{
            readWriteLock.readLock().unlock();
        }
    }

    public static boolean validStudent(int studentId) throws ClassNotFoundException, SQLException
    {
        if(StudentMapper.getData(studentId) != null)
            return true;
        return false;
    }

    public static ArrayList<Student> getAllData() throws SQLException, ClassNotFoundException
    {
        ResultSet resultSet = StudentTDG.findAll();
        ArrayList<Student> studentList = new ArrayList<Student>();
        readWriteLock.readLock().lock();

        try
        {
            if (resultSet == null)
                return null;
            else
            {
                while (resultSet.next())
                {
                    int username = resultSet.getInt("username");
                    String name = resultSet.getString("FullName");
                    String password = resultSet.getString("password");

                    studentList.add(new Student(username, name, password));
                    StudentIdentityMap.addStudent(new Student(username, name, password));
                }
                return studentList;
            }
        }
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }

    public static void makeNew(int u, String n, String p) throws SQLException, ClassNotFoundException
    {
        readWriteLock.writeLock().lock();

        try
        {
            Student s = new Student(u, n, p);
            StudentIdentityMap.addStudent(s);
            UnitOfWork.registerNew(s);
            UnitOfWork.commit();
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void set(Student s, String n, String p) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            s.setName(n);
            s.setPassword(p);
            UnitOfWork.registerDirty(s);
            UnitOfWork.commit();
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void erase(Student s) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            StudentIdentityMap.delete(s);
            UnitOfWork.registerDelete(s);
            UnitOfWork.commit();
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void saveToDB(Student s) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            StudentTDG.insert(s);
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void deleteToDB(Student s) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            StudentTDG.delete(s);
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void updateToDB(Student s) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            StudentTDG.update(s);
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }
}