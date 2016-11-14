package Mapper;

import Core.Reservation;
import IdentityMap.ReservationIdentityMap;
import TDG.ReservationTDG;
import UnitOfWork.UnitOfWork;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Emili on 2016-10-26.
 */

public class ReservationMapper {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public ReservationMapper(){
    }

    public static Reservation getData(int resId) throws ClassNotFoundException, SQLException
    {
        readWriteLock.readLock().lock();

        try
        {
            Reservation reservation = ReservationIdentityMap.getResFromMap(resId);
            if(reservation != null)
            {
                return reservation;
            }
            else
            {
                ResultSet resultSet = ReservationTDG.find(resId);

                if (resultSet.next())
                {
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
                }
                else
                {
                    return null;
                }
            }
        }
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }

    public static ArrayList<Reservation> getAllData() throws SQLException, ClassNotFoundException
    {
        readWriteLock.readLock().lock();

        try
        {
            ResultSet resultSet = ReservationTDG.findAll();
            ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

            if (resultSet == null)
                return null;
            else
            {
                while (resultSet.next())
                {
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
        }
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }

    public static int makeNew(int roomId, int studentId, String day, int startTime, int endTime) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            int position = getPosition(day, startTime, endTime, roomId);
            Reservation reservation = new Reservation(Reservation.getNextId(), roomId, studentId, day, startTime, endTime, position);
            ReservationIdentityMap.addRes(reservation);
            UnitOfWork.registerNew(reservation);
            UnitOfWork.commit();
            return position;
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    //Returns the next position available in the waitlist for this room and time (lowest position is 0
    private static int getPosition(String day, int startTimeMillis, int endTimeMillis, int roomId) throws SQLException, ClassNotFoundException
    {
        ResultSet resultSet = ReservationTDG.findInRange(day, startTimeMillis, endTimeMillis, roomId);
        ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

        if (resultSet == null)
            return 0;
        else
        {
            while (resultSet.next())
            {
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

    public static void set(Reservation reservation, int roomId, int studentId, String day, int startTime, int endTime, int position) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            reservation.setRoomId(roomId);
            reservation.setStudentId(studentId);
            reservation.setDay(day);
            reservation.setStartTime(startTime);
            reservation.setEndTime(endTime);
            reservation.setPosition(position);
            UnitOfWork.registerDirty(reservation);
            UnitOfWork.commit();
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void erase(Reservation reservation) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            ReservationIdentityMap.delete(reservation);
            UnitOfWork.registerDelete(reservation);
            UnitOfWork.commit();
        }
        finally
        {
            readWriteLock.writeLock().unlock();

        }
    }

    public static void saveToDB(Reservation reservation) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            ReservationTDG.insert(reservation);
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void deleteToDB(Reservation reservation) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            ReservationTDG.delete(reservation);
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }

    }

    public static void updateToDB(Reservation reservation) throws ClassNotFoundException, SQLException
    {
        readWriteLock.writeLock().lock();

        try
        {
            ReservationTDG.update(reservation);
        }
        finally
        {
            readWriteLock.writeLock().unlock();
        }
    }

    public static ArrayList<Reservation> getResForStud(int studentId) throws SQLException, ClassNotFoundException
    {
        readWriteLock.readLock().lock();

        ResultSet resultSet = ReservationTDG.getAllResOfStudent(studentId);
        ArrayList<Reservation> reservationStudentList = new ArrayList<Reservation>();

        try
        {
            if (resultSet == null)
                return null;
            else
            {
                while (resultSet.next())
                {
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
        finally
        {
            readWriteLock.readLock().unlock();
        }
    }
}
