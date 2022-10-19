package pl.nbd.hotel.room;

import jakarta.persistence.EntityManager;
import jakarta.persistence.RollbackException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

public class RoomManager {
    private final EntityManager entityManager;
    private final RoomRepository roomRepository;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    public RoomManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.roomRepository = new RoomRepository(entityManager);
    }

    public Room addShowerRoom(String roomNumber, Double basePrice, int roomCapacity, boolean withShelf) {
        final Room room = new ShowerRoom(roomNumber, basePrice, roomCapacity, withShelf);
        if (validator.validate(room).size() == 0) {
            try {
                entityManager.getTransaction().begin();
                if (roomRepository.findById(room.roomNumber).isPresent()) {
                    entityManager.getTransaction().rollback();
                } else {
                    final Room savedRoom = roomRepository.save(room);
                    entityManager.getTransaction().commit();
                    return savedRoom;
                }
            } catch (RollbackException e) {
                entityManager.getTransaction().rollback();
            }
        } else {
            System.out.println("error");
        }
        return null;
    }

    public Room addBathRoom(String roomNumber, Double basePrice, int roomCapacity, bathType bathType){
        final Room room = new BathRoom(roomNumber, basePrice, roomCapacity, bathType);
        if (validator.validate(room).size() == 0) {
            try {
                entityManager.getTransaction().begin();
                if (roomRepository.findById(room.roomNumber).isPresent()) {
                    entityManager.getTransaction().rollback();
                } else {
                    final Room savedRoom = roomRepository.save(room);
                    entityManager.getTransaction().commit();
                    return savedRoom;
                }
            } catch (RollbackException e) {
                entityManager.getTransaction().rollback();
            }
        } else {
            System.out.println("error");
        }
        return null;
    }

    public void removeRoom(Room room) {
        if (validator.validate(room).size() == 0) {
            entityManager.getTransaction().begin();
            roomRepository.findById(room.getRoomNumber()).map(room1 -> {
                roomRepository.remove(room1);
                entityManager.getTransaction().commit();
                return null;
            }).orElseGet(() -> {
                entityManager.getTransaction().rollback();
                return null;
            });
        }
    }

    public Room getRoom(String id) {
        if (Objects.nonNull(id)) {
            return roomRepository.findById(id).orElse(null);
        } else {
            return null;
        }
    }

    public List<Room> findRooms(Predicate<Room> predicate) {
        return roomRepository.find(predicate);
    }

    public List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    public String getAllRoomsReport() {
        return roomRepository.getReport();
    }

}
