import org.junit.Before;
import org.junit.Test;
import pl.nbd.hotel.client.Address;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.ClientRepository;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;
import pl.nbd.hotel.rent.Rent;
import pl.nbd.hotel.rent.RentManager;
import pl.nbd.hotel.rent.RentRepository;
import pl.nbd.hotel.room.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.Assert.*;


public class RentRepositoryTest {
    RentRepository rentRepository;
    ClientRepository clientRepository;
    Rent rentExample;

    Client clientExample;
    Room roomExample;
    DateTimeFormatter formatter;

    @Before
    public void init() {
        rentRepository = new RentRepository();
        clientRepository = new ClientRepository();

        rentRepository.mongoDatabase.drop();
        clientRepository.mongoDatabase.drop();

        Client client = new Client("11111111111", "imie", "nazwisko", new Address("ulica", "numer", "miasto", "11-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 1500));
        Client client1 = new Client("11010101010", "imie", "nazwisko", new Address("ulica", "numer", "miasto", "11-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 1500));
        clientRepository.save(client);
        clientRepository.save(client1);

        Room room = new Room("3", 150.0, 2);
        Room room1 = new Room("4", 120.0, 1);

        rentRepository.save(new Rent(UUID.fromString("c9ba0eae-5084-11ed-bdc3-0242ac120002"), LocalDateTime.of(2022, 10, 18, 13, 10), LocalDateTime.of(2022, 10, 20, 12, 0), client, room, 1000.0));
        rentRepository.save(new Rent(UUID.fromString("c9ba0eae-5011-11fd-bdc3-0243ac120002"), LocalDateTime.of(2022, 10, 19, 13, 10), LocalDateTime.of(2022, 10, 20, 12, 0), client1, room1, 800.0));

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        roomExample = new Room("3", 150.0, 2);
        clientExample = new Client("11111111111", "imie", "nazwisko", new Address("ulica", "numer", "miasto", "11-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 1500));
        rentExample = new Rent(UUID.fromString("c9ba0eae-5084-11ed-bdc3-0242ac120002"), LocalDateTime.parse("2022-10-18 13:10:00", formatter), LocalDateTime.parse("2022-10-20 12:00:00", formatter), clientExample, roomExample, 1000.0);

    }

    @Test
    public void shouldFindById() {
        String id = "c9ba0eae-5084-11ed-bdc3-0242ac120002";
        Rent rent = rentRepository.findById(id);
        assertEquals(rentExample.rentInfoGet(), rent.rentInfoGet());
    }

    @Test
    public void shouldFindByPriceHigherThan300() {
        assertEquals(2, rentRepository.find(rent -> rent.getRentCost() > 300).size());
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
        Room roomExample2 = new Room("3", 100.0, 1);
        Client clientExample2 = new Client("11010000000", "imie2", "nazwisko2", new Address("ulica2", "numer2", "miasto2", "00-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));


        RentManager rentManager = new RentManager(rentRepository, clientRepository);
        rentManager.rentRoom(clientExample2, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());
    }

    @Test
    public void shouldNotPermitsRentSameRoomInSameDateSecondTime() {
        assertEquals(2, rentRepository.getSize());
        Room roomExample2 = new Room("3", 100.0, 1);
        Client clientExample2 = new Client("11010000000", "imie2", "nazwisko2", new Address("ulica2", "numer2", "miasto2", "00-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));

        RentManager rentManager = new RentManager(rentRepository, clientRepository);
        rentManager.rentRoom(clientExample2, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());
    }

    @Test
    public void shouldNotAllowRentSameRoomConcurrent() {
        assertEquals(2, rentRepository.getSize());
        Room roomExample2 = new Room("3", 100.0, 1);
        Client clientExample2 = new Client("11010000000", "imie2", "nazwisko2", new Address("ulica2", "numer2", "miasto2", "00-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));
        Client clientExample3 = new Client("10000000000", "imie3", "nazwisko3", new Address("ulica3", "numer3", "miasto3", "01-101"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));


        RentManager rentManager = new RentManager(rentRepository, clientRepository);
        rentManager.rentRoom(clientExample2, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());

        rentManager.rentRoom(clientExample3, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());

        rentManager.rentRoom(clientExample3, roomExample2, LocalDateTime.parse("2021-10-11 13:10:00", formatter), LocalDateTime.parse("2021-10-25 13:10:00", formatter));
        assertEquals(4, rentRepository.getSize());
    }

    @Test
    public void shouldDeleteRentFromRepository() {
        assertEquals(2, rentRepository.getSize());
        Rent rent = rentRepository.findById("c9ba0eae-5084-11ed-bdc3-0242ac120002");
        assertNotNull(rent);
        rentRepository.removeById("c9ba0eae-5084-11ed-bdc3-0242ac120002");

        assertEquals(1, rentRepository.getSize());
    }

    @Test
    public void shouldRemoveRoomFromRepositoryManager() {
        assertEquals(2, rentRepository.getSize());

        Rent rent = rentRepository.findById("c9ba0eae-5084-11ed-bdc3-0242ac120002");
        assertNotNull(rent);
        RentManager rentManager = new RentManager(rentRepository, clientRepository);
        rentManager.endRoomRent(rent);

        assertEquals(1, rentRepository.getSize());
    }

    @Test
    public void shouldUpdateRentFromRepository() {
        assertEquals(2, rentRepository.getSize());
        RentManager rentManager = new RentManager(rentRepository, clientRepository);
        Rent rent1 = rentManager.getRent("c9ba0eae-5084-11ed-bdc3-0242ac120002");
        rent1.setRentCost(500.);
        rentRepository.update(rent1);
        Rent rent2 = rentManager.getRent("c9ba0eae-5084-11ed-bdc3-0242ac120002");
        assertEquals(rent1.rentInfoGet(), rent2.rentInfoGet());
    }

    @Test
    public void shouldUpdateRentFromRepositoryManager() {
        assertEquals(2, rentRepository.getSize());
        RentManager rentManager = new RentManager(rentRepository, clientRepository);
        Rent rent1 = rentManager.getRent("c9ba0eae-5084-11ed-bdc3-0242ac120002");
        rentManager.updateRent("c9ba0eae-5084-11ed-bdc3-0242ac120002", 5.);
        Rent rent2 = rentManager.getRent("c9ba0eae-5084-11ed-bdc3-0242ac120002");
        assertNotEquals(rent1.rentInfoGet(), rent2.rentInfoGet());
        rent1.setRentCost(5.);
        assertEquals(rent1.rentInfoGet(), rent2.rentInfoGet());
    }

}
