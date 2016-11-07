package com.server;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.testng.PowerMockTestCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import static org.mockito.Mockito.*;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.sql.SQLException;

import static org.powermock.api.mockito.PowerMockito.mockStatic;

import Core.Student;
import Mapper.StudentMapper;

//@RunWith(SpringRunner.class)
@PrepareForTest({StudentMapper.class})
@RunWith(PowerMockRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest extends PowerMockTestCase
{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void normalCaseReturnsTrue() throws Exception
    {
        Student student = new Student(123456789, "Test Student", "password");
        mockStatic(StudentMapper.class);
        when(StudentMapper.getData(123456789)).thenReturn(student);

        this.mockMvc.perform(get("/login?username=12345678&password=password")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(true));
    }

    @Test
    public void wrongPasswordReturnsFalse() throws Exception
    {
        Student student = new Student(123456789, "Test Student", "otherpassword");
        mockStatic(StudentMapper.class);
        when(StudentMapper.getData(123456789)).thenReturn(student);

        this.mockMvc.perform(get("/login?username=12345678&password=password")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(false));
    }

    @Test
    public void missingParamsReturnsFalse() throws Exception
    {
        this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(false));
    }

    @Test
    public void tooShortReturnsFalse() throws Exception
    {
        this.mockMvc.perform(get("/login?username=1234&password=pass")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(false));
    }

    @Test
    public void tooLongReturnsFalse() throws Exception
    {
        this.mockMvc.perform(get("/login?username=123456781234567890123&password=password1234567890123")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(false));
    }

    @Test
    public void usernameNotNumReturnsFalse() throws Exception
    {
        this.mockMvc.perform(get("/login?username=username&password=password1234567890123")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(false));
    }
}
