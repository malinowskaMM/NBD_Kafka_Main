import org.junit.Before;
import org.junit.Test;
import pl.nbd.hotel.room.*;
import pl.nbd.hotel.room.cache.RepositoryDecorator;

import java.util.List;

import static org.junit.Assert.*;

public class RoomRepositoryTest {
    RoomRepository roomRepository;
    RepositoryDecorator repositoryDecorator;
    Room roomExample;

    @Before
    public void init() {
        roomRepository = new RoomRepository();
        roomRepository.mongoDatabase.drop();
        repositoryDecorator = new RepositoryDecorator(roomRepository);
        repositoryDecorator.flush();
        roomRepository.save(new Room("1", 150.0, 2));
        roomRepository.save(new Room("2", 150.0, 1));
        roomExample = new Room("3", 150.0, 2);
        repositoryDecorator.save(roomExample);
    }

    @Test
    public void shouldFindById() {
        Room room = roomRepository.findById("1");
        String info = "1".concat(" 150.0 2");
        assertNotNull(room);
        assertEquals(info, room.roomInfoGet());
    }

    @Test
    public void shouldFindByRoomNumberEquals1() {
        List<Room> rooms = roomRepository.find(room -> room.getRoomNumber().equals("1"));
        assertEquals(1, rooms.size());
    }

    @Test
    public void shouldNotFindByRoomNumberEquals0() {
        List<Room> rooms = roomRepository.find(room -> room.getRoomNumber().equals("0"));
        assertEquals(0, rooms.size());
    }

    @Test
    public void shouldReturnListSizeEqualsTwo() {
        List<Room> rooms = roomRepository.findAll();
        assertEquals(3, rooms.size());
    }

    @Test
    public void shouldAddRoomToRepository() {
        assertEquals(3, roomRepository.getSize());

        RoomManager roomManager = new RoomManager(roomRepository);
        roomManager.addBathRoom("112", 200.0, 2);

        assertEquals(4, roomRepository.getSize());
    }

    @Test
    public void shouldRemoveRoomFromRepository() {
        assertEquals(3, roomRepository.getSize());

        Room room = roomRepository.findById("1");
        assertNotNull(room);
        roomRepository.removeById("1");

        assertEquals(2, roomRepository.getSize());
    }

    @Test
    public void shouldUpdateRoomFromRepository() {
        assertEquals(3, roomRepository.getSize());

        Room room = roomRepository.findById("1");
        assertNotNull(room);
        room.setPrice(359.);
        roomRepository.update(room);
        Room room2 = roomRepository.findById("1");

        assertEquals(room2.roomInfoGet(), room.roomInfoGet());
    }

    @Test
    public void shouldUpdateRoomFromRepositoryManager() {
        assertEquals(3, roomRepository.getSize());
        RoomManager roomManager = new RoomManager(roomRepository);
        roomManager.getRoom("1");
        Room room = roomManager.getRoom("1");
        assertNotNull(room);

        roomManager.updateRoom("1", 359.);
        Room room2 = roomRepository.findById("1");
        assertNotEquals(room2.roomInfoGet(), room.roomInfoGet());

        room.setPrice(359.);
        assertEquals(room2.roomInfoGet(), room.roomInfoGet());
    }

    @Test
    public void shouldRemoveRoomFromRepositoryManager() {
        assertEquals(3, roomRepository.getSize());

        Room room = roomRepository.findById("1");
        RoomManager roomManager = new RoomManager(roomRepository);
        roomManager.removeRoom(room);

        assertEquals(2, roomRepository.getSize());
    }

    @Test
    public void shouldGetItemFromCache() {
        Room room = repositoryDecorator.findById("3");
        assertNotNull(room);
        assertEquals(roomExample.getRoomNumber(), room.getRoomNumber());
        assertEquals(roomExample.getRoomCapacity(), room.getRoomCapacity());
        assertEquals(roomExample.getPrice(), room.getPrice());

        assertNotNull(roomRepository.findById("3"));
    }

    @Test
    public void shouldRemoveItemFromCache() {
        assertNotNull(roomRepository.findById("2"));
        assertNotNull(repositoryDecorator.findById("2"));

        repositoryDecorator.removeById("2");

        assertNull(roomRepository.findById("2"));
        assertNull(repositoryDecorator.findById("2"));
    }

    @Test
    public void shouldGetFromDBIfConnectionWithCacheRefused() {
        assertNotNull(roomRepository.findById("2"));
        assertNotNull(repositoryDecorator.findById("2"));
        repositoryDecorator.closeRedisConnection();

        repositoryDecorator.removeById("2");

        assertNull(roomRepository.findById("2"));
        assertNull(repositoryDecorator.findById("2"));
    }

    @Test
    public void flushCache() {
        Room room = new Room("12", 120.0, 1);
        repositoryDecorator.save(room);
        repositoryDecorator.findAll();
    }

    @Test
    public void shouldFlushItemFromCache() {
        assertNotNull(roomRepository.findById("3"));
        assertNotNull(repositoryDecorator.findById("3"));
        repositoryDecorator.flush();
        assertEquals(0, repositoryDecorator.getSize());

    }

    @Test
    public void shouldCloseConnection() {
        assertEquals(1, repositoryDecorator.getSize());
        repositoryDecorator.closeRedisConnection();
        assertNotNull(repositoryDecorator.findById("3"));
    }


}
