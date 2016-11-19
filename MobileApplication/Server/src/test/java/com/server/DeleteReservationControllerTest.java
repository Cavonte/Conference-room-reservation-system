package com.server;

import Core.Reservation;
import Mapper.ReservationMapper;
import org.junit.Before;
import org.junit.Test;
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

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringRunner.class)
@PrepareForTest({ReservationMapper.class})
@RunWith(PowerMockRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class DeleteReservationControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private DeleteReservationController deleteReservationController;

    @Before
    public void setup() throws Exception
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(deleteReservationController).build();

        PowerMockito.mockStatic(ReservationMapper.class);

        Reservation reservation1 = new Reservation(1, 1, 12345678, "monday", 8, 9, 0);
        Reservation reservation2 = new Reservation(2, 2, 12345678, "tuesday", 10, 11, 15);
        Reservation reservation3 = new Reservation(3, 3, 12345678, "wednesday", 19, 20, 1);

        ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
        reservationArrayList.add(reservation1);
        reservationArrayList.add(reservation2);
        reservationArrayList.add(reservation3);

        ArrayList<Reservation> emptyArrayList = new ArrayList<Reservation>();

        when(ReservationMapper.getAllResOfStudent(12345678)).thenReturn(reservationArrayList);
        when(ReservationMapper.getAllResOfStudent(12345679)).thenReturn(emptyArrayList);
    }

    @Test
    public void normalCaseReturnsPosition() throws Exception
    {
        this.mockMvc.perform(post("/deleteReservation").param("studentId", "12345678").param("reservationId", "2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("true"));
    }


    @Test
    public void mismatchedStudentAndReservationIdThrowsIllegalArgumentException() throws Exception
    {
        try
        {
            this.mockMvc.perform(post("/deleteReservation").param("studentId", "12345679").param("reservationId", "1"));
            fail();
        }
        catch(/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }
}
