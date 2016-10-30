package Mapper;



import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

import Core.Student;
import IdentityMap.ObjectNotFoundException;
import IdentityMap.StudentIdentityMap;

import TDG.StudentTDG;
import UnitOfWork.UnitOfWork;

/**
 * Created by Emili on 2016-10-25.
 */

public class StudentMapper {

    public StudentMapper(){
    }

    public static Student getData(int stuid) throws Exception, ClassNotFoundException, SQLException{
        Student stu = StudentIdentityMap.getStudentFromMap(stuid);
        if(stu != null)
        {
            return stu;
        }
        else {
            Student studentDB = StudentTDG.find(stuid);
            StudentIdentityMap.addStudent(studentDB);
            return studentDB;
        }
    }

    public void makeNew(String u, String n, String p) throws SQLException {
        Student s = new Student(u, n, p);
        StudentIdentityMap.addStudent(s);
        UnitOfWork.registerNew(s);
        UnitOfWork.commit();
    }

    public void set(Student s, String u, String n, String p) throws SQLException{
            s.setUsername(u);
            s.setName(n);
            s.setPassword(p);
            StudentIdentityMap.addStudent(s);
            UnitOfWork.registerDirty(s);
            UnitOfWork.commit();

    }

    public void erase(Student s) throws SQLException {
        StudentIdentityMap.delete(s);
        UnitOfWork.registerDelete(s);
        UnitOfWork.commit();
    }

    public static void saveToDB(Student s) throws SQLException {
        StudentTDG.insert(s);
    }

    public static void deleteToDB(Student s) throws SQLException {
        StudentTDG.delete(s);
    }

    public static void updateToDB(Student s) throws SQLException {
        StudentTDG.update(s);
    }
}

