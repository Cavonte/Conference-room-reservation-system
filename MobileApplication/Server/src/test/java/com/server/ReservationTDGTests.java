package com.server;

import Core.Reservation;
import Core.Room;
import TDG.ReservationTDG;
import TDG.RoomsTDG;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;
import java.util.ArrayList;
import Core.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest

public class ReservationTDGTests {
    @Test
    public void testFindAll(){

        try {
            ArrayList<Reservation> arrList = ReservationTDG.findAll();
        }
        catch(SQLException e){
            Assert.assertTrue(false);
        }
        catch(ClassNotFoundException e){
            Assert.assertTrue(false);
        }
    }

    @Test
    public void testInsertAndFind(){
        try {

            Room room = new Room("LH-123",null,4);
            Student s = new Student(1,"Hello","??");
            Reservation r1 = new Reservation(room.getId(),s.getId(),"Monday", "11:00:00", "12:00:00",0);
            ReservationTDG.insert(r1);
            Reservation retrievedReservation = ReservationTDG.find(r1.getId());


            String day = retrievedReservation.getDay();
            String startTime = retrievedReservation.getStartTime();
            int position = retrievedReservation.getPosition();
            int roomId = retrievedReservation.getId();


            Assert.assertTrue(day.equals("Monday"));
            Assert.assertTrue(startTime.equals("11:00:00"));
            Assert.assertTrue(position == 0);

            //removes from database
            ReservationTDG.delete(r1);
        }
        catch(SQLException e){
            Assert.assertTrue(false);
        }
        catch(ClassNotFoundException e){
            Assert.assertTrue(false);
        }

    }
}
