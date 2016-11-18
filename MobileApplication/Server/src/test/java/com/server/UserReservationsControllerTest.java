package com.server;

import Core.Reservation;
import Core.Student;
import Mapper.ReservationMapper;
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

import java.lang.reflect.Array;
import java.util.ArrayList;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@PrepareForTest({ReservationMapper.class, StudentMapper.class})
@RunWith(PowerMockRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UserReservationsControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserReservationsController userReservationsController;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(userReservationsController).build();
    }

    @Test
    public void normalCase() throws Exception
    {
        Reservation reservation1 = new Reservation(1, 1, 1, "monday", 8, 9, 0);
        Reservation reservation2 = new Reservation(2, 2, 5, "monday", 10, 11, 15);
        Reservation reservation3 = new Reservation(3, 3, 3, "monday", 19, 20, 1);

        ArrayList<Reservation> reservationArrayList = new ArrayList<Reservation>();
        reservationArrayList.add(reservation1);
        reservationArrayList.add(reservation2);
        reservationArrayList.add(reservation3);

        PowerMockito.mockStatic(ReservationMapper.class);
        PowerMockito.mockStatic(StudentMapper.class);

        when(ReservationMapper.getAllResOfStudent(27511876)).thenReturn(reservationArrayList);
        when(StudentMapper.validStudent(27511876)).thenReturn(true);

        this.mockMvc.perform(get("/userReservations?studentId=27511876")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":1,\"roomId\":1,\"studentId\":1,\"day\":\"monday\",\"startTime\":8,\"endTime\":9,\"position\":0},"+
                        "{\"id\":2,\"roomId\":2,\"studentId\":5,\"day\":\"monday\",\"startTime\":10,\"endTime\":11,\"position\":15},"+
                        "{\"id\":3,\"roomId\":3,\"studentId\":3,\"day\":\"monday\",\"startTime\":19,\"endTime\":20,\"position\":1}]"));
    }

    @Test
    public void invalidStudentIdThrowsIllegalArgumentException() throws Exception
    {
        try
        {
            this.mockMvc.perform(get("/userReservations?studentId=1"));
            fail();
        }
        catch (/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void nonExistantStudentIdThrowsIllegalArgumentException() throws Exception
    {
        PowerMockito.mockStatic(StudentMapper.class);

        when(StudentMapper.validStudent(33333333)).thenReturn(false);

        try
        {
            this.mockMvc.perform(get("/userReservations?studentId=33333333"));
            fail();
        }
        catch (/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }
}
