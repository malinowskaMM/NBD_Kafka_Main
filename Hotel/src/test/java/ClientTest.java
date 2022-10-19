import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.junit.Test;
import pl.nbd.hotel.client.Address;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;
import pl.nbd.hotel.repository.ClientRepository;

import java.util.List;
import java.util.UUID;

public class ClientTest {

    @Test
    public void test() {

//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("HOTEL");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        ClientRepository clientRepository = new ClientRepository(entityManager);
//
//        ClientType clientType = new ClientType(ClientTypeName.Diamond, 15);
//        Client client = new Client(UUID.randomUUID(), "John", "Nowak", new Address("12th Street", "12", "New York", "00-001"),
//                clientType);
//
//        entityManager.persist(clientType);
//
//
//        clientRepository.save(client);
    }

    @Test
    public void test2() {
        EntityManager  entityManager = Persistence.createEntityManagerFactory("HOTEL")
                .createEntityManager();
        //Dodawanie do bazy z uzyciem transakcji
        entityManager.getTransaction().begin();
        entityManager.createNativeQuery("INSERT INTO clienttype(client_type_name, discount) VALUES ('Diamond', 15);").executeUpdate();
        entityManager.createNativeQuery("INSERT INTO client (personal_id, version, city_name, postal_code, street, street_number, first_name, last_name, client_type_name) " +
                "VALUES (\'38c75e74-4fd5-11ed-bdc3-0242ac120002\', 1, 'cos', '96-002', 'ktos', 'ulica', 'number', 'Hubert', 'Diamond');").executeUpdate();
        entityManager.getTransaction().commit();

        List<Client> listEmployee = entityManager.createQuery("SELECT e FROM Client e").getResultList();
        Client employee = entityManager.find(Client.class, UUID.fromString("38c75e74-4fd5-11ed-bdc3-0242ac120002"));
        System.out.println(employee.getLastName());
//        Client client = entityManager.createQuery("SELECT e FROM Client e WHERE Client .clientType= '12345678912'")
//                .getSingleResult();
    }
}
