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

    private final ClientRepository clientRepository;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public ClientManager() {
        this.clientRepository = new ClientRepository();
    }

    public Client registerClient(String firstName, String lastName, String personalId, Address address) {
        final Client client = new Client(personalId, firstName,lastName,address, 0., new ClientType(ClientTypeName.REGULAR, 0));
        if (validator.validate(client).size() == 0) {
            if (clientRepository.findById(client.personalId) == null) {
                final Client client1 = clientRepository.save(client);
                return client1;
                }
        }
    return null;
    }

    public void unregisterClient(Client client) {
        if (validator.validate(client).size() == 0) {
            final Client client1 = clientRepository.findById(client.personalId);
            if(client1 != null) {
                clientRepository.remove(client1);
            }
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
