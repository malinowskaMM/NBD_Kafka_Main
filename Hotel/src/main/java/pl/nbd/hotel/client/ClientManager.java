package pl.nbd.hotel.client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.RollbackException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;

import java.util.List;
import java.util.function.Predicate;

public class ClientManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4j.class);
    @PersistenceContext
    private final EntityManager entityManager;
    private final ClientRepository clientRepository;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public ClientManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.clientRepository = new ClientRepository(entityManager);
    }

    public Client registerClient(String firstName, String lastName, String personalId, Address address) {
        final Client client = new Client(personalId, firstName,lastName,address, 0., new ClientType(ClientTypeName.REGULAR, 0));
        if (validator.validate(client).size() == 0) {
            try {
                entityManager.getTransaction().begin();
                if (clientRepository.findById(client.personalId) != null) {
                    LOGGER.warn("Client {} does not exist in the database.", client.personalId);
                    entityManager.getTransaction().rollback();
                } else {
                    final Client client1 = clientRepository.save(client);
                    entityManager.getTransaction().commit();
                    return client1;
                }
            } catch (RollbackException e) {
                LOGGER.error("Transaction failed:", e);
                entityManager.getTransaction().rollback();
            }
        } else {
            LOGGER.error("Client {} validation failed.", client.personalId);
          }
    return null;
    }

    public void unregisterClient(Client client) {
        if (validator.validate(client).size() == 0) {
            entityManager.getTransaction().begin();
            final Client client1 = clientRepository.findById(client.personalId);
            if(client1 == null) {
                LOGGER.warn("Client {} does not exist in the database.", client.personalId);
                entityManager.getTransaction().rollback();
            } else {
                entityManager.remove(client1);
                entityManager.getTransaction().commit();
            }
        } else {
            LOGGER.atError().log("Client {} validation failed.", client.personalId);
        }
    }

    public Client getClient(String id) {
            return clientRepository.findById(id);
    }

    public List<Client> findClients(Predicate<Client> predicate) {
        return clientRepository.find(predicate);
    }

    public String getAllClientsReport() {
        return clientRepository.getReport();
    }

}
