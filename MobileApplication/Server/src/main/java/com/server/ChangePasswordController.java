package com.server;

import Core.Student;
import Mapper.StudentMapper;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

@RestController
public class ChangePasswordController
{
    /**
     * End point that changes the password of the given user to the given password
     * @throws IllegalArgumentException if username or password are in an invalid format
     */
    @RequestMapping(value = "/changePassword", method = RequestMethod.POST,produces = "application/json")
    public boolean login(@RequestParam(value="username", defaultValue="") String username, @RequestParam(value="password", defaultValue="") String newPassword) throws ClassNotFoundException, SQLException
    {
        if(!areValidFormat(username, newPassword))
            throw new IllegalArgumentException("Student id or password were in an invalid format.");

        newPassword = Student.encryptPassword(newPassword);

        //if log in info correct
        int studentId = Integer.parseInt(username);

        Student student = StudentMapper.getData(studentId);

        StudentMapper.set(student, student.getName(), newPassword);
        return true;
    }

    /**
     * Method to before basic validation on the length of the parameters, in case the front end did not
     * @param username
     * @param password
     * @return true if it validates properly
     */
    private boolean areValidFormat(String username, String password)
    {
        return Student.isValidUsername(username) && Student.isValidPassword(password);
    }
}