package com.server;

import Core.Room;
import Core.Student;
import Mapper.RoomMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;
import java.util.ArrayList;

@RestController
public class RoomController {

    @Autowired
    RoomMapper mockMapper;

    @RequestMapping(value = "/room", method = RequestMethod.GET,produces = "application/json")
    public String display(@RequestParam(value="roomId", defaultValue="") int roomId) throws ClassNotFoundException, SQLException
    {
        Room room = RoomMapper.getData(roomId);

        if(room != null){
            return room.toString();
        }
        else{
            return "";
        }
    }

    @RequestMapping(value = "/roomAll", method = RequestMethod.GET,produces = "application/json")
    public String displayAll() throws ClassNotFoundException, SQLException
    {
        ArrayList<Room> rooms = RoomMapper.getAllData();

        if(rooms.size() == 0){
            return "";
        }
        else{
            return rooms.toString();
        }
    }



}
