package pl.nbd.hotel.room;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nbd.hotel.db.UniqueId;

import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class RoomManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4j.class);
    private final RoomRepository roomRepository;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public RoomManager() {
        this.roomRepository = new RoomRepository();
    }

    public Room addShowerRoom(String roomNumber, Double basePrice, int roomCapacity, boolean withShelf) {
        final Room room = new ShowerRoom(new UniqueId(UUID.randomUUID()),roomNumber, basePrice, roomCapacity, withShelf);
        if (validator.validate(room).size() == 0) {
            if (roomRepository.findById(room.roomNumber) != null) {
                LOGGER.warn("Room {} already exists in the database", room.getRoomNumber());
            } else {
                final Room savedRoom = roomRepository.save(room);
                return savedRoom;
                }
        } else {
            LOGGER.warn("Room {} validation failed", room.getRoomNumber());
        }
        return null;
    }

    public Room addBathRoom(String roomNumber, Double basePrice, int roomCapacity, bathType bathType){
        final Room room = new BathRoom(new UniqueId(UUID.randomUUID()),roomNumber, basePrice, roomCapacity, bathType);
        if (validator.validate(room).size() == 0) {
                if (roomRepository.findById(room.roomNumber) != null) {
                    LOGGER.warn("Room {} already exists in the database", room.getRoomNumber());
                } else {
                    final Room savedRoom = roomRepository.save(room);
                    return savedRoom;
                }
        } else {
            LOGGER.warn("Room {} validation failed", room.getRoomNumber());
        }
        return null;
    }

    public void removeRoom(Room room) {
        if (validator.validate(room).size() == 0) {
            final Room room1 = roomRepository.findById(room.getRoomNumber());
            if(room1 == null) {
                LOGGER.warn("Room {} does not exist in the database", room.getRoomNumber());
            } else {
                roomRepository.remove(room1);
            }
        } else {
            LOGGER.warn("Room {} validation failed", room.getRoomNumber());
        }
    }

    public Room getRoom(String id) {
        return roomRepository.findById(id);
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
