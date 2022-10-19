package pl.nbd.hotel.room;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import pl.nbd.hotel.repository.Repository;
import pl.nbd.hotel.room.Room;

import java.util.List;
import java.util.function.Predicate;

public class RoomRepository implements Repository<Room> {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public Room findById(String id) {
        Room room = entityManager.find(Room.class, id);
        entityManager.detach(room);
        return room;
    }

    @Override
    @Transactional
    public Room save(Room object) {
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
    public List<Room> find(Predicate<Room> predicate) {
        return findAll().stream().filter(predicate).toList();
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
        entityManager.getTransaction().begin();
        entityManager.remove(object);
        entityManager.getTransaction().commit();
    }
}
