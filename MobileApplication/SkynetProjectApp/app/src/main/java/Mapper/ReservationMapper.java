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
    public void makeNew(int roomid, int studentid, String d, String st, String et, int p) throws SQLException {
        Reservation re = new Reservation(roomid, studentid, d, st, et, p);
        ReservationIdentityMap.addRes(re);
        UnitOfWork.registerNew(re);
        ReservationTDG.insert(re);
    }

    public void set(Reservation re, int roomid, int studentid, String d, String st, String et, int p) throws SQLException{
        re.setRoomid(roomid);
        re.setStudentid(studentid);
        re.setDay(d);
        re.setStartTime(st);
        re.setEndTime(et);
        re.setPosition(p);
        UnitOfWork.registerDirty(re);
        UnitOfWork.commit();
        ReservationTDG.update(re);
    }

    public void erase(Reservation re) throws SQLException {
        ReservationIdentityMap.delete(re);
        UnitOfWork.registerDelete(re);
        UnitOfWork.commit();
        ReservationTDG.delete(re);
    }

    public void saveToMap(Reservation re){
        ReservationIdentityMap.addRes(re);
    }

    public void deleteToMap(Reservation re){
        ReservationIdentityMap.delete(re);
    }
}
