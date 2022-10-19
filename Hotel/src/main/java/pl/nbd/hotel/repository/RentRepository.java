package pl.nbd.hotel.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import pl.nbd.hotel.rent.Rent;

import java.util.List;
import java.util.function.Predicate;

public class RentRepository implements Repository<Rent>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Rent findById(String id) {
        Rent rent = entityManager.find(Rent.class, id);
        entityManager.detach(rent);
        return rent;
    }

    @Override
    @Transactional
    public Rent save(Rent object) {
        entityManager.getTransaction().begin();
        if(object != null) {
            entityManager.lock(object.getRoom(), LockModeType.OPTIMISTIC_FORCE_INCREMENT);
            entityManager.persist(object);
            entityManager.getTransaction().commit();
        } else {
            entityManager.getTransaction().rollback();
        }
        return object;
    }

    @Override
    @Transactional
    public List<Rent> find(Predicate<Rent> predicate) {
        List<Rent> rents = findAll();
        return rents.stream().filter(predicate).toList();
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
        entityManager.getTransaction().begin();
        entityManager.remove(object);
        entityManager.getTransaction().commit();
    }
}
