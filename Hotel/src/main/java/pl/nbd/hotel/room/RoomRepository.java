package pl.nbd.hotel.room;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import pl.nbd.hotel.repository.Repository;

import java.util.List;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class RoomRepository implements Repository<Room> {

    private final EntityManager entityManager;

    @Override
    public Room findById(String id) {
        return entityManager.find(Room.class, id);
    }

    @Override
    public Room save(Room room) {
        entityManager.persist(room);
        return room;
    }

    @Override
    public List<Room> find(Predicate<Room> predicate) {
        return findAll().stream().filter(predicate).toList();
    }

    @Override
    public List<Room> findAll() {
        return entityManager.createQuery("SELECT c FROM Room c", Room.class).getResultList();
    }

    @Override
    public String getReport() {
        final StringBuilder description = new StringBuilder();
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
    public void remove(Room object) {
        entityManager.remove(object);
    }
}
