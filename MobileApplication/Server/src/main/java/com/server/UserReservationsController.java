package com.server;

import Core.Room;
import Core.Student;
import Mapper.ReservationMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * Created by dias on 16-10-28.
 */

//End point that returns all room objects from the database
@RestController
public class UserReservationsController {

    @RequestMapping(value = "/userReservations", method = RequestMethod.GET,produces = "application/json")
    public Object[] getUserReservations(@RequestParam(value="studentId", defaultValue="") String username) throws SQLException, ClassNotFoundException
    {
        if(!Student.isValidUsername(username))
            throw new IllegalArgumentException("Invalid format of student id.");

        int studentId = Integer.parseInt(username);
        //I don't like working with arrays, but it works much better with JSON.
        //This should be converted back to an ArrayList on the front end.
        return ReservationMapper.getResForStud(studentId).toArray();
    }
}
