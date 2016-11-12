package UnitTestPattern;

import Core.Reservation;
import Core.Room;
import Mapper.ReservationMapper;
import Mapper.RoomMapper;
import com.server.ReservationController;
import com.server.RoomController;
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
    public void returnReservation() throws Exception {

        Reservation r = new Reservation(2,2,1,"Monday", "11:00:00", "12:00:00", 0);
        PowerMockito.mockStatic(ReservationMapper.class);


        when(ReservationMapper.getData(2)).thenReturn(r);

        this.mockMvc.perform(get("/reservation?resId=2")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(r.toString()));

    }

    @Test
    public void returnReservationAll() throws Exception {

        PowerMockito.mockStatic(ReservationMapper.class);

        ArrayList<Reservation> reservations = new ArrayList<Reservation>();

        reservations.add(new Reservation(1,1,123123123,"This is a string", "This is another string", "This is another string string",0));
        reservations.add(new Reservation(4,1,123123123,"This is a string", "This is another string", "This is another string string",0));
        reservations.add(new Reservation(5,1,123123123,"This is a string", "This is another string", "This is another string string",0));

        when(ReservationMapper.getAllData()).thenReturn(reservations);

        this.mockMvc.perform(get("/reservationAll")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(reservations.toString()));

    }

    @Test
    public void returnReservationAllError() throws Exception {

        PowerMockito.mockStatic(ReservationMapper.class);

        ArrayList<Reservation> reservations = new ArrayList<Reservation>();
        when(ReservationMapper.getAllData()).thenReturn(reservations);

        this.mockMvc.perform(get("/reservationAll")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(""));

    }

    @Test
    public void returnReservationError() throws Exception {
        Reservation r = new Reservation(3,2,1,"Monday", "11:00:00", "12:00:00", 0);
        PowerMockito.mockStatic(ReservationMapper.class);


        when(ReservationMapper.getData(1)).thenReturn(null);

        this.mockMvc.perform(get("/reservation?resId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(""));

    }



}
