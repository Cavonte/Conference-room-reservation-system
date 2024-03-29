package Core;

import org.springframework.util.StringUtils;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Emili on 2016-10-24.
 */

public class Reservation extends DomainObject{
    private static final AtomicInteger idCounter = new AtomicInteger();
    private static final String[] VALID_DAYS = {"saturday", "sunday", "monday", "tuesday", "wednesday", "thursday", "friday"};
    private int roomId;
    private int studentId;
    private String day;
    private int startTime;
    private int endTime;
    private int position;

    public Reservation(int resId, int roomId, int studentId, String day, int startTime, int endTime, int position)
    {
        super(resId);
        this.roomId = roomId;
        this.studentId = studentId;
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.position = position;
    }

    public static int getNextId()
    {
        return idCounter.getAndIncrement();
    }

    public int getRoomId()
    {
        return roomId;
    }

    public int getStudentId()
    {
        return studentId;
    }

    public String getDay()
    {
        return day;
    }

    public int getStartTime()
    {
        return startTime;
    }

    public int getPosition()
    {
        return position;
    }

    public int getEndTime()
    {
        return endTime;
    }

    public static void setIdCounter(int value)
    {
        idCounter.set(value);
    }

    public void setRoomId(int roomId)
    {
        this.roomId = roomId;
    }

    public void setStudentId(int studentId)
    {
        this.studentId = studentId;
    }

    public void setDay(String day)
    {
        this.day = day;
    }

    public void setStartTime(int startTime)
    {
        this.startTime = startTime;
    }

    public void setEndTime(int endTime)
    {
        this.endTime = endTime;
    }

    public void setPosition(int position)
    {
        this.position = position;
    }

    public String toString() {
        return ("Reservation id: " + super.getId() +
                "\nRoom id: " + roomId +
                "\nStudent id: " + studentId +
                "\nDay of the Week: " + day +
                "\nStart Time: " + startTime +
                "\nEnd Time: " + endTime +
                "\nPosition: " + position);
    }

    public static boolean validDay(String weekDay)
    {
        if(StringUtils.isEmpty(weekDay))
            return false;

        String lowerDay = weekDay.toLowerCase();
        for(String validDay: VALID_DAYS)
        {
            if(lowerDay.equals(validDay))
                return true;
        }

        return false;
    }

    public static boolean validTime(int time)
    {
        return (time >= 7 && time <= 24);
    }
}
