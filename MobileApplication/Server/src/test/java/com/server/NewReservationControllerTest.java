package com.server;

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

import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringRunner.class)
@PrepareForTest({ReservationMapper.class})
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
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(newReservationController).build();
    }

    @Test
    public void normalCaseReturnsPosition() throws Exception
    {
        PowerMockito.mockStatic(ReservationMapper.class);

        when(ReservationMapper.makeNew(0, 0, "Monday", 12, 13)).thenReturn(0);

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
