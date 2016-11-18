package Mapper;

import Core.Reservation;
import IdentityMap.ReservationIdentityMap;
import TDG.ReservationTDG;
import UnitOfWork.UnitOfWork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Emili on 2016-10-26.
 */

public class ReservationMapper {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public ReservationMapper() {
    }

    public static Reservation getData(int resId) throws ClassNotFoundException, SQLException {
        readWriteLock.readLock().lock();

        try {
            Reservation reservation = ReservationIdentityMap.getResFromMap(resId);
            if (reservation != null) {
                return reservation;
            } else {
                ResultSet resultSet = ReservationTDG.find(resId);

                if (resultSet.next()) {
                    int reservationId = resultSet.getInt("reservationId");
                    int roomId = resultSet.getInt("roomId");
                    int studentId = resultSet.getInt("studentId");
                    String weekDay = resultSet.getString("weekDay");
                    int startTime = resultSet.getInt("startTime");
                    int endTime = resultSet.getInt("endTime");
                    int position = resultSet.getInt("position");

                    Reservation reservationDB = new Reservation(reservationId, roomId, studentId, weekDay, startTime, endTime, position);
                    ReservationIdentityMap.addRes(reservationDB);

                    return reservationDB;
                } else {
                    return null;
                }
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public static ArrayList<Reservation> getAllData() throws SQLException, ClassNotFoundException {
        readWriteLock.readLock().lock();

        try {
            ResultSet resultSet = ReservationTDG.findAll();
            ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

            if (resultSet == null)
                return null;
            else {
                while (resultSet.next()) {
                    int resId = resultSet.getInt("reservationId");
                    int roomId = resultSet.getInt("roomId");
                    int studentId = resultSet.getInt("studentId");
                    String weekDay = resultSet.getString("weekDay");
                    int startTime = resultSet.getInt("startTime");
                    int endTime = resultSet.getInt("endTime");
                    int position = resultSet.getInt("position");

                    reservationList.add(new Reservation(resId, roomId, studentId, weekDay, startTime, endTime, position));
                    ReservationIdentityMap.addRes(new Reservation(resId, roomId, studentId, weekDay, startTime, endTime, position));
                }
                return reservationList;
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public static int makeNew(int roomId, int studentId, String day, int startTime, int endTime) throws ClassNotFoundException, SQLException, IllegalArgumentException {
        readWriteLock.writeLock().lock();

        try {
            int position = getPosition(day, startTime, endTime, roomId);
            ArrayList<Reservation> existingReservations = getAllResOfStudentNoLock(studentId);

            int numReservations = numReservations(existingReservations);

            if (numReservations >= 3)
                throw new IllegalArgumentException("Cannot add the reservation because the user already has 3 reservations.");
            else if (position > 0 && numWaitlist(existingReservations) >= 3)
                throw new IllegalArgumentException("Cannot add the user to this waitlist because are already on 3 waitlists.");

            handleConflictingReservations(existingReservations, day, startTime, endTime, position, roomId);

            //If this will fill up their list of reservations, then delete their waitlist items
            if (numReservations == 2)
                deleteWaitlist(existingReservations);

            Reservation reservation = new Reservation(Reservation.getNextId(), roomId, studentId, day, startTime, endTime, position);
            ReservationIdentityMap.addRes(reservation);
            UnitOfWork.registerNew(reservation);
            UnitOfWork.commit();
            return position;
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    //Helper method that returns the number of non waitlist reservations that are in the provided arraylist
    private static int numReservations(ArrayList<Reservation> reservations) {
        int count = 0;
        for (Reservation reservation : reservations) {
            if (reservation.getPosition() == 0)
                count++;
        }

        return count;
    }

    //Helper method that returns the number of waitlist reservations that are in the provided arraylist
    private static int numWaitlist(ArrayList<Reservation> reservations) {
        int count = 0;
        for (Reservation reservation : reservations) {
            if (reservation.getPosition() > 0)
                count++;
        }

        return count;
    }

    //Finds conflicting reservations and deletes them if the new reservation is position 0 and they are not or
    // @throws IllegalArgumentException if any conflicting reservations are position 0
    private static void handleConflictingReservations(ArrayList<Reservation> reservations, String day, int startTime, int endTime, int position, int roomId) throws SQLException, ClassNotFoundException, IllegalArgumentException {
        ArrayList<Reservation> conflictingReservations = getConflictingReservations(reservations, day, startTime, endTime, roomId);

        //At this point, we know there are only conflicting waitlists
        //If we're creating a reservation, then remove all waitlists
        if (position == 0) {
            deleteWaitlist(conflictingReservations);
        }
    }

    //Method that deletes all waitlist items in the list from the database
    private static void deleteWaitlist(ArrayList<Reservation> reservations) throws ClassNotFoundException, SQLException {
        for (Reservation reservation : reservations) {
            if (reservation.getPosition() > 0)
                eraseNoLock(reservation);
        }
    }

    //Method that returns all reservations in the list at the same day and time
    private static ArrayList<Reservation> getConflictingReservations(ArrayList<Reservation> reservations, String day, int startTime, int endTime, int roomId) throws IllegalArgumentException {
        ArrayList<Reservation> conflictingReservations = new ArrayList<Reservation>();
        for (Reservation reservation : reservations) {
            if (reservation.getDay().toLowerCase().equals(day) && reservation.getStartTime() == startTime && reservation.getEndTime() == endTime) {
                if (reservation.getPosition() == 0)
                    throw new IllegalArgumentException("Could not add reservation because the user has an existing reservation on that day and time.");
                else if (reservation.getRoomId() == roomId)
                    throw new IllegalArgumentException("Could not add reservation because the user is already on the waitlist for that room.");
                conflictingReservations.add(reservation);
            }
        }

        return conflictingReservations;
    }

    //Returns the next position available in the waitlist for this room and time (lowest position is 0)
    private static int getPosition(String day, int startTimeMillis, int endTimeMillis, int roomId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = ReservationTDG.findInRange(day, startTimeMillis, endTimeMillis, roomId);
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

        if (resultSet == null)
            return 0;
        else {
            while (resultSet.next()) {
                int resId = resultSet.getInt("reservationId");
                int resRoomId = resultSet.getInt("roomId");
                int studentId = resultSet.getInt("studentId");
                String weekDay = resultSet.getString("weekDay");
                int startTime = resultSet.getInt("startTime");
                int endTime = resultSet.getInt("endTime");
                int position = resultSet.getInt("position");

                reservationList.add(new Reservation(resId, resRoomId, studentId, weekDay, startTime, endTime, position));
                ReservationIdentityMap.addRes(new Reservation(resId, resRoomId, studentId, weekDay, startTime, endTime, position));
            }
            return reservationList.size();
        }
    }

    public static void set(Reservation reservation, int roomId, int studentId, String day, int startTime, int endTime, int position) throws ClassNotFoundException, SQLException {
        readWriteLock.writeLock().lock();

        try
        {
            reservation.setRoomId(roomId);
            reservation.setStudentId(studentId);
            reservation.setDay(day);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            reservation.setPosition(position);

            ReservationIdentityMap.set(reservation, reservation.getId());
            UnitOfWork.registerDirty(reservation);
            UnitOfWork.commit();
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void erase(Reservation reservation) throws ClassNotFoundException, SQLException {
        readWriteLock.writeLock().lock();

        try {
            eraseNoLock(reservation);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    private static void eraseNoLock(Reservation reservation) throws ClassNotFoundException, SQLException {
        ReservationIdentityMap.delete(reservation);
        UnitOfWork.registerDelete(reservation);
        UnitOfWork.commit();
    }

    public static void saveToDB(Reservation reservation) throws ClassNotFoundException, SQLException {
        readWriteLock.writeLock().lock();

        try {
            ReservationTDG.insert(reservation);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void deleteToDB(Reservation reservation) throws ClassNotFoundException, SQLException {
        readWriteLock.writeLock().lock();

        try {
            ReservationTDG.delete(reservation);
        } finally {
            readWriteLock.writeLock().unlock();
        }

    }

    public static void updateToDB(Reservation reservation) throws ClassNotFoundException, SQLException {
        readWriteLock.writeLock().lock();

        try {
            ReservationTDG.update(reservation);
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static ArrayList<Reservation> getAllResOfStudent(int studentId) throws SQLException, ClassNotFoundException {
        readWriteLock.readLock().lock();

        try {
            return getAllResOfStudentNoLock(studentId);
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    private static ArrayList<Reservation> getAllResOfStudentNoLock(int studentId) throws SQLException, ClassNotFoundException {
        ResultSet resultSet = ReservationTDG.getAllResOfStudent(studentId);
        ArrayList<Reservation> reservationStudentList = new ArrayList<Reservation>();

        if (resultSet == null)
            return null;
        else {
            while (resultSet.next()) {
                int resId = resultSet.getInt("reservationId");
                int roomId = resultSet.getInt("roomId");
                int resStudentId = resultSet.getInt("studentId");
                String weekDay = resultSet.getString("weekDay");
                int startTime = resultSet.getInt("startTime");
                int endTime = resultSet.getInt("endTime");
                int position = resultSet.getInt("position");

                reservationStudentList.add(new Reservation(resId, roomId, resStudentId, weekDay, startTime, endTime, position));

                if (ReservationIdentityMap.getResFromMap(resId) == null)
                    ReservationIdentityMap.addRes(new Reservation(resId, roomId, resStudentId, weekDay, startTime, endTime, position));
            }
            return reservationStudentList;
        }
    }

    public static ArrayList<Reservation> getFullReservationsForDay(String weekDay) throws SQLException, ClassNotFoundException {
        readWriteLock.readLock().lock();

        ResultSet resultSet = ReservationTDG.getFullReservationsForDay(weekDay);
        ArrayList<Reservation> reservationStudentList = new ArrayList<Reservation>();

        try {
            if (resultSet == null)
                return null;
            else {
                while (resultSet.next()) {
                    int resId = resultSet.getInt("reservationId");
                    int roomId = resultSet.getInt("roomId");
                    int resStudentId = resultSet.getInt("studentId");
                    String day = resultSet.getString("weekDay");
                    int startTime = resultSet.getInt("startTime");
                    int endTime = resultSet.getInt("endTime");
                    int position = resultSet.getInt("position");

                    reservationStudentList.add(new Reservation(resId, roomId, resStudentId, day, startTime, endTime, position));

                    if (ReservationIdentityMap.getResFromMap(resId) == null)
                        ReservationIdentityMap.addRes(new Reservation(resId, roomId, resStudentId, day, startTime, endTime, position));

                }
                return reservationStudentList;
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
    }
}