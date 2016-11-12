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
        
        Student s1 = StudentMapper.getData(27526741);

        Student s2 = StudentMapper.getData(27526766);

        Student s3 = StudentMapper.getData(27526711);

        ReservationMapper.makeNew(1, r.getId(), s1.getId(), "Friday", "3pm", "4pm", 1);
        ReservationMapper.makeNew(2, 53, s1.getId(), "Monday", "11am", "12pm", 4);
        ReservationMapper.makeNew(3, 11, s2.getId(), "Tuesday", "6pm", "7pm", 1);
        ReservationMapper.makeNew(4, 11, s3.getId(), "Tuesday", "7pm", "8pm", 1);

        Reservation res = ReservationMapper.getData(1);

        ReservationMapper.set(res, r.getId(), s1.getId(), "Wednesday", "2am", "3am", 2);

        ReservationMapper.getAllData();
        ReservationMapper.getResForStud(s1.getId());

        ReservationMapper.erase(res);
        Reservation res2 = ReservationMapper.getData(2);
        ReservationMapper.erase(res2);
        Reservation res3 = ReservationMapper.getData(3);
        ReservationMapper.erase(res3);
        Reservation res4 = ReservationMapper.getData(4);
        ReservationMapper.erase(res4);
    }
}
