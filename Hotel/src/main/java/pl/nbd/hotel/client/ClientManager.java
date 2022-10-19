package pl.nbd.hotel.client;

import lombok.AllArgsConstructor;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;

import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
public class ClientManager {
    ClientRepository clientRepository;

    Client registerClient(String firstName, String lastName, String personalId, Address address) {
        Client client = new Client(personalId,firstName,lastName,address, new ClientType(ClientTypeName.Regular, 0));
        clientRepository.save(client);
        return client;
    }

    void unregisterClient(Client client) {
        clientRepository.remove(client);
    }

    Client getClient(String id) {
        return clientRepository.findById(id);
    }

    List<Client> findClients(Predicate<Client> predicate) {
        return clientRepository.find(predicate);
    }

    String getAllClientsReport() {
        return clientRepository.getReport();
    }

}
