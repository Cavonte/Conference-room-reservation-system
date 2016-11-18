package PatternTests.SystemTests;

import Core.Reservation;
import Core.Room;
import Core.Student;
import Mapper.ReservationMapper;
import Mapper.RoomMapper;
import Mapper.StudentMapper;
import TestControllers.ReservationController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@PrepareForTest({ReservationMapper.class})
@RunWith(PowerMockRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ReservationMapperTests {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private ReservationMapper mockMapper = Mockito.mock(ReservationMapper.class);

    @InjectMocks
    private ReservationController mockReservationController;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(mockReservationController).build();
    }

    @Test
    public void testInsertAndGetData() throws SQLException, ClassNotFoundException {
        Room r = RoomMapper.getData(34);

        Student s1 = StudentMapper.getData(27526741);

        Student s2 = StudentMapper.getData(27526766);

        Student s3 = StudentMapper.getData(27526711);

        ReservationMapper.makeNew(r.getId(), s1.getId(), "Friday", 15, 16);
        ReservationMapper.makeNew(53, s1.getId(), "Monday", 11, 12);
        ReservationMapper.makeNew(11, s2.getId(), "Tuesday", 18, 19);
        ReservationMapper.makeNew(11, s3.getId(), "Tuesday", 19, 20);

        Reservation res = ReservationMapper.getData(1);

        ReservationMapper.set(res, r.getId(), s1.getId(), "Wednesday", 14, 15, 2);

        ReservationMapper.getAllData();
        ReservationMapper.getAllResOfStudent(s1.getId());

        ReservationMapper.erase(res);
        Reservation res2 = ReservationMapper.getData(2);
        ReservationMapper.erase(res2);
        Reservation res3 = ReservationMapper.getData(3);
        ReservationMapper.erase(res3);
        Reservation res4 = ReservationMapper.getData(4);
        ReservationMapper.erase(res4);
    }

    @Test
    public void returnReservation() throws Exception {

        Reservation r = new Reservation(2,2,1,"Monday", 11, 12, 0);
        PowerMockito.mockStatic(ReservationMapper.class);


        when(ReservationMapper.getData(2)).thenReturn(r);

        this.mockMvc.perform(get("/reservationTest?resId=2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(r.toString()));

    }

    @Test
    public void returnReservationAll() throws Exception {

        PowerMockito.mockStatic(ReservationMapper.class);

        ArrayList<Reservation> reservations = new ArrayList<Reservation>();

        reservations.add(new Reservation(1,1,123123123,"Monday", 12, 13, 0));
        reservations.add(new Reservation(4,1,123123123,"Tuesday", 14, 15, 0));
        reservations.add(new Reservation(5,1,123123123,"Wednesday", 16, 17, 0));

        when(ReservationMapper.getAllData()).thenReturn(reservations);

        this.mockMvc.perform(get("/reservationAllTest")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(reservations.toString()));

    }

    @Test
    public void returnReservationAllError() throws Exception {

        PowerMockito.mockStatic(ReservationMapper.class);

        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        when(ReservationMapper.getAllData()).thenReturn(reservations);

        this.mockMvc.perform(get("/reservationAllTest")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(""));
    }

    @Test
    public void returnReservationError() throws Exception {
        Reservation r = new Reservation(3,2,1,"Monday", 11, 12, 0);
        PowerMockito.mockStatic(ReservationMapper.class);


        when(ReservationMapper.getData(1)).thenReturn(null);

        this.mockMvc.perform(get("/reservationTest?resId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(""));

    }



}
