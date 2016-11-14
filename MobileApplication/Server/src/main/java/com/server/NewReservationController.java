package com.server;

import Mapper.ReservationMapper;
import Mapper.RoomMapper;
import Mapper.StudentMapper;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class NewReservationController {

    //End point that takes in the room id, day, start and end time, and student id so it can add a new reservation or add to the waitlist
    // @throws IllegalArgumentException if there is a reservation conflict or if the user has too many existing reservations
    @RequestMapping(value = "/reservation", method = RequestMethod.POST,produces = "application/json")
    public int newReservation(@RequestParam(value="roomId", defaultValue="") int roomId, @RequestParam(value="studentId", defaultValue="") int studentId, @RequestParam(value="day", defaultValue="") String day, @RequestParam(value="startTime", defaultValue="0") int startTime, @RequestParam(value="endTime", defaultValue="0") int endTime) throws ClassNotFoundException, SQLException, IllegalArgumentException
    {
        if(!validParameters(roomId, studentId, day, startTime, endTime))
            return -1;

        day = day.toLowerCase();

        int position = ReservationMapper.makeNew(roomId, studentId, day, startTime, endTime);

        return position;
    }

    private boolean validParameters(int roomId, int studentId, String day, int startTime, int endTime) throws ClassNotFoundException, SQLException
    {
        return validRoom(roomId) && validStudent(studentId) && validDay(day) && validTime(startTime) && validTime(endTime) && endTime == startTime+1;
    }

    private boolean validRoom(int roomId) throws ClassNotFoundException, SQLException
    {
        if(RoomMapper.getData(roomId) != null)
            return true;
        return false;
    }

    private boolean validStudent(int studentId) throws ClassNotFoundException, SQLException
    {
        if(StudentMapper.getData(studentId) != null)
            return true;
        return false;
    }

    private boolean validDay(String day)
    {
        if(StringUtils.isEmpty(day))
            return false;
        day = day.toLowerCase();
        return ((day.equals("monday") || day.equals("tuesday") || day.equals("wednesday") || day.equals("thursday") || day.equals("friday")));
    }

    private boolean validTime(int time)
    {
        return (time >= 8 && time <= 23);
    }
}