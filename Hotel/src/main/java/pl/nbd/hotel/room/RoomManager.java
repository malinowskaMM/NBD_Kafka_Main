package pl.nbd.hotel.room;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

public class RoomManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4j.class);
    private final RoomRepository roomRepository;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public RoomManager(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    public Room addShowerRoom(String roomNumber, Double basePrice, int roomCapacity) {
        final Room room = new Room(roomNumber, basePrice, roomCapacity);
        if (validator.validate(room).size() == 0) {
            if (roomRepository.findById(room.roomNumber) == null) {
                final Room savedRoom = roomRepository.save(room);
                return savedRoom;
                } else {
                LOGGER.warn("Room {} already exists in the database", room.getRoomNumber());
            }
        } else {
            LOGGER.warn("Room {} validation failed", room.getRoomNumber());
        }
        return null;
    }

    public Room addBathRoom(String roomNumber, Double basePrice, int roomCapacity){
        final Room room = new Room(roomNumber, basePrice, roomCapacity);
        if (validator.validate(room).size() == 0) {
            if (roomRepository.findById(room.roomNumber) == null) {
                final Room savedRoom = roomRepository.save(room);
                return savedRoom;
            } else {
                LOGGER.warn("Room {} already exists in the database", room.getRoomNumber());
            }
        } else {
            LOGGER.warn("Rent {} validation failed", room.getRoomNumber());
        }
        return null;
    }

    public Room updateRoom(String roomNumber, Double price) {
        final Room room1 = roomRepository.findById(roomNumber);
        if(room1 != null) {
            room1.setPrice(price);
            roomRepository.update(room1);
            return roomRepository.findById(roomNumber);
        } else {
            LOGGER.warn("Room {} does not exist in the database.", roomNumber);
        }
        return null;
    }

    public void removeRoom(Room room) {
        if (validator.validate(room).size() == 0) {
            final Room room1 = roomRepository.findById(room.getRoomNumber());
            if(room1 != null) {
                roomRepository.removeById(room1.getRoomNumber());
            } else {
                LOGGER.warn("Room {} does not exist in the database.", room.getRoomNumber());
            }
        } else {
            LOGGER.error("Room {} validation failed.", room.getRoomNumber());
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
