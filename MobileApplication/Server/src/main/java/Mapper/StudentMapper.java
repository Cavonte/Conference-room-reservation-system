package Mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import Core.Student;
import IdentityMap.StudentIdentityMap;
import TDG.StudentTDG;
import UnitOfWork.UnitOfWork;

/**
 * Created by Emili on 2016-10-25.
 */

public class StudentMapper {

    public StudentMapper(){
    }

    public static Student getData(int stuid) throws ClassNotFoundException, SQLException{
        Student stu = StudentIdentityMap.getStudentFromMap(stuid);
        if(stu != null)
        {
            return stu;
        }
        else {

            ResultSet resultSet = StudentTDG.find(stuid);

            if(resultSet.next()){

                int username = resultSet.getInt("username");
                String name = resultSet.getString("FullName");
                String password = resultSet.getString("password");

                Student studentDB = new Student(username, name, password);
                StudentIdentityMap.addStudent(studentDB);

                return studentDB;
            }
            else{
                throw new SQLException("Error: empty result");
            }
        }
    }

    public static void makeNew(int u, String n, String p) throws ClassNotFoundException, SQLException {
        Student s = new Student(u, n, p);
        StudentIdentityMap.addStudent(s);
        UnitOfWork.registerNew(s);
        UnitOfWork.commit();
    }

    public static void set(Student s, String n, String p) throws ClassNotFoundException, SQLException{
            s.setName(n);
            s.setPassword(p);
            UnitOfWork.registerDirty(s);
            UnitOfWork.commit();

    }

    public static void erase(Student s) throws ClassNotFoundException, SQLException {
        StudentIdentityMap.delete(s);
        UnitOfWork.registerDelete(s);
        UnitOfWork.commit();
    }

    public static void saveToDB(Student s) throws ClassNotFoundException, SQLException{
        StudentTDG.insert(s);
    }

    public static void deleteToDB(Student s) throws ClassNotFoundException, SQLException{
        StudentTDG.delete(s);
    }

    public static void updateToDB(Student s) throws ClassNotFoundException, SQLException{
        StudentTDG.update(s);
    }
}

