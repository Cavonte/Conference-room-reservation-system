package Mapper;

import java.sql.SQLException;

import Core.Reservation;
import IdentityMap.ReservationIdentityMap;
import TDG.ReservationTDG;
import UnitOfWork.UnitOfWork;

import java.sql.ResultSet;
import java.util.ArrayList;

/**
 * Created by Emili on 2016-10-26.
 */

public class ReservationMapper {

    public ReservationMapper(){
    }

    public static Reservation getData(int reId) throws ClassNotFoundException, SQLException {
        Reservation res = ReservationIdentityMap.getResFromMap(reId);
        if(res != null)
        {
            return res;
        }
        else {

            ResultSet resultSet = ReservationTDG.find(reId);

            if(resultSet.next()){

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
            }
            else{
                throw new SQLException("Error: empty result");
            }

        }
    }

    public static ArrayList<Reservation> getAllData() throws SQLException, ClassNotFoundException {

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

    public static void makeNew(int resid, int roomid, int studentid, String d, String st, String et, int p) throws ClassNotFoundException, SQLException {
        Reservation re = new Reservation(resid, roomid, studentid, d, st, et, p);
        ReservationIdentityMap.addRes(re);
        UnitOfWork.registerNew(re);
        UnitOfWork.commit();
    }

    public static void set(Reservation re, int roomid, int studentid, String d, String st, String et, int p) throws ClassNotFoundException, SQLException{
        re.setRoomid(roomid);
        re.setStudentid(studentid);
        re.setDay(d);
        re.setStartTime(st);
        re.setEndTime(et);
        re.setPosition(p);
        UnitOfWork.registerDirty(re);
        UnitOfWork.commit();
    }

    public static void erase(Reservation re) throws ClassNotFoundException, SQLException {
        ReservationIdentityMap.delete(re);
        UnitOfWork.registerDelete(re);
        UnitOfWork.commit();
    }

    public static void saveToDB(Reservation re) throws ClassNotFoundException, SQLException {
        ReservationTDG.insert(re);
    }

    public static void deleteToDB(Reservation re) throws ClassNotFoundException, SQLException{
        ReservationTDG.delete(re);
    }

    public static void updateToDB(Reservation re) throws ClassNotFoundException, SQLException{
        ReservationTDG.update(re);
    }

    public static ArrayList<Reservation> getResForStud(int sid) throws SQLException, ClassNotFoundException {

        ResultSet resultSet = ReservationTDG.getAllResOfStudent(sid);
        ArrayList<Reservation> reservationStudentList = new ArrayList<Reservation>();

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

}
