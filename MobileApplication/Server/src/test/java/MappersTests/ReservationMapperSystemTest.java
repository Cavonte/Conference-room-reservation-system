package MappersTests;

import Mapper.StudentMapper;
import Core.Reservation;
import Core.Room;
import Core.Student;

import Mapper.ReservationMapper;
import Mapper.RoomMapper;
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
public class ReservationMapperSystemTest {

    @Test
    public void testGetData() throws SQLException, ClassNotFoundException {
        Room r = RoomMapper.getData(34);
        
        Student s = StudentMapper.getData(27526741);

        ReservationMapper.makeNew(1, r.getId(), s.getId(), "Friday", "3pm", "4pm", 1);

        Reservation res = ReservationMapper.getData(1);

        ReservationMapper.set(res, r.getId(), s.getId(), "Wednesday", "2am", "3am", 2);

        ReservationMapper.erase(res);
    }
}
