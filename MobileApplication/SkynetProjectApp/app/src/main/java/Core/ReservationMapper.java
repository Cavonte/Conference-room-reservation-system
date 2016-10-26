package Core;

import java.sql.SQLException;

/**
 * Created by Emili on 2016-10-26.
 */

public class ReservationMapper {

    public ReservationMapper(){
    }

    public static Reservation getReservation(int reId) throws Exception, ClassNotFoundException, SQLException{
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
}
