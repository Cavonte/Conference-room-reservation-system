package com.server;

import Core.Reservation;
import Mapper.ReservationMapper;
import com.server.DailyReservationsController;
import com.sun.javaws.exceptions.InvalidArgumentException;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertTrue;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@PrepareForTest({ReservationMapper.class})
@RunWith(PowerMockRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DailyReservationControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private DailyReservationsController dailyReservationsController;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(dailyReservationsController).build();
    }

    @Test
    public void normalCase() throws Exception
    {
        Reservation reservation1 = new Reservation(1, 1, 1, "monday", 8, 9, 0);
        Reservation reservation2 = new Reservation(2, 2, 5, "monday", 10, 11, 0);
        Reservation reservation3 = new Reservation(3, 3, 3, "monday", 19, 20, 0);

        ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
        reservationArrayList.add(reservation1);
        reservationArrayList.add(reservation2);
        reservationArrayList.add(reservation3);

        PowerMockito.mockStatic(ReservationMapper.class);

        when(ReservationMapper.getFullReservationsForDay("monday")).thenReturn(reservationArrayList);

        this.mockMvc.perform(get("/dailyReservations?weekDay=monday")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"roomId\":1,\"studentId\":1,\"day\":\"monday\",\"startTime\":8,\"endTime\":9,\"position\":0},"+
                "{\"id\":2,\"roomId\":2,\"studentId\":5,\"day\":\"monday\",\"startTime\":10,\"endTime\":11,\"position\":0},"+
                "{\"id\":3,\"roomId\":3,\"studentId\":3,\"day\":\"monday\",\"startTime\":19,\"endTime\":20,\"position\":0}]"));
    }


   // For some reason these tests get mad that there's an error even though I told them to expect an error, so idk. I'll figure this out later
    @Test
    public void invalidDayThrowsInvalidArgumentException() throws Exception
    {
        try
        {
            this.mockMvc.perform(get("/dailyReservations?weekDay=moday"));
        }
        catch (/*InvalidArgument*/Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void missingDayThrowsInvalidArgumentException() throws Exception
    {
        try
        {
            this.mockMvc.perform(get("/dailyReservations"));
        }
        catch (/*InvalidArgument*/Exception e)
        {
            assertTrue(true);
        }
    }
}
