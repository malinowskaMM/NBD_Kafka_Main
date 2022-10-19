import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Test;
import pl.nbd.hotel.client.Address;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;
import pl.nbd.hotel.repository.ClientRepository;

import java.util.UUID;

public class ClientTest {

    @Test
    public void test() {

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HOTEL");
        EntityManager entityManager = entityManagerFactory.createEntityManager();
        ClientRepository clientRepository = new ClientRepository(entityManager);

        ClientType clientType = new ClientType(ClientTypeName.Diamond, 15);
        Client client = new Client(UUID.randomUUID(), "John", "Nowak", new Address("12th Street", "12", "New York", "00-001"),
                clientType);

        entityManager.persist(clientType);


        clientRepository.save(client);
    }
}
