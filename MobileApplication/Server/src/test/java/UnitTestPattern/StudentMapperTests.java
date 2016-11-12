package UnitTestPattern;

/**
 * Created by Emili on 2016-11-05.
 */

import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.*;
import java.sql.SQLException;

import Mapper.StudentMapper;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StudentMapperTests {

    @Test
    public void testMakeNew(){
        try {
            int u = 27526799;
            String n = "patrick";
            String p = "lkjlkaj09813";
            StudentMapper.makeNew(27526799, "patrick", "lkjlkaj09813");
        }
        catch(ClassNotFoundException e){
            Assert.assertTrue(false);
        }
        catch(SQLException e){
            Assert.assertTrue(false);
        }


    }

}
