package com.server;



import org.springframework.boot.test.context.SpringBootTest;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.junit.*;
import java.sql.SQLException;
import TDG.StudentTDG;
import Core.Student;
import java.util.ArrayList;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class StudentTDGTests {


    @Test
    public void testFindAll(){
        try {
            ArrayList<Student> arrList = StudentTDG.findAll();
        }
        catch(SQLException e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testFind(){
        try {
            Student s = StudentTDG.find(27526766);
            int id = s.getId();
            String name = s.getName();
            Assert.assertTrue(id == 27526766);
            Assert.assertTrue(name.equals("Sponge Bob"));
        }
        catch(SQLException e){
            Assert.assertTrue(false);
        }

    }

    @Test
    public void testInsert(){

        Student s = new Student(12345, "Hello", "12312321323");

        try {
            StudentTDG.insert(s);
            Assert.assertTrue(s.getId() == StudentTDG.find(12345).getId());
            StudentTDG.delete(s);
        }
        catch(SQLException e){
            Assert.assertTrue(false);
        }

    }

    @Test
    public void testUpdate(){
        try {
            Student s = new Student(3333, "HELLO", "?");
            Student s2 = new Student(3333, "THERE", "?");
            StudentTDG.insert(s);
            StudentTDG.update(s2);
            Assert.assertTrue(StudentTDG.find(3333) != null);
            Assert.assertTrue(StudentTDG.find(3333).getName().equals(s2.getName()));
            StudentTDG.delete(s2);
        }
        catch(SQLException e){
            Assert.assertTrue(false);
        }
    }






}
