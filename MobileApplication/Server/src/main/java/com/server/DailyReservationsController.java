package com.server;

import Core.Reservation;
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
public class DailyReservationsController {

    //End point to return all reservations at position 0 on the given day
    // @throws IllegalArgumentException if the week day sent is invalid
    @RequestMapping(value = "/dailyReservations", method = RequestMethod.GET,produces = "application/json")
    public Object[] getDailyReservations(@RequestParam(value="weekDay", defaultValue="") String weekDay) throws SQLException, ClassNotFoundException
    {
        if(!Reservation.validDay(weekDay))
            throw new IllegalArgumentException("Invalid day " + weekDay);

        weekDay = weekDay.toLowerCase();

        //I don't like working with arrays, but it works much better with JSON.
        //This should be converted back to an ArrayList on the front end.
        return ReservationMapper.getFullReservationsForDay(weekDay).toArray();
    }
}
