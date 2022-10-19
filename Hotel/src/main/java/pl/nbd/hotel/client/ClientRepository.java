package pl.nbd.hotel.client;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.repository.Repository;

import java.util.*;
import java.util.function.Predicate;

@AllArgsConstructor
public class ClientRepository implements Repository<Client> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Client findById(String id) {
        Client client = entityManager.find(Client.class, id);
        entityManager.detach(client);
        return client;
    }

    @Override
    @Transactional
    public Client save(Client object) {
        entityManager.getTransaction().begin();
        if(object != null) {
            entityManager.persist(object);
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().rollback();
        }
        return object;
    }

    @Override
    @Transactional
    public List<Client> find(Predicate<Client> predicate) {
        return findAll().stream().filter(predicate).toList();
    }

    @Override
    @Transactional
    public List<Client> findAll() {
        TypedQuery<Client> query = entityManager.createQuery("SELECT c FROM Client c", Client.class);
        return query.getResultList();
    }

    @Override
    public String getReport() {
        StringBuilder description = new StringBuilder();
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
    @Transactional
    public void remove(Client object) {
        entityManager.getTransaction().begin();
        entityManager.remove(object);
        entityManager.getTransaction().commit();
    }
}
