package com.server;

import Core.Student;
import Core.Room;
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

import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringRunner.class)
@PrepareForTest({ReservationMapper.class, StudentMapper.class, RoomMapper.class})
@RunWith(PowerMockRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class NewReservationControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private NewReservationController newReservationController;

    @Before
    public void setup() throws Exception
    {
        this.mockMvc = MockMvcBuilders.standaloneSetup(newReservationController).build();

        PowerMockito.mockStatic(ReservationMapper.class);
        PowerMockito.mockStatic(StudentMapper.class);
        PowerMockito.mockStatic(RoomMapper.class);

        Student student = new Student(123, "James Smith", "password");
        Room room = new Room(12, "H523", "Description", 4);

        when(ReservationMapper.makeNew(0, 0, "Monday", 12, 13)).thenReturn(0);
        when(StudentMapper.getData(0)).thenReturn(student);
        when(StudentMapper.getData(1)).thenReturn(null);
        when(RoomMapper.getData(0)).thenReturn(room);
        when(RoomMapper.getData(1)).thenReturn(null);
    }

    @Test
    public void normalCaseReturnsPosition() throws Exception
    {
        this.mockMvc.perform(post("/reservation").param("roomId", "0").param("studentId", "0").param("day", "Monday").param("startTime", "12").param("endTime", "13")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("0"));
    }


    @Test
    public void tooBigRangeInvalid() throws Exception
    {
        this.mockMvc.perform(post("/reservation").param("roomId", "0").param("studentId", "0").param("day", "Monday").param("startTime", "12").param("endTime", "10")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("-1"));
    }

    @Test
    public void notRealStudent() throws Exception
    {
        this.mockMvc.perform(post("/reservation").param("roomId", "0").param("studentId", "1").param("day", "Monday").param("startTime", "-1").param("endTime", "0")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("-1"));
    }

    @Test
    public void notRealRoom() throws Exception
    {
        this.mockMvc.perform(post("/reservation").param("roomId", "1").param("studentId", "0").param("day", "Monday").param("startTime", "-1").param("endTime", "0")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("-1"));
    }

    @Test
    public void invalidTimes() throws Exception
    {
        this.mockMvc.perform(post("/reservation").param("roomId", "0").param("studentId", "0").param("day", "Monday").param("startTime", "-1").param("endTime", "0")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("-1"));
    }

    @Test
    public void invalidDay() throws Exception
    {
        this.mockMvc.perform(post("/reservation").param("roomId", "0").param("studentId", "0").param("day", "341").param("startTime", "12").param("endTime", "13")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("-1"));
    }
}
