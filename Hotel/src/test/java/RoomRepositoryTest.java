import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;
import pl.nbd.hotel.room.BathRoom;
import pl.nbd.hotel.room.Room;
import pl.nbd.hotel.room.RoomRepository;
import pl.nbd.hotel.room.bathType;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class RoomRepositoryTest {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    RoomRepository roomRepository;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("HOTEL");
        entityManager = entityManagerFactory.createEntityManager();
        roomRepository = new RoomRepository(entityManager);

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO room(room_number, price, room_capacity, bathroom_type, bath_type, with_shelf) " +
                "VALUES ('1', 150.0, 2, 'BATH', 'SMALL', 'FALSE')").executeUpdate();

        entityManager.getTransaction().commit();
        }

    @Test
    public void shouldFindById() {
        Room room = roomRepository.findById("1");
        String info = "1".concat(" 150.0 2");
        assertEquals(info,room.getRoomInfo());
    }

    @Test
    public void shouldFindByFirstNameEqualsImie() {
        List<Room> rooms = roomRepository.find(room -> room.getRoomNumber().equals("1"));
        assertEquals(1,rooms.size());
    }

    @Test
    public void shouldNotFindByFirstNameEqualsName() {
        List<Room> rooms = roomRepository.find(room -> room.getRoomNumber().equals("0"));
        assertEquals(0,rooms.size());
    }

    @Test
    public void shouldReturnListSizeEqualsTwo() {
        List<Room> rooms = roomRepository.findAll();
        assertEquals(1,rooms.size());
    }

    @Test
    public void shouldAddClientToRepository() {
        assertEquals(1, roomRepository.getSize());

        Room room = new BathRoom("112", 200.0, 2, bathType.JACUZZI);
        roomRepository.save(room);

        assertEquals(2, roomRepository.getSize());
    }

    @Test
    public void shouldRemoveClientFromRepository() {
        assertEquals(1, roomRepository.getSize());

        Room room = roomRepository.findById("1");
        roomRepository.remove(room);

        assertEquals(0, roomRepository.getSize());
    }
}
