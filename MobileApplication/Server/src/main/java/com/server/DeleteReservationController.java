package com.server;

import Core.Reservation;
import Mapper.ReservationMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class DeleteReservationController {

    //End point that deletes the given reservation belonging to the given student
    // @throws IllegalArgumentException if the given reservation id does not belong to the given student
    @RequestMapping(value = "/deleteReservation", method = RequestMethod.POST,produces = "application/json")
    public boolean deleteReservation(@RequestParam(value="studentId", defaultValue="") int studentId, @RequestParam(value="reservationId", defaultValue="") int reservationId) throws ClassNotFoundException, SQLException, IllegalArgumentException
    {
        Reservation reservation = getStudentReservation(studentId, reservationId);
        if(reservation == null)
            throw new IllegalArgumentException("Student " + studentId + " does not have the reservation " + reservationId);

        ReservationMapper.erase(reservation);
        return true;
    }

    private Reservation getStudentReservation(int studentId, int reservationId) throws ClassNotFoundException, SQLException
    {
        ArrayList<Reservation> reservations = ReservationMapper.getAllResOfStudent(studentId);

        for(Reservation reservation: reservations)
        {
            if(reservation.getId() == reservationId)
                return reservation;
        }

        return null;
    }
}