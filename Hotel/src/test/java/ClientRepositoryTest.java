import org.junit.Before;
import org.junit.Test;
import pl.nbd.hotel.client.Address;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.ClientRepository;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;

import java.util.List;

import static org.junit.Assert.*;

public class ClientRepositoryTest {
    ClientRepository clientRepository;

    @Before
    public void init() {
        clientRepository = new ClientRepository();

        clientRepository.save(new Client("11111111111", "imie", "nazwisko",
                new Address("ulica", "numer", "miasto", "11-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 1500)));


//        entityManager.getTransaction().begin();
//        entityManager.createNativeQuery("INSERT INTO Client (personal_id, version, city_name, postal_code, street, street_number, first_name, last_name, client_type_name, money_spent) " +
//                "VALUES ('11111111110', 1, 'miastoDuze', '12-211', 'ulicaDluga', 'numerN', 'xyz', 'zyx', 'DIAMOND', 0.0);").executeUpdate();
//        entityManager.getTransaction().commit();

    }

    @Test
    public void shouldFindById() {
        String id = "11111111111";
        Client client = clientRepository.findById(id);
        String info = id.concat(" imie nazwisko ulica numer miasto 11-111 DIAMOND 1500");
        assertNotNull(client);
        assertEquals(info,client.getClientInfo());
    }

    @Test
    public void shouldFindByFirstNameEqualsImie() {
        List<Client> client = clientRepository.find(client1 -> client1.getFirstName().equals("imie"));
        assertEquals(1,client.size());
    }
//
//    @Test
//    public void shouldNotFindByFirstNameEqualsName() {
//        List<Client> client = clientRepository.find(client1 -> client1.getFirstName().equals("name"));
//        assertEquals(0,client.size());
//    }
//
//    @Test
//    public void shouldReturnListSizeEqualsTwo() {
//        List<Client> clients = clientRepository.findAll();
//        assertEquals(2,clients.size());
//    }
//
//    @Test
//    public void shouldAddClientToRepository() {
//        assertEquals(2,clientRepository.getSize());
//
//        ClientType clientType = new ClientType(ClientTypeName.REGULAR, 0);

//        entityManager.getTransaction().begin();
//        entityManager.persist(clientType);
//        entityManager.getTransaction().commit();
//
//        ClientManager clientManager = new ClientManager();
//        clientManager.registerClient("Jan", "Nowak", "00230908071", new Address("Nowa", "3a", "Warszawa", "00-010"));
//
//        assertEquals(3,clientRepository.getSize());
//    }
//
//    @Test
//    public void shouldRemoveClientFromRepository() {
//        assertEquals(2,clientRepository.getSize());
//
//        Client client = clientRepository.findById("11111111110");
//        ClientManager clientManager = new ClientManager();
//        clientManager.unregisterClient(client);
//
//        assertEquals(1,clientRepository.getSize());
//    }
}
