package pl.nbd.hotel.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import pl.nbd.hotel.room.Room;

import java.util.List;
import java.util.function.Predicate;

public class RoomRepository implements Repository<Room>{

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Room findById(String id) {
        return entityManager.find(Room.class, id);
    }

    @Override
    @Transactional
    public Room save(Room object) {
        entityManager.persist(object);
        return object;
    }

    @Override
    @Transactional
    public Room find(Predicate<Room> predicate) {
        return entityManager.find(Room.class, predicate);
    }

    @Override
    @Transactional
    public List<Room> findAll() {
        TypedQuery<Room> query = entityManager.createQuery("SELECT c FROM Room c", Room.class);
        return query.getResultList();
    }

    @Override
    public String getReport() {
        StringBuilder description = new StringBuilder();
        for (Room r: findAll()) {
            description.append(r.getRoomInfo());
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
    public void remove(Room object) {
        entityManager.remove(object);
    }
}
