package Core;

import java.util.concurrent.atomic.AtomicInteger;


/**
 * Created by Emili on 2016-10-24.
 */

public class Reservation {

    private static final AtomicInteger countres = new AtomicInteger(1);
    private int resid;
    private int roomid;
    private int studentid;
    private String day;
    private String startTime;
    private String endTime;
    private int position;

    public void Reservation(int roomid, int studentid, String d, String st, String et, int p){
        resid = countres.incrementAndGet();
        this.roomid = roomid;
        this.studentid = studentid;
        day = d;
        startTime = st;
        endTime = et;
        position = p;
    }

    public int getResid() {
        return resid;
    }

    public int getRoomid() {
        return roomid;
    }

    public int getStudentid() {
        return studentid;
    }

    public String getDay() {
        return day;
    }

    public String getStartTime() {
        return startTime;
    }

    public int getPosition() {
        return position;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setResid(int resid) {
        this.resid = resid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String toString() {
        return ("Reservation id: " + resid +
                "\nRoom id: " + roomid +
                "\nStudent id: " + studentid +
                "\nDay of the Week: " + day +
                "\nStart Time: " + startTime +
                "\nEnd Time: " + endTime +
                "\nPosition: " + position);
    }
}
