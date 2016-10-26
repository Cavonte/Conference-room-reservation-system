package IdentityMap;

import java.util.HashMap;
import java.util.Map;

import Core.Reservation;

/**
 * Created by Emili on 2016-10-25.
 */

public class ReservationIdentityMap {

    private static Map<Integer, Reservation> mapOfRes;

    public ReservationIdentityMap(){
        mapOfRes = new HashMap<Integer, Reservation>();
    }

    public static void addRes(Reservation res){
        mapOfRes.put(res.getResid(), res);
    }

    public static Reservation getResFromMap(int resID) throws ObjectNotFoundException {
        Reservation res = mapOfRes.get(resID);
        if (res == null){
            throw new ObjectNotFoundException("Reservation not found.");
        }
        return res;
    }
}
