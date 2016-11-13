package IdentityMap;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;

import Core.Student;

/**
 * Created by Emili on 2016-10-25.
 */

public class StudentIdentityMap {

    private static Map<Integer, Student> mapOfStudents = new ConcurrentHashMap<Integer, Student>();

    public static void addStudent(Student s){
        mapOfStudents.put(s.getId(), s);
    }

    public static Student getStudentFromMap(int studentID){
        Student s = mapOfStudents.get(studentID);
        if (s == null){
            return null;
        }
        return s;
    }

    public static void delete(Student s){
        int id = s.getId();
        mapOfStudents.remove(id);
    }
}
