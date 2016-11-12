package MappersTests;

import Mapper.StudentMapper;
import Core.Student;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

import org.junit.*;

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

            Student s = StudentMapper.getData(27526799);

            StudentMapper.set(s, "123aaaa", "no");

            Student ss = StudentMapper.getData(27526799);

            StudentMapper.erase(s);

            StudentMapper.getAllData();
        }
        catch(ClassNotFoundException e){
            Assert.assertTrue(false);
        }
        catch(SQLException e){
            Assert.assertTrue(false);
        }
    }


}

