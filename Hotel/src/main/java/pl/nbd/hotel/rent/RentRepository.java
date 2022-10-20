package pl.nbd.hotel.rent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import pl.nbd.hotel.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Predicate;

@RequiredArgsConstructor
public class RentRepository implements Repository<Rent> {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public Rent findById(String id) {
        return Optional.of(entityManager.find(Rent.class, UUID.fromString(id))).orElse(null);
    }

    @Override
    public Rent save(Rent object) {
        entityManager.persist(object);
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

        return find(
                rent -> ((rent.getRoom().getRoomNumber().equals(roomNumber))
                &&
                ((rent.getBeginTime().isAfter(beginTime) && rent.getEndTime().isBefore(beginTime))
                        ||
                        ((rent.getBeginTime().isAfter(endTime) && rent.getEndTime().isBefore(endTime)))
                ||
                        (rent.getBeginTime().isEqual(beginTime) && rent.getEndTime().isEqual(endTime))))
        );
    }


}
