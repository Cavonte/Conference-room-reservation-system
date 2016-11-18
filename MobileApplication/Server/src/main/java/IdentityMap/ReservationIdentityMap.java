package IdentityMap;

import Core.Reservation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Emili on 2016-10-25.
 */

public class ReservationIdentityMap {

    private static Map<Integer, Reservation> mapOfReservations = new ConcurrentHashMap<Integer, Reservation>();

    public static void addRes(Reservation reservation){
        mapOfReservations.put(reservation.getId(), reservation);
    }

    public static Reservation getResFromMap(int reservationId)  {
        Reservation reservation = mapOfReservations.get(reservationId);
        if (reservation == null){
            return null;
        }
        return reservation;
    }

    public static void delete(Reservation reservation){
        int id = reservation.getId();
        mapOfReservations.remove(id);
    }

    public static void set(Reservation reservation, int reservationId)
    {
        mapOfReservations.put(reservationId, reservation);
    }
}