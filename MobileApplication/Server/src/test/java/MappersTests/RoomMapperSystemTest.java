package MappersTests;

import Core.Room;
import Mapper.RoomMapper;
import IdentityMap.RoomIdentityMap;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.SQLException;


/**
 * Created by Emili on 2016-11-05.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class RoomMapperSystemTest {

    @Test
    public void testGetData() throws SQLException, ClassNotFoundException {
        RoomMapper.makeNew(56, "b", "c", 2);

        Room r = RoomIdentityMap.getRoomFromMap(56);

        RoomMapper.set(r, "potato", "banana", 5);

        RoomMapper.erase(r);
    }

}
