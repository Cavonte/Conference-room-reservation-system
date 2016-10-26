package IdentityMap;

import java.util.HashMap;
import java.util.Map;

import Core.Student;

/**
 * Created by Emili on 2016-10-25.
 */

public class StudentIdentityMap {

    private static Map<Integer, Student> mapOfStudents;

    public StudentIdentityMap(){
        mapOfStudents = new HashMap<Integer, Student>();
    }

    public static void addStudent(Student s){
        mapOfStudents.put(s.getSid(), s);
    }

    public static Student getStudentFromMap(int studentID) throws ObjectNotFoundException {
        Student s = mapOfStudents.get(studentID);
        if (s == null){
            throw new ObjectNotFoundException("Student not found.");
        }
        return s;
    }
}
