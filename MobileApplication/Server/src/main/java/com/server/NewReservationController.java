package com.server;

import Core.Student;
import Mapper.ReservationMapper;
import Mapper.StudentMapper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class NewReservationController {

    @RequestMapping(value = "/reservation", method = RequestMethod.POST,produces = "application/json")
    public int newReservation(@RequestParam(value="roomId", defaultValue="") int roomId, @RequestParam(value="studentId", defaultValue="") int studentId, @RequestParam(value="day", defaultValue="") String day, @RequestParam(value="startTime", defaultValue="0") int startTime, @RequestParam(value="endTime", defaultValue="0") int endTime) throws ClassNotFoundException, SQLException
    {
        if(!validParameters(day, startTime, endTime))
            return -1;

        int position = ReservationMapper.makeNew(roomId, studentId, day, startTime, endTime);

        return position;
    }

    private boolean validParameters(String day, int startTime, int endTime)
    {
        return validDay(day) && validTime(startTime) && validTime(endTime) && endTime == startTime+1;
    }

    private boolean validDay(String day)
    {
        return (!StringUtils.isEmpty(day) && (day.equals("Monday") || day.equals("Tuesday") || day.equals("Wednesday") || day.equals("Thursday") || day.equals("Friday")));
    }

    private boolean validTime(int time)
    {
        return (time >= 8 && time <= 23);
    }
}