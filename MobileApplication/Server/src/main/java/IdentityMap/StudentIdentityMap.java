package IdentityMap;

import java.util.HashMap;
import java.util.Map;

import Core.Student;

/**
 * Created by Emili on 2016-10-25.
 */

public class StudentIdentityMap {

    private static Map<Integer, Student> mapOfStudents = new HashMap<Integer, Student>();;

    public StudentIdentityMap(){

    }

    public static void addStudent(Student s){
        int temp = s.getId();
        mapOfStudents.put(s.getId(), s);
    }

    public static Student getStudentFromMap(int studentID) throws ObjectNotFoundException {
        Student s = mapOfStudents.get(studentID);
        if (s == null){
            throw new ObjectNotFoundException("Student not found.");
        }
        return s;
    }

    public static void delete(Student s){
        int id = s.getId();
        mapOfStudents.remove(id);
    }
}
