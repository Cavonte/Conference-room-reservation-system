package Core;

import java.sql.SQLException;

/**
 * Created by Emili on 2016-10-25.
 */

public class StudentMapper {

    public StudentMapper(){
    }

    public static Student getStudent(int stuid) throws Exception, ClassNotFoundException, SQLException{
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
}
