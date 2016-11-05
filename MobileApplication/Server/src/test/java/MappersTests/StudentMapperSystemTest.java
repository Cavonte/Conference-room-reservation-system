package MappersTests;

import Core.Student;
import IdentityMap.ObjectNotFoundException;
import IdentityMap.StudentIdentityMap;
import Mapper.RoomMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import Core.Room;
import org.junit.*;
import Mapper.StudentMapper;

/**
 * Created by dias on 16-11-05.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class StudentMapperSystemTest {

    @Test
    public void testGetData(){
        try {

            StudentMapper.makeNew(27526799, "patrick", "lkjlkaj09813");
            Student student = StudentMapper.getData(27526799);
            int id = student.getId();
            String name = student.getName();
            String password = student.getPassword();

            Assert.assertEquals(27526799, id);
            Assert.assertEquals("patrick", name);
            Assert.assertEquals("lkjlkaj09813", password);

            Student s = StudentIdentityMap.getStudentFromMap(27526799);
            StudentMapper.deleteToDB(s);

            StudentIdentityMap.addStudent(s);

            Assert.assertEquals(27526799, StudentIdentityMap.getStudentFromMap(s.getId()).getId());
            Assert.assertEquals("patrick", StudentIdentityMap.getStudentFromMap(s.getId()).getName());
            Assert.assertEquals("lkjlkaj09813", StudentIdentityMap.getStudentFromMap(s.getId()).getPassword());

            StudentIdentityMap.delete(s);

            StudentMapper.set(s, "123aaaa", "no");

            StudentMapper.erase(s);


        }
        catch(ClassNotFoundException e){
            Assert.assertTrue(false);
        }
        catch(SQLException e){
            Assert.assertTrue(false);
        }
        catch(ObjectNotFoundException e){
            Assert.assertTrue(false);
        }
    }


}

