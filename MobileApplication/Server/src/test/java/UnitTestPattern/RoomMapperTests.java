package UnitTestPattern;

import Core.Reservation;
import Core.Room;
import Mapper.ReservationMapper;
import Mapper.RoomMapper;
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

@PrepareForTest({RoomMapper.class})
@RunWith(PowerMockRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RoomMapperTests {


    @Autowired
    private MockMvc mockMvc;

    @Mock
    private RoomMapper mockMapper = Mockito.mock(RoomMapper.class);

    @InjectMocks
    private RoomController mockRoomController;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        this.mockMvc = MockMvcBuilders.standaloneSetup(mockRoomController).build();
    }

    @Test
    public void returnRoom() throws Exception {
        Room room = new Room(1, "LB-351", "This room is located in the Webster Library. It has 1 table, a 46\" LCD screen with an HDMI port, an USB camera and multiple power outlets.\n", 6);
        PowerMockito.mockStatic(RoomMapper.class);


        when(RoomMapper.getData(1)).thenReturn(room);

        this.mockMvc.perform(get("/room?roomId=1")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(room.toString()));

    }

    @Test
    public void returnAll() throws Exception {
        PowerMockito.mockStatic(RoomMapper.class);

        ArrayList<Room> rooms = new ArrayList<Room>();
        rooms.add(new Room(12, "LB-351", "This room is located in the Webster Library. It has 1 table, a 46\" LCD screen with an HDMI port, an USB camera and multiple power outlets.\n", 6));
        rooms.add(new Room(13, "LB-351", "This room is located in the Webster Library. It has 1 table, a 46\" LCD screen with an HDMI port, an USB camera and multiple power outlets.\n", 6));
        rooms.add(new Room(14, "LB-351", "This room is located in the Webster Library. It has 1 table, a 46\" LCD screen with an HDMI port, an USB camera and multiple power outlets.\n", 6));

        when(RoomMapper.getAllData()).thenReturn(rooms);
        this.mockMvc.perform(get("/roomAll")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(rooms.toString()));
    }

    @Test
    public void returnRoomsAllError() throws Exception {

        PowerMockito.mockStatic(RoomMapper.class);

        ArrayList<Room> rooms = new ArrayList<Room>();
        when(RoomMapper.getAllData()).thenReturn(rooms);

        this.mockMvc.perform(get("/roomAll")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(""));

    }

    @Test
    public void returnRoomError() throws Exception {
        Room room = new Room(2, "LB-351", "This room is located in the Library. It has 1 table, a 46\" LCD screen with an HDMI port, an USB camera and multiple power outlets.\n", 6);
        PowerMockito.mockStatic(RoomMapper.class);


        when(RoomMapper.getData(70)).thenReturn(null);

        this.mockMvc.perform(get("/room?roomId=70")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(""));

    }



    }
