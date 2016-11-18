package IdentityMap;

import Core.Student;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Emili on 2016-10-25.
 */

public class StudentIdentityMap {

    private static Map<Integer, Student> mapOfStudents = new ConcurrentHashMap<Integer, Student>();

    public static void addStudent(Student student)
    {
        mapOfStudents.put(student.getId(), student);
    }

    public static Student getStudentFromMap(int studentID){
        Student student = mapOfStudents.get(studentID);
        if (student == null){
            return null;
        }
        return student;
    }

    public static void delete(Student student){
        int id = student.getId();
        mapOfStudents.remove(id);
    }
}