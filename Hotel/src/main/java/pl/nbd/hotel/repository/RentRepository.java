package pl.nbd.hotel.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import pl.nbd.hotel.rent.Rent;
import pl.nbd.hotel.room.Room;

import java.util.List;
import java.util.function.Predicate;

public class RentRepository implements Repository<Rent>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Rent findById(String id) {
        return entityManager.find(Rent.class, id);
    }

    @Override
    @Transactional
    public Rent save(Rent object) {
        entityManager.persist(object);
        return object;
    }

    @Override
    @Transactional
    public Rent find(Predicate<Rent> predicate) {
        return entityManager.find(Rent.class, predicate);
    }

    @Override
    @Transactional
    public List<Rent> findAll() {
        TypedQuery<Rent> query = entityManager.createQuery("SELECT c FROM Rent c", Rent.class);
        return query.getResultList();
    }

    @Override
    public String getReport() {
        StringBuilder description = new StringBuilder();
        for (Rent r: findAll()) {
            description.append(r.getRentInfo());
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
    public void remove(Rent object) {
        entityManager.remove(object);
    }
}
