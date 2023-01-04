import org.junit.Before;
import org.junit.Test;
import pl.nbd.hotel.client.Address;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.ClientRepository;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;
import pl.nbd.hotel.rent.Rent;
import pl.nbd.hotel.rent.RentRepository;
import pl.nbd.hotel.room.Room;

import java.time.LocalDateTime;
import java.util.UUID;

public class KafkaRentRepositoryTest {

    RentRepository rentRepository;

    Client client;

    Room room;

    @Before
    public void initialize() {
        rentRepository = new RentRepository();

        rentRepository.mongoDatabase.drop();

        client = new Client("11111111111", "Jan", "Kowalski", new Address("Piotrkowska", "198", "Lodz", "91-111"), 0.0, new ClientType(ClientTypeName.REGULAR, 10));
        room = new Room("5", 500.0, 1);
    }



    @Test
    public void shouldCreateRent() {
        UUID generatedUUID = UUID.randomUUID();
        System.out.println(generatedUUID);
        rentRepository.save(new Rent(generatedUUID,
                LocalDateTime.of(2022, 10, 18, 13, 10),
                LocalDateTime.of(2022, 10, 20, 12, 0),
                client,
                room,
                2000.0));
    }

}
