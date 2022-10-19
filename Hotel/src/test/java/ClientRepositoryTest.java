import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Before;
import org.junit.Test;
import pl.nbd.hotel.client.Address;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.ClientRepository;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ClientRepositoryTest {
    EntityManagerFactory entityManagerFactory;
    EntityManager entityManager;
    ClientRepository clientRepository;

    @Before
    public void init() {
        entityManagerFactory = Persistence.createEntityManagerFactory("HOTEL");
        entityManager = entityManagerFactory.createEntityManager();
        clientRepository = new ClientRepository(entityManager);

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO clienttype(client_type_name, discount) VALUES ('Diamond', 15);").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO client (personal_id, version, city_name, postal_code, street, street_number, first_name, last_name, client_type_name) " +
                "VALUES ('11111111111', 1, 'miasto', '11-111', 'ulica', 'numer', 'imie', 'nazwisko', 'Diamond');").executeUpdate();
        entityManager.getTransaction().commit();

        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO client (personal_id, version, city_name, postal_code, street, street_number, first_name, last_name, client_type_name) " +
                "VALUES ('11111111110', 1, 'miastoDuze', '12-211', 'ulicaDluga', 'numerN', 'xyz', 'zyx', 'Diamond');").executeUpdate();
        entityManager.getTransaction().commit();

    }

    @Test
    public void shouldFindById() {
        String id = "11111111111";
        Client client = clientRepository.findById(id);
        String info = id.concat(" imie nazwisko ulica numer miasto 11-111 Diamond 15");
        assertEquals(info,client.getClientInfo());
    }

    @Test
    public void shouldFindByFirstNameEqualsImie() {
        List<Client> client = clientRepository.find(client1 -> client1.getFirstName().equals("imie"));
        assertEquals(1,client.size());
    }

    @Test
    public void shouldNotFindByFirstNameEqualsName() {
        List<Client> client = clientRepository.find(client1 -> client1.getFirstName().equals("name"));
        assertEquals(0,client.size());
    }

    @Test
    public void shouldReturnListSizeEqualsTwo() {
        List<Client> clients = clientRepository.findAll();
        assertEquals(2,clients.size());
    }

    @Test
    public void shouldAddClientToRepository() {
        assertEquals(2,clientRepository.getSize());

        ClientType clientType = new ClientType(ClientTypeName.Sapphire, 35);

        entityManager.getTransaction().begin();
        entityManager.persist(clientType);
        entityManager.getTransaction().commit();

        Client client = new Client("00230908071", "Jan", "Nowak", new Address("Nowa", "3a", "Warszawa", "00-010"), clientType);
        clientRepository.save(client);

        assertEquals(3,clientRepository.getSize());
    }

    @Test
    public void shouldRemoveClientFromRepository() {
        assertEquals(2,clientRepository.getSize());

        Client client = clientRepository.findById("11111111110");
        clientRepository.remove(client);

        assertEquals(1,clientRepository.getSize());
    }
}
