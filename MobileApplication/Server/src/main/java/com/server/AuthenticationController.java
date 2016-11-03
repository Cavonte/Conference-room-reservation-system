package com.server;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import Core.Student;

import Mapper.StudentMapper;
import netscape.security.Principal;

/**
 * Created by dias on 16-10-28.
 */

@RestController
public class AuthenticationController {

    @RequestMapping(value = "/login", method = RequestMethod.GET,produces = "application/json")
    public boolean login(@RequestParam(value="username", defaultValue="") String username, @RequestParam(value="password", defaultValue="") String password){
        if(!areValidFormat(username, password))
            return false;

        password = encrypt(password);

        //if log in info correct
        int studentId = Integer.parseInt(username);
        Student student = StudentMapper.getData(studentId);//This is wrong now, but I am assuming the method will change once we discuss what the primary keys for the maps should be
        if(!isValidPassword(student, password))
            return false;

        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(username, password));
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
        if(username.length() < 8 || username.length() > 20 || password.length() < 8 || password.length() > 20)
            return false;
        try {
            Integer.parseInt(username);
            return true;
        }
        catch(NumberFormatException e)
        {
            return false;
        }
    }

    /**
     * Unimplemented method to encrypt the password before it can be checked against the encrypted passwords in the database
     * @param password
     * @return the encrypted password as a String
     */
    private String encrypt(String password)
    {
        String encryptedPassword = password;
        return encryptedPassword;
    }

    private boolean isValidPassword(Student student, String password)
    {
        if(student == null || !password.equals(student.getPassword()))
            return false;
        return true;
    }
}
