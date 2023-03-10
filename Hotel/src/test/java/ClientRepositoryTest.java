import org.junit.Before;
import org.junit.Test;
import pl.nbd.hotel.client.Address;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.ClientManager;
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
        clientRepository.mongoDatabase.drop();

        clientRepository.save(new Client("11111111111", "imie", "nazwisko", new Address("ulica", "numer", "miasto", "11-111"), 0.0, new ClientType(ClientTypeName.DIAMOND, 1500)));
        clientRepository.save(new Client("11111111110", "xyz", "zyx", new Address("ulicaDluga", "numerN", "miastoDuze", "12-211"), 0.0, new ClientType(ClientTypeName.DIAMOND, 1500)));
    }

    @Test
    public void shouldFindById() {
        String id = "11111111111";
        Client client = clientRepository.findById(id);
        String info = id.concat(" imie nazwisko ulica numer miasto 11-111 DIAMOND 1500");
        assertNotNull(client);
        assertEquals(info, client.clientInfoGet());
    }

    @Test
    public void shouldFindByFirstNameEqualsImie() {
        List<Client> client = clientRepository.find(client1 -> client1.getFirstName().equals("imie"));
        assertEquals(1, client.size());
    }

    @Test
    public void shouldNotFindByFirstNameEqualsName() {
        List<Client> client = clientRepository.find(client1 -> client1.getFirstName().equals("name"));
        assertEquals(0, client.size());
    }

    @Test
    public void shouldReturnListSizeEqualsTwo() {
        List<Client> clients = clientRepository.findAll();
        assertEquals(2, clients.size());
    }

    @Test
    public void shouldAddClientToRepository() {
        assertEquals(2, clientRepository.getSize());

        ClientManager clientManager = new ClientManager(clientRepository);
        clientManager.registerClient("Jan", "Nowak", "00230908071", new Address("Nowa", "3a", "Warszawa", "00-010"));

        assertEquals(3, clientRepository.getSize());
    }

    @Test
    public void shouldRemoveClientFromRepository() {
        assertEquals(2, clientRepository.getSize());

        Client client = clientRepository.findById("11111111110");
        ClientManager clientManager = new ClientManager(clientRepository);
        clientManager.unregisterClient(client);

        assertEquals(1, clientRepository.getSize());
    }

    @Test
    public void shouldUpdateClientInRepository() {
        assertEquals(2, clientRepository.getSize());

        ClientManager clientManager = new ClientManager(clientRepository);
        clientManager.registerClient("Hub", "Nowak", "00230908072", new Address("Nowa", "3a", "Warszawa", "00-010"));

        Client client = clientManager.getClient("00230908072");
        client.setMoneySpent(1000);

        clientRepository.update(client);

        Client client2 = clientManager.getClient("00230908072");
        assertEquals(client2.clientInfoGet(), client.clientInfoGet());
    }

    @Test
    public void shouldUpdateClientInRepositoryManager() {
        assertEquals(2, clientRepository.getSize());

        ClientManager clientManager = new ClientManager(clientRepository);
        clientManager.registerClient("Hub", "Nowak", "00230908072", new Address("Nowa", "3a", "Warszawa", "00-010"));

        Client client = clientManager.getClient("00230908072");
        Address address = new Address("Nowy", "3a", "Warszawa", "00-010");

        clientManager.updateClient("00230908072", address);

        Client client2 = clientManager.getClient("00230908072");
        assertNotEquals(client2.clientInfoGet(), client.clientInfoGet());
        client.setAddress(address);
        assertEquals(client2.clientInfoGet(), client.clientInfoGet());
    }

}
