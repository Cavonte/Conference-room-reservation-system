package com.server;

import Mapper.ReservationMapper;

import java.sql.SQLException;
import java.util.TimerTask;

/**
 * Created by Sean on 2016-11-18.
 */
public class ResetTask extends TimerTask {
    public void run()
    {
        try
        {
            ReservationMapper.resetReservations();
            System.out.println("Reservations reset.");
        }
        //If the reset is unsuccessful, then nothing can work right, so just crash
        catch(Exception e)
        {
            System.out.println("Error: " + e.getMessage());
            System.exit(0);
        }
    }
}
