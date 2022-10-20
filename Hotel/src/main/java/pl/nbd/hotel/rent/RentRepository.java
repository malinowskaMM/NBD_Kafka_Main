package pl.nbd.hotel.rent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import pl.nbd.hotel.repository.Repository;
import pl.nbd.hotel.room.Room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class RentRepository implements Repository<Rent> {

    private final EntityManager entityManager;

    @Override
    public Optional<Rent> findById(String id) {
        return Optional.of(entityManager.find(Rent.class, id));
    }

    @Override
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
    public List<Rent> find(Predicate<Rent> predicate) {
        List<Rent> rents = findAll();
        return rents.stream().filter(predicate).toList();
    }

    @Override
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
    public void remove(Rent object) {
        entityManager.remove(object);
    }

    public List<Rent> getRentsForRoom(String roomNumber, LocalDateTime beginTime, LocalDateTime endTime) {
        TypedQuery<Rent> query = entityManager.createQuery("SELECT c FROM Rent c WHERE c.id = ?1 AND (c.beginTime BETWEEN ?2 AND ?3 OR c.endTime BETWEEN ?2 AND ?3)", Rent.class);
        return query.setParameter(1, roomNumber).setParameter(2, beginTime).setParameter(3, endTime).getResultList();
    }


}
