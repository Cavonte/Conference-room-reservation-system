package com.server;

import Core.Reservation;
import Mapper.ReservationMapper;
import Mapper.RoomMapper;
import Mapper.StudentMapper;
import org.springframework.dao.ConcurrencyFailureException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class ModifyReservationController
{
    //End point that modifies the given reservation for the given student to the new one defined in the parameters
    // @throws IllegalArgumentException if there are validation issues while adding or removing reservations
    // @throws ConcurrencyFailureException if the user is expecting to be added as a full reservation, but can only be added to the waitlist
    @RequestMapping(value = "/modifyReservation", method = RequestMethod.POST,produces = "application/json")
    public int modifyReservation(@RequestParam(value="studentId", defaultValue="") int studentId, @RequestParam(value="oldReservationId", defaultValue="") int oldReservationId, @RequestParam(value="newRoomId", defaultValue="") int newRoomId, @RequestParam(value="newDay", defaultValue="") String newDay, @RequestParam(value="newStartTime", defaultValue="0") int newStartTime, @RequestParam(value="newEndTime", defaultValue="0") int newEndTime, @RequestParam(value="reservation", defaultValue="") boolean expectingReservation) throws ClassNotFoundException, SQLException, IllegalArgumentException, ConcurrencyFailureException
    {
        if(!validParameters(newRoomId, studentId, newDay, newStartTime, newEndTime))
            throw new IllegalArgumentException("One of more of the parameters are invalid.");

        Reservation oldReservation = getStudentReservation(studentId, oldReservationId);
        if(oldReservation == null)
            throw new IllegalArgumentException("Student " + studentId + " does not have the reservation " + oldReservationId);

        newDay = newDay.toLowerCase();

        int position = ReservationMapper.modifyReservation(expectingReservation, oldReservation, newRoomId, studentId, newDay, newStartTime, newEndTime);

        return position;
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

    private boolean validParameters(int roomId, int studentId, String day, int startTime, int endTime) throws ClassNotFoundException, SQLException
    {
        return RoomMapper.validRoom(roomId) && StudentMapper.validStudent(studentId) && Reservation.validDay(day) && Reservation.validTime(startTime) && Reservation.validTime(endTime) && endTime == startTime+1;
    }
}