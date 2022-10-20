import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;
import pl.nbd.hotel.client.Address;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.ClientManager;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;

public class Main {
    public static void main(String[] args){
        EntityManager entityManager= Persistence.createEntityManagerFactory("HOTEL")
                .createEntityManager();
        ClientManager clientManager = new ClientManager(entityManager);
        ClientType clientType = new ClientType(ClientTypeName.REGULAR, 0);
        entityManager.getTransaction().begin();
        entityManager.persist(clientType);
        entityManager.getTransaction().commit();
        clientManager.registerClient("hubert", "Nowak", "11111111111", new Address("12th Street", "12", "New York", "00-001"));
        Client client = clientManager.getClient("11111111111");
        clientManager.unregisterClient(client);
//        clientManager.unregisterClient();
    }
}