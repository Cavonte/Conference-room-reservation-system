package IdentityMap;

import Core.Reservation;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Emili on 2016-10-25.
 */

public class ReservationIdentityMap {

    private static Map<Integer, Reservation> mapOfRes = new ConcurrentHashMap<Integer, Reservation>();

    public static void addRes(Reservation res){
        mapOfRes.put(res.getId(), res);
    }

    public static Reservation getResFromMap(int resID)  {
        Reservation res = mapOfRes.get(resID);
        if (res == null){
            return null;
        }
        return res;
    }

    public static void delete(Reservation re){
        int id = re.getId();
        mapOfRes.remove(id);
    }
}
