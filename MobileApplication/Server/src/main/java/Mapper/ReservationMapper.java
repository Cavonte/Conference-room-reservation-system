package Mapper;

import java.sql.SQLException;

import Core.Reservation;
import IdentityMap.ReservationIdentityMap;
import TDG.ReservationTDG;
import UnitOfWork.UnitOfWork;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Created by Emili on 2016-10-26.
 */

public class ReservationMapper {

    private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public ReservationMapper(){
    }

    public static Reservation getData(int reId) throws ClassNotFoundException, SQLException {

        readWriteLock.readLock().lock();

        try {
            Reservation res = ReservationIdentityMap.getResFromMap(reId);
            if (res != null) {
                return res;
            } else {

                ResultSet resultSet = ReservationTDG.find(reId);

                if (resultSet.next()) {

                    int resId = resultSet.getInt("reservationId");
                    int roomId = resultSet.getInt("roomId");
                    int studentId = resultSet.getInt("studentId");
                    String weekDay = resultSet.getString("weekDay");
                    String startTime = resultSet.getString("startTime");
                    String endTime = resultSet.getString("endTime");
                    int position = resultSet.getInt("position");

                    Reservation reservationDB = new Reservation(resId, roomId, studentId, weekDay, startTime, endTime, position);
                    ReservationIdentityMap.addRes(reservationDB);

                    return reservationDB;
                } else {
                    throw new SQLException("Error: empty result");
                }

            }
        }
        finally{
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
                    String startTime = resultSet.getString("startTime");
                    String endTime = resultSet.getString("endTime");
                    int position = resultSet.getInt("position");

                    reservationList.add(new Reservation(resId, roomId, studentId, weekDay, startTime, endTime, position));
                    ReservationIdentityMap.addRes(new Reservation(resId, roomId, studentId, weekDay, startTime, endTime, position));
                }
                return reservationList;
            }
        }
        finally{
            readWriteLock.readLock().unlock();
        }
    }

    public static void makeNew(int resid, int roomid, int studentid, String d, String st, String et, int p) throws ClassNotFoundException, SQLException {

        readWriteLock.writeLock().lock();

        try {
            Reservation re = new Reservation(resid, roomid, studentid, d, st, et, p);
            ReservationIdentityMap.addRes(re);
            UnitOfWork.registerNew(re);
            UnitOfWork.commit();
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static void set(Reservation re, int roomid, int studentid, String d, String st, String et, int p) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            re.setRoomid(roomid);
            re.setStudentid(studentid);
            re.setDay(d);
            re.setStartTime(st);
            re.setEndTime(et);
            re.setPosition(p);
            UnitOfWork.registerDirty(re);
            UnitOfWork.commit();
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static void erase(Reservation re) throws ClassNotFoundException, SQLException {

        readWriteLock.writeLock().lock();

        try {
            ReservationIdentityMap.delete(re);
            UnitOfWork.registerDelete(re);
            UnitOfWork.commit();
        }
        finally{
            readWriteLock.writeLock().unlock();

        }
    }

    public static void saveToDB(Reservation re) throws ClassNotFoundException, SQLException {

        readWriteLock.writeLock().lock();

        try {
            ReservationTDG.insert(re);
        }
        finally {
            readWriteLock.writeLock().unlock();
        }
    }

    public static void deleteToDB(Reservation re) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            ReservationTDG.delete(re);
        }
        finally{
            readWriteLock.writeLock().unlock();
        }

    }

    public static void updateToDB(Reservation re) throws ClassNotFoundException, SQLException{

        readWriteLock.writeLock().lock();

        try {
            ReservationTDG.update(re);
        }
        finally{
            readWriteLock.writeLock().unlock();
        }
    }

    public static ArrayList<Reservation> getResForStud(int sid) throws SQLException, ClassNotFoundException {

        readWriteLock.readLock().lock();


        ResultSet resultSet = ReservationTDG.getAllResOfStudent(sid);
        ArrayList<Reservation> reservationStudentList = new ArrayList<Reservation>();

        try {
            if (resultSet == null)
                return null;
            else {

                while (resultSet.next()) {

                    int resId = resultSet.getInt("reservationId");
                    int roomId = resultSet.getInt("roomId");
                    int studentId = resultSet.getInt("studentId");
                    String weekDay = resultSet.getString("weekDay");
                    String startTime = resultSet.getString("startTime");
                    String endTime = resultSet.getString("endTime");
                    int position = resultSet.getInt("position");

                    reservationStudentList.add(new Reservation(resId, roomId, studentId, weekDay, startTime, endTime, position));

                    if (ReservationIdentityMap.getResFromMap(resId) == null)
                        ReservationIdentityMap.addRes(new Reservation(resId, roomId, studentId, weekDay, startTime, endTime, position));

                }
                return reservationStudentList;
            }
        }
        finally{
            readWriteLock.readLock().unlock();
        }


    }

}
