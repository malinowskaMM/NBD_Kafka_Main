package pl.nbd.hotel.client;

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
    private final ClientRepository clientRepository;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public ClientManager(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client registerClient(String firstName, String lastName, String personalId, Address address) {
        final Client client = new Client(personalId, firstName,lastName,address, 0., new ClientType(ClientTypeName.REGULAR, 0));
        if (validator.validate(client).size() == 0) {
            if (clientRepository.findById(client.personalId) == null) {
                final Client client1 = clientRepository.save(client);
                return client1;
                }
        } else {
            LOGGER.error("Client {} validation failed.", client.personalId);
        }
    return null;
    }

    public void unregisterClient(Client client) {
        if (validator.validate(client).size() == 0) {
            final Client client1 = clientRepository.findById(client.personalId);
            if(client1 != null) {
                clientRepository.removeById(client1.personalId);
            } else {
                LOGGER.warn("Client {} does not exist in the database.", client.personalId);
            }
        } else {
            LOGGER.error("Client {} validation failed.", client.personalId);
        }
    }

    public Client updateClient(String personalId, Address address) {
        final Client client1 = clientRepository.findById(personalId);
        if(client1 != null) {
            client1.setAddress(address);
            clientRepository.update(client1);
            return clientRepository.findById(personalId);
        } else {
            LOGGER.warn("Client {} does not exist in the database.", personalId);
        }
        return null;
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
