package TDG;

import Core.Reservation;
import IdentityMap.ReservationIdentityMap;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Created by dias on 2016-10-25.
 */

public class ReservationTDG
{
    public static ResultSet findAll() throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations");

        return resultSet;
    }

    public static ResultSet find(int reservationId) throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations WHERE reservationId = " + reservationId);

        return resultSet;
    }

    public static ResultSet findProceedingWaitlist(Reservation reservation) throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations WHERE roomId = " + reservation.getRoomId() + " && weekDay = \"" + reservation.getDay() + "\" && startTime = " + reservation.getStartTime() + " && endTime = " + reservation.getEndTime() + " && position > " + reservation.getPosition());

        return resultSet;
    }

    public static ResultSet findInRange(String day, int startTime, int endTime, int roomId) throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations WHERE roomId = " + roomId + " && weekDay = \"" + day + "\" && startTime = " + startTime + " && endTime = " + endTime);

        return resultSet;
    }

    public static void intitializeIdCounter() throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet results = statement.executeQuery("SELECT * FROM reservations");
        int id = getMaxId(results) + 1;

        Reservation.setIdCounter(id);

        statement.close();
        connection.close();
        results.close();
    }

    private static int getMaxId(ResultSet results) throws SQLException
    {
        int max = 0;
        while(results.next())
        {
            int resId = results.getInt("reservationId");
            int resRoomId = results.getInt("roomId");
            int studentId = results.getInt("studentId");
            String weekDay = results.getString("weekDay");
            int startTime = results.getInt("startTime");
            int endTime = results.getInt("endTime");
            int position = results.getInt("position");

            ReservationIdentityMap.addRes(new Reservation(resId, resRoomId, studentId, weekDay, startTime, endTime, position));

            if(resId > max)
                max = resId;
        }
        return max;
    }

    public static void insert(ArrayList<Reservation> newReservations) throws ClassNotFoundException, SQLException
    {
        if(newReservations.size() > 0)
        {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();

            try
            {
                for(Reservation reservation : newReservations)
                {
                    statement.executeUpdate("INSERT INTO reservations " + "VALUES ( " + reservation.getId() + "," + reservation.getRoomId() + "," + reservation.getStudentId() + ", '" + reservation.getDay() + "','" + reservation.getStartTime() + "','" + reservation.getEndTime() + "'," + reservation.getPosition() + ")");
                }
            }
            finally
            {
                statement.close();
                connection.close();
            }
        }
    }

    public static void update(ArrayList<Reservation> dirtyReservations) throws ClassNotFoundException, SQLException
    {
        if(dirtyReservations.size() > 0)
        {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            try
            {
                for(Reservation reservation : dirtyReservations)
                {
                    statement.executeUpdate("UPDATE reservations " + "SET roomId = " + reservation.getRoomId() + ", studentId = " + reservation.getStudentId() + ", weekDay = '" + reservation.getDay() + "', startTime = '" + reservation.getStartTime() + "', endTime = '" + reservation.getEndTime() + "', position = " + reservation.getPosition() + " WHERE reservationId = " + reservation.getId());
                }
            }
            finally
            {
                statement.close();
                connection.close();
            }
        }
    }

    public static void delete(ArrayList<Reservation> removedReservations) throws ClassNotFoundException, SQLException
    {
        if(removedReservations.size() > 0)
        {
            Connection connection = DatabaseUtils.getConnection();
            Statement statement = connection.createStatement();
            try
            {
                for(Reservation reservation : removedReservations)
                {
                    statement.executeUpdate("DELETE FROM reservations " + "WHERE reservationId = " + reservation.getId());
                }
            }
            finally
            {
                statement.close();
                connection.close();
            }
        }
    }

    public static ResultSet getAllResOfStudent(int studID) throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations WHERE studentId = " + studID);

        return resultSet;
    }

    public static ResultSet getFullReservationsForDay(String weekDay) throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT * FROM reservations WHERE weekDay = \"" + weekDay + "\" && position = 0");

        return resultSet;
    }

    public static void reset() throws ClassNotFoundException, SQLException
    {
        Connection connection = DatabaseUtils.getConnection();
        Statement statement = connection.createStatement();

        statement.executeUpdate("DELETE FROM reservations");
    }
}