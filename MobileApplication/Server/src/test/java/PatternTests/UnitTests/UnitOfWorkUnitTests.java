package PatternTests.UnitTests;

import Core.Student;
import Mapper.ReservationMapper;
import UnitOfWork.UnitOfWork;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;

//NOTE: IF THE TESTS ARE RAN THE SECOND TIME, SOME OF THEM WILL FAIL SINCE THE OBJECT WILL BE ALREADY INSIDE THE DB.
//PLEASE CLEAR YOUR DATABASE.

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@SpringBootConfiguration
public class UnitOfWorkUnitTests {

    @Test
    public void testRegisterNew() {

        Student student = new Student(1, "2name", "password");
        UnitOfWork.registerNew(student);

        try{
            UnitOfWork.registerNew(student);
        }
        catch(IllegalArgumentException e){
            System.out.println("Already exists in the map");
        }
    }

    @Test
    public void testRegisterNewAlreadyExisting(){

        Student student = new Student(1, "name", "password");
        UnitOfWork.registerNew(student);

        try{
            UnitOfWork.registerNew(student);
        }
        catch(IllegalArgumentException e){
            System.out.println("Already exists in the map");
        }
    }

    @Test
    public void testRegisterDirty(){
        Student student = new Student(16, "name", "password");
        UnitOfWork.registerDirty(student);
    }

    @Test
    public void testRegisterDirtyAlreadyExisting(){

        Student student = new Student(99, "name", "password");
        UnitOfWork.registerDirty(student);

        try{
            UnitOfWork.registerDirty(student);
        }
        catch(IllegalArgumentException e){
            System.out.println("Already exists in the map");
        }
    }

    @Test
    public void testRegisterDelete(){

        Student student = new Student(14, "name", "password");
        UnitOfWork.registerDelete(student);
    }

    @Test
    public void testRegisterDeleteAlreadyExisting(){

        Student student = new Student(15, "name", "password");
        UnitOfWork.registerDelete(student);

        try{
            UnitOfWork.registerDelete(student);
        }
        catch(IllegalArgumentException e){
            System.out.println("Already exists in the map");
        }
    }

    @Test
    public void testCommitRegisterNew() throws ClassNotFoundException, SQLException{

        Student student = new Student(222, "name", "password");
        UnitOfWork.registerNew(student);
        UnitOfWork.commit();

        UnitOfWork.registerDelete(student);
        UnitOfWork.commit();
    }

    @Test
    public void testCommitRegisterUpdate() throws ClassNotFoundException, SQLException{

        Student student = new Student(311, "name", "password");
        UnitOfWork.registerNew(student);
        UnitOfWork.commit();

        student = new Student(311, "name", "/za1232156");
        UnitOfWork.registerDirty(student);
        UnitOfWork.commit();

        UnitOfWork.registerDelete(student);
        UnitOfWork.commit();
    }



}
