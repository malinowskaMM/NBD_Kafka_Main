import org.junit.Before;
import org.junit.Test;
import pl.nbd.hotel.room.*;

import java.util.List;

import static org.junit.Assert.*;

public class RoomRepositoryTest {
    RoomRepository roomRepository;

    @Before
    public void init() {
        roomRepository = new RoomRepository();
        roomRepository.save(new BathRoom("1", 150.0, 2, bathType.SMALL));
        }

    @Test
    public void shouldFindById() {
        Room room = roomRepository.findById("1");
        String info = "1".concat(" 150.0 2");
        assertNotNull(room);
        assertEquals(info,room.getRoomInfo());
    }

    @Test
    public void shouldFindByRoomNumberEquals1() {
        List<Room> rooms = roomRepository.find(room -> room.getRoomNumber().equals("1"));
        assertEquals(1,rooms.size());
    }

    @Test
    public void shouldNotFindByRoomNumberEquals0() {
        List<Room> rooms = roomRepository.find(room -> room.getRoomNumber().equals("0"));
        assertEquals(0,rooms.size());
    }

    @Test
    public void shouldReturnListSizeEqualsTwo() {
        List<Room> rooms = roomRepository.findAll();
        assertEquals(1,rooms.size());
    }

    @Test
    public void shouldAddRoomToRepository() {
        assertEquals(1, roomRepository.getSize());

        RoomManager roomManager = new RoomManager();
        roomManager.addBathRoom("112", 200.0, 2, bathType.JACUZZI);


        assertEquals(2, roomRepository.getSize());
    }

    @Test
    public void shouldRemoveRoomFromRepository() {
        assertEquals(1, roomRepository.getSize());

        Room room = roomRepository.findById("1");
        assertNotNull(room);
        RoomManager roomManager = new RoomManager();
        roomManager.removeRoom(room);

        assertEquals(0, roomRepository.getSize());
    }
}
