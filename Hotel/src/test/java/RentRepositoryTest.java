import org.junit.Before;
import org.junit.Test;
import pl.nbd.hotel.client.Address;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;
import pl.nbd.hotel.rent.Rent;
import pl.nbd.hotel.rent.RentManager;
import pl.nbd.hotel.rent.RentRepository;
import pl.nbd.hotel.room.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static org.junit.Assert.assertEquals;


public class RentRepositoryTest {
    RentRepository rentRepository;
    Rent rentExample;

    Client clientExample;
    Room roomExample;
    DateTimeFormatter formatter;

    @Before
    public void init() {
        rentRepository = new RentRepository();

        rentRepository.mongoDatabase.drop();

        Client client = new Client("11111111111", "imie", "nazwisko", new Address("ulica", "numer", "miasto", "11-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 1500));
        Client client1 = new Client("11010101010", "imie", "nazwisko", new Address("ulica", "numer", "miasto", "11-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 1500));

        Room room = new BathRoom("3", 150.0, 2, bathType.SMALL);
        Room room1 = new BathRoom("4", 120.0, 1, bathType.SMALL);

        rentRepository.save(new Rent(UUID.fromString("c9ba0eae-5084-11ed-bdc3-0242ac120002"), LocalDateTime.of(2022, 10, 18, 13, 10),
                LocalDateTime.of(2022, 10, 20, 12, 0), client, room, 1000.0));
        rentRepository.save(new Rent(UUID.fromString("c9ba0eae-5011-11fd-bdc3-0243ac120002"), LocalDateTime.of(2022, 10, 19, 13, 10),
                LocalDateTime.of(2022, 10, 20, 12, 0), client1, room1, 800.0));

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        roomExample = new BathRoom("3", 150.0, 2, bathType.SMALL);
        clientExample = new Client("11111111111","imie", "nazwisko", new Address("ulica", "numer", "miasto", "11-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 1500));
        rentExample = new Rent(UUID.fromString("c9ba0eae-5084-11ed-bdc3-0242ac120002"), LocalDateTime.parse("2022-10-18 13:10:00", formatter), LocalDateTime.parse("2022-10-20 12:00:00", formatter), clientExample, roomExample, 1000.0);

    }

    @Test
    public void shouldFindById() {
        String id = "c9ba0eae-5084-11ed-bdc3-0242ac120002";
        Rent rent = rentRepository.findById(id);
        assertEquals(rentExample.getRentInfo(), rent.getRentInfo());
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
        Room roomExample2 = new ShowerRoom("3", 100.0, 1, false);
        Client clientExample2 = new Client("11010000000","imie2", "nazwisko2", new Address("ulica2", "numer2", "miasto2", "00-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));


        RentManager rentManager = new RentManager();
        rentManager.rentRoom(clientExample2, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());
    }

    @Test
    public void shouldRemoveRentFromRepository() {
        assertEquals(2, rentRepository.getSize());

        Rent rent = rentRepository.findById("c9ba0eae-5084-11ed-bdc3-0242ac120002");
        RentManager rentManager = new RentManager();
        rentManager.endRoomRent(rent);

        assertEquals(1, rentRepository.getSize());
    }

    @Test
    public void shouldNotPermitsRentSameRoomInSameDateSecondTime() {
        assertEquals(2, rentRepository.getSize());
        Room roomExample2 = new ShowerRoom("3", 100.0, 1, false);
        Client clientExample2 = new Client("11010000000","imie2", "nazwisko2", new Address("ulica2", "numer2", "miasto2", "00-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));
        Client clientExample3 = new Client("10000000000","imie3", "nazwisko3", new Address("ulica3", "numer3", "miasto3", "01-101"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));


        RentManager rentManager = new RentManager();
        rentManager.rentRoom(clientExample2, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());
    }

    @Test
    public void shouldNotAllowRentSameRoomConcurrent() {
        assertEquals(2, rentRepository.getSize());
        Room roomExample2 = new ShowerRoom("3", 100.0, 1, false);
        Client clientExample2 = new Client("11010000000","imie2", "nazwisko2", new Address("ulica2", "numer2", "miasto2", "00-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));
        Client clientExample3 = new Client("10000000000","imie3", "nazwisko3", new Address("ulica3", "numer3", "miasto3", "01-101"), 0.0, new ClientType(ClientTypeName.DIAMOND, 15));


        RentManager rentManager = new RentManager();
        rentManager.rentRoom(clientExample2, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());

        rentManager.rentRoom(clientExample3, roomExample2, LocalDateTime.parse("2022-10-10 13:10:00", formatter), LocalDateTime.parse("2022-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());

        rentManager.rentRoom(clientExample3, roomExample2, LocalDateTime.parse("2021-10-11 13:10:00", formatter), LocalDateTime.parse("2021-10-25 13:10:00", formatter));
        assertEquals(3, rentRepository.getSize());
    }
}
