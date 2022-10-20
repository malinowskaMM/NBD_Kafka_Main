package pl.nbd.hotel.client;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import pl.nbd.hotel.repository.Repository;

import java.util.*;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class ClientRepository implements Repository<Client> {

    private final EntityManager entityManager;

    @Override
    public Client findById(String id) {
        return entityManager.find(Client.class, id);
    }

    @Override
    public Client save(Client client) {
        entityManager.persist(client);
        return client;
    }

    @Override
    public List<Client> find(Predicate<Client> predicate) {
        return findAll().stream().filter(predicate).toList();
    }

    @Override
    public List<Client> findAll() {
        return entityManager.createQuery("SELECT c FROM Client c", Client.class).getResultList();
    }

    @Override
    public String getReport() {
        final StringBuilder description = new StringBuilder();
        for (Client c: findAll()) {
            description.append(c.getClientInfo());
            description.append(", ");
        }
        return description.toString();
    }

    @Override
    public int getSize() {
        return findAll().size();
    }

    @Override
    public void remove(Client object) {
        entityManager.remove(object);
    }
}
