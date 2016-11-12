package com.server;

import Core.Reservation;
import Core.Room;
import Core.Student;
import Mapper.ReservationMapper;
import Mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class ReservationController {

    @Autowired
    ReservationMapper mockMapper;

    @RequestMapping(value = "/reservation", method = RequestMethod.GET,produces = "application/json")
    public String display(@RequestParam(value="resId", defaultValue="") int resId) throws ClassNotFoundException, SQLException
    {

        Reservation r1 = ReservationMapper.getData(resId);

        if(r1 == null)
            return "";
        else
            return r1.toString();

    }

    @RequestMapping(value = "/reservationAll", method = RequestMethod.GET,produces = "application/json")
    public String displayAll() throws ClassNotFoundException, SQLException
    {

        ArrayList<Reservation> reservations = ReservationMapper.getAllData();

        if(reservations.size() == 0)
            return "";
        else
            return reservations.toString();

    }


}
