package com.server;

import Core.Room;
import Mapper.RoomMapper;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;

/**
 * Created by dias on 16-10-28.
 */

@RestController
public class AllRoomsController {

    @RequestMapping(value = "/rooms", method = RequestMethod.GET,produces = "application/json")
    public Object[] getAllRooms() throws SQLException, ClassNotFoundException
    {
        //I don't like working with arrays, but it works much better with JSON.
        //This should be converted back to an ArrayList on the front end.
        return RoomMapper.getAllData().toArray();
    }
}
