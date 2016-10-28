package com.server;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.stereotype.Controller;

/**
 * Created by dias on 16-10-28.
 */

@Controller
@RequestMapping("/*")
public class AuthenticationController {

    @RequestMapping(value = "/login", method = RequestMethod.GET,produces = "application/json")
    public @ResponseBody String getMessage(){
        return "Hello there!Welcome";
    }

}
