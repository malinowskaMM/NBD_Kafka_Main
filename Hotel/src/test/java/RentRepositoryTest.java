import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;
import pl.nbd.hotel.client.Address;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;
import pl.nbd.hotel.rent.Rent;
import pl.nbd.hotel.rent.RentManager;
import pl.nbd.hotel.rent.RentRepository;
import pl.nbd.hotel.room.BathRoom;
import pl.nbd.hotel.room.Room;
import pl.nbd.hotel.room.ShowerRoom;
import pl.nbd.hotel.room.bathType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class RentRepositoryTest {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    RentRepository rentRepository;
    Rent rentExample;
    Client clientExample;
    Room roomExample;
    DateTimeFormatter formatter;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("HOTEL");
        entityManager = entityManagerFactory.createEntityManager();
        rentRepository = new RentRepository(entityManager);

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO clientType(client_type_name, discount) VALUES ('DIAMOND', 15);").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO client (personal_id, version, city_name, postal_code, street, street_number, first_name, last_name, client_type_name, money_spent) " +
                "VALUES ('11111111111', 1, 'miasto', '11-111', 'ulica', 'numer', 'imie', 'nazwisko', 'DIAMOND', 0.0);").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO room(room_number, price, version, room_capacity, bathroom_type, bath_type, with_shelf) " +
                "VALUES ('1', 150.0, 1, 2, 'BATH', 'SMALL', 'FALSE')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO client (personal_id, version, city_name, postal_code, street, street_number, first_name, last_name, client_type_name, money_spent) " +
                "VALUES ('11010101010', 1, 'miasto', '11-111', 'ulica', 'numer', 'imie', 'nazwisko', 'DIAMOND', 0.0);").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO room(room_number, price, version, room_capacity, bathroom_type, bath_type, with_shelf) " +
                "VALUES ('2', 120.0, 1, 1, 'BATH', 'SMALL', 'FALSE')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO rent(id, version, begin_time, end_time, rent_cost, personal_id, room_number) " +
                "VALUES ('c9ba0eae-5084-11ed-bdc3-0242ac120002', 1, '2022-10-18 13:10:00', '2022-10-20 13:10:00', 350.0, '11111111111', '1')").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO rent(id, version, begin_time, end_time, rent_cost, personal_id, room_number) " +
                "VALUES ('c9ba0eae-5011-11fd-bdc3-0243ac120002', 1, '2022-10-19 13:10:00', '2022-10-20 13:10:00', 280.0, '11010101010', '2')").executeUpdate();
        entityManager.getTransaction().commit();


        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        roomExample = new BathRoom("2", 120.0, 1, bathType.SMALL);
        clientExample = new Client("11010101010","imie", "nazwisko", new Address("ulica", "numer", "miasto", "11-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));
        rentExample = new Rent(UUID.fromString("c9ba0eae-5011-11fd-bdc3-0243ac120002"), LocalDateTime.parse("2022-10-19 13:10:00", formatter), LocalDateTime.parse("2022-10-20 13:10:00", formatter), clientExample, roomExample, 280.0);

    }

    @Test
    public void shouldFindById() {
        String id = "c9ba0eae-5011-11fd-bdc3-0243ac120002";
        Rent rent = rentRepository.findById(id);
        assertTrue(rent.getRentInfo().equals(rentExample.getRentInfo()));
    }

    @Test
    public void shouldFindByPriceHigherThan300() {
        assertEquals(1, rentRepository.find(rent -> rent.getRentCost() > 300).size());
    }

    @Test
    public void shouldNotFindByPriceLowerThat100() {
        assertEquals(0, rentRepository.find(rent -> rent.getRentCost() < 100).size());
    }

    @Test
    public void shouldReturnListSizeEqualsTwo() {
        assertEquals(2, rentRepository.findAll().size());
    }

    @Test
    public void shouldAddRentToRepository() {
        assertEquals(2, rentRepository.getSize());
        Room roomExample2 = new ShowerRoom("3", 100.0, 1, false);
        Client clientExample2 = new Client("11010000000","imie2", "nazwisko2", new Address("ulica2", "numer2", "miasto2", "00-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));

        entityManager.getTransaction().begin();
        entityManager.persist(roomExample2);
        entityManager.persist(clientExample2);
        entityManager.getTransaction().commit();

        RentManager rentManager = new RentManager(entityManager);
        rentManager.rentRoom(clientExample2, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());
    }

    @Test
    public void shouldRemoveRentFromRepository() {
        assertEquals(2, rentRepository.getSize());

        Rent rent = rentRepository.findById("c9ba0eae-5084-11ed-bdc3-0242ac120002");
        RentManager rentManager = new RentManager(entityManager);
        rentManager.endRoomRent(rent);

        assertEquals(1, rentRepository.getSize());
    }

    @Test
    public void shouldNotPermitsRentSameRoomInSameDateSecondTime() {
        assertEquals(2, rentRepository.getSize());
        Room roomExample2 = new ShowerRoom("3", 100.0, 1, false);
        Client clientExample2 = new Client("11010000000","imie2", "nazwisko2", new Address("ulica2", "numer2", "miasto2", "00-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));
        Client clientExample3 = new Client("10000000000","imie3", "nazwisko3", new Address("ulica3", "numer3", "miasto3", "01-101"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));

        entityManager.getTransaction().begin();
        entityManager.persist(roomExample2);
        entityManager.persist(clientExample2);
        entityManager.persist(clientExample3);
        entityManager.getTransaction().commit();

        RentManager rentManager = new RentManager(entityManager);
        rentManager.rentRoom(clientExample2, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());

        rentManager.rentRoom(clientExample3, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());

        rentManager.rentRoom(clientExample3, roomExample2, LocalDateTime.parse("2022-10-11 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());
    }
}
