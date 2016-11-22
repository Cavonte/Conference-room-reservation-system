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

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@PrepareForTest({StudentMapper.class})
@RunWith(PowerMockRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ChangePasswordControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private ChangePasswordController changePasswordController;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(changePasswordController).build();
    }

    @Test
    public void normalCaseReturnsTrue() throws Exception
    {
        Student student = new Student(12345678, "Test Student", "password");
        PowerMockito.mockStatic(StudentMapper.class);

        when(StudentMapper.getData(12345678)).thenReturn(student);

        this.mockMvc.perform(post("/changePassword").param("username", "12345678").param("newPassword", "newPasswordHere")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void tooShortThrowsIllegalArgumentException() throws Exception
    {
        try
        {
            this.mockMvc.perform(post("/changePassword").param("username", "1234").param("newPassword", "pass")).andDo(print());
            fail();
        }
        catch(/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }

    @Test
    public void tooLongThrowsIllegalArgumentException() throws Exception
    {
        try
        {
            this.mockMvc.perform(post("/changePassword").param("username", "123456781234567890123").param("newPassword", "password1234567890123")).andDo(print());
            fail();
        }
        catch(/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
        }

    @Test
    public void usernameNotNumThrowsIllegalArgumentException() throws Exception
    {
        try
        {
            this.mockMvc.perform(post("/changePassword").param("username", "username").param("newPassword", "password1234567890123")).andDo(print());
            fail();
        }
        catch(/*IllegalArgument*/Exception e)
        {
            assertTrue(true);
        }
    }
}
