package pl.nbd.hotel.client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.RollbackException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class ClientManager {
    @PersistenceContext
    private final EntityManager entityManager;

    public ClientManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.clientRepository = new ClientRepository(entityManager);
    }

    ClientRepository clientRepository;
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public Client registerClient(String firstName, String lastName, String personalId, Address address) {
        final Client client = new Client(personalId, firstName,lastName,address, 0., new ClientType(ClientTypeName.REGULAR, 0));
        if (validator.validate(client).size() == 0) {
            try {
                entityManager.getTransaction().begin();
                if (clientRepository.findById(client.personalId).isPresent()) {
                    entityManager.getTransaction().rollback();
                } else {
                    final Client client1 = clientRepository.save(client);
                    entityManager.getTransaction().commit();
                    return client1;
                }
            } catch (RollbackException e) {
                entityManager.getTransaction().rollback();
            }
        } else {
            System.out.println("error");
          }
    return null;
    }

    public void unregisterClient(Client client) {
        if (validator.validate(client).size() == 0) {
            entityManager.getTransaction().begin();
            clientRepository.findById(client.personalId).map(client1 -> {
                clientRepository.remove(client1);
                entityManager.getTransaction().commit();
                return client1;
            }).orElseGet(() -> {
                entityManager.getTransaction().rollback();
                return null;
            });
        }
    }

    public Client getClient(String id) {
        if (Objects.nonNull(id)) {
            return clientRepository.findById(id).orElse(null);
        } else {
            return null;
        }
    }

    public List<Client> findClients(Predicate<Client> predicate) {
        return clientRepository.find(predicate);
    }

    public String getAllClientsReport() {
        return clientRepository.getReport();
    }

}
