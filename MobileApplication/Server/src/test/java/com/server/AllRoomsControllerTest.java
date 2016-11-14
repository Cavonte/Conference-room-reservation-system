package com.server;

import Core.Room;
import Mapper.RoomMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.lang.reflect.Array;
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
public class AllRoomsControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private AllRoomsController allRoomsController;

    @Before
    public void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(allRoomsController).build();
    }

    @Test
    public void normalCase() throws Exception
    {
        Room room1 = new Room(0, "H123", "Room has a TV.", 2);
        Room room2 = new Room(1, "MBS2.234", "Room has 2 computers and a desk.", 12);
        Room room3 = new Room(2, "H532", "Room has nothing. Don't reserve this.", 0);

        ArrayList<Room> roomArrayList = new ArrayList<Room>();
        roomArrayList.add(room1);
        roomArrayList.add(room2);
        roomArrayList.add(room3);

        PowerMockito.mockStatic(RoomMapper.class);

        when(RoomMapper.getAllData()).thenReturn(roomArrayList);

        this.mockMvc.perform(get("/rooms")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[{\"id\":0,\"roomNumber\":\"H123\",\"description\":\"Room has a TV.\",\"roomSize\":2},"+
                "{\"id\":1,\"roomNumber\":\"MBS2.234\",\"description\":\"Room has 2 computers and a desk.\",\"roomSize\":12},"+
                "{\"id\":2,\"roomNumber\":\"H532\",\"description\":\"Room has nothing. Don't reserve this.\",\"roomSize\":0}]"));
    }
}
