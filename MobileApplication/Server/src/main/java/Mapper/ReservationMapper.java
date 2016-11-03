package Mapper;

import java.sql.SQLException;

import Core.Reservation;
import Core.Student;
import IdentityMap.ReservationIdentityMap;

import IdentityMap.StudentIdentityMap;
import TDG.ReservationTDG;
import TDG.StudentTDG;
import UnitOfWork.UnitOfWork;

/**
 * Created by Emili on 2016-10-26.
 */

public class ReservationMapper {

    public ReservationMapper(){
    }

    public static Reservation getData(int reId) throws Exception, ClassNotFoundException, SQLException{
        Reservation res = ReservationIdentityMap.getResFromMap(reId);
        if(res != null)
        {
            return res;
        }
        else {
            Reservation reservationDB = ReservationTDG.find(reId);
            ReservationIdentityMap.addRes(reservationDB);
            return reservationDB;
        }
    }
    public void makeNew(int roomid, int studentid, String d, String st, String et, int p) throws ClassNotFoundException,SQLException {
        Reservation re = new Reservation(roomid, studentid, d, st, et, p);
        ReservationIdentityMap.addRes(re);
        UnitOfWork.registerNew(re);
        UnitOfWork.commit();
    }

    public void set(Reservation re, int roomid, int studentid, String d, String st, String et, int p) throws ClassNotFoundException,SQLException{
        re.setRoomid(roomid);
        re.setStudentid(studentid);
        re.setDay(d);
        re.setStartTime(st);
        re.setEndTime(et);
        re.setPosition(p);
        ReservationIdentityMap.addRes(re);
        UnitOfWork.registerDirty(re);
        UnitOfWork.commit();
    }

    public void erase(Reservation re) throws ClassNotFoundException,SQLException {
        ReservationIdentityMap.delete(re);
        UnitOfWork.registerDelete(re);
        UnitOfWork.commit();
    }

    public static void saveToDB(Reservation re) throws ClassNotFoundException,SQLException {
        ReservationTDG.insert(re);
    }

    public static void deleteToDB(Reservation re) throws ClassNotFoundException,SQLException{
        ReservationTDG.delete(re);
    }

    public static void updateToDB(Reservation re) throws ClassNotFoundException,SQLException{
        ReservationTDG.update(re);
    }
}
