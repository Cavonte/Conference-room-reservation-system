package com.server;

import Core.Student;
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
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


//@RunWith(SpringRunner.class)
@PrepareForTest({StudentMapper.class})
@RunWith(PowerMockRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest
{

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AuthenticationController authenticationController;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(authenticationController).build();
    }

    @Test
    public void normalCaseReturnsTrue() throws Exception
    {
        Student student = new Student(123456789, "Test Student", "password");
        PowerMockito.mockStatic(StudentMapper.class);

        when(StudentMapper.getData(123456789)).thenReturn(student);

        this.mockMvc.perform(get("/login?username=123456789&password=password")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("true"));

    }


    @Test
    public void wrongPasswordReturnsFalse() throws Exception
    {
        Student student = new Student(123456789, "Test Student", "otherpassword");
        PowerMockito.mockStatic(StudentMapper.class);

        when(StudentMapper.getData(123456789)).thenReturn(student);

        this.mockMvc.perform(get("/login?username=123456789&password=password"))
                .andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("false"));
    }


    @Test
    public void missingParamsReturnsFalse() throws Exception
    {
        this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void tooShortReturnsFalse() throws Exception
    {
        this.mockMvc.perform(get("/login?username=1234&password=pass")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void tooLongReturnsFalse() throws Exception
    {
        this.mockMvc.perform(get("/login?username=123456781234567890123&password=password1234567890123")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("false"));
    }

    @Test
    public void usernameNotNumReturnsFalse() throws Exception
    {
        this.mockMvc.perform(get("/login?username=username&password=password1234567890123")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("false"));
    }
}
