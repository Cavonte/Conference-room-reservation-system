package com.server;

import Core.Reservation;
import Mapper.ReservationMapper;
import Mapper.RoomMapper;
import Mapper.StudentMapper;
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
@PrepareForTest({ReservationMapper.class, StudentMapper.class, RoomMapper.class})
@RunWith(PowerMockRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ModifyReservationControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ModifyReservationController modifyReservationController;

    @Before
    public void setup() throws Exception
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(modifyReservationController).build();

        PowerMockito.mockStatic(ReservationMapper.class);
        PowerMockito.mockStatic(StudentMapper.class);
        PowerMockito.mockStatic(RoomMapper.class);

        Reservation reservation1 = new Reservation(1, 1, 12345678, "monday", 8, 9, 0);
        Reservation reservation2 = new Reservation(2, 2, 12345678, "tuesday", 10, 11, 15);
        Reservation reservation3 = new Reservation(3, 3, 12345678, "wednesday", 19, 20, 1);

        ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
        reservationArrayList.add(reservation1);
        reservationArrayList.add(reservation2);
        reservationArrayList.add(reservation3);

        ArrayList<Reservation> emptyArrayList = new ArrayList<Reservation>();

        when(ReservationMapper.modifyReservation(true, reservation1, 0, 12345678, "Monday", 12, 13)).thenReturn(0);
        when(ReservationMapper.getAllResOfStudent(12345678)).thenReturn(reservationArrayList);
        when(ReservationMapper.getAllResOfStudent(22222222)).thenReturn(emptyArrayList);
        when(StudentMapper.validStudent(12345678)).thenReturn(true);
        when(StudentMapper.validStudent(12345679)).thenReturn(false);
        when(RoomMapper.validRoom(0)).thenReturn(true);
        when(RoomMapper.validRoom(1)).thenReturn(false);
    }

    @Test
    public void normalCaseReturnsPosition() throws Exception
    {
        this.mockMvc.perform(post("/modifyReservation").param("oldReservationId", "1").param("newRoomId", "0").param("studentId", "12345678").param("newDay", "Monday").param("newStartTime", "12").param("newEndTime", "13").param("reservation", "true")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("0"));
    }


    @Test
    public void tooBigRangeThrowsIllegalArgumentException() throws Exception
    {
        try
        {
            this.mockMvc.perform(post("/modifyReservation").param("oldReservationId", "1").param("newRoomId", "0").param("studentId", "12345678").param("newDay", "Monday").param("newStartTime", "12").param("newEndTime", "10").param("reservation", "true"));
            fail();
        }
        catch(/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void notRealStudent() throws Exception
    {
        try
        {
            this.mockMvc.perform(post("/modifyReservation").param("oldReservationId", "1").param("newRoomId", "0").param("studentId", "12345679").param("newDay", "Monday").param("newStartTime", "12").param("newEndTime", "13").param("reservation", "true"));
            fail();
        }
        catch(/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void notRealRoom() throws Exception
    {
        try
        {
            this.mockMvc.perform(post("/modifyReservation").param("oldReservationId", "1").param("newRoomId", "1").param("studentId", "12345678").param("newDay", "Monday").param("newStartTime", "12").param("newEndTime", "13").param("reservation", "true"));
            fail();
        }
        catch(/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void invalidTimes() throws Exception
    {
        try
        {
            this.mockMvc.perform(post("/modifyReservation").param("oldReservationId", "1").param("newRoomId", "0").param("studentId", "12345678").param("newDay", "Monday").param("newStartTime", "-1").param("newEndTime", "0").param("reservation", "true"));
            fail();
        }
        catch(/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void invalidDay() throws Exception
    {
        try
        {
            this.mockMvc.perform(post("/modifyReservation").param("oldReservationId", "1").param("newRoomId", "0").param("studentId", "12345678").param("newDay", "341").param("newStartTime", "12").param("newEndTime", "13").param("reservation", "true"));
            fail();
        }
        catch(/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void mismatchedStudentAndReservationIdThrowsIllegalArgumentException() throws Exception
    {
        try
        {
            this.mockMvc.perform(post("/modifyReservation").param("oldReservationId", "1").param("newRoomId", "0").param("studentId", "22222222").param("newDay", "monday").param("newStartTime", "12").param("newEndTime", "13").param("reservation", "true"));
            fail();
        }
        catch(/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }
}