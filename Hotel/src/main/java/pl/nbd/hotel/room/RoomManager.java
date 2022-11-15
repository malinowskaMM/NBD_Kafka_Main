package pl.nbd.hotel.room;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

public class RoomManager {
    private final RoomRepository roomRepository;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    public RoomManager() {
        this.roomRepository = new RoomRepository();
    }

    public Room addShowerRoom(String roomNumber, Double basePrice, int roomCapacity, boolean withShelf) {
        final Room room = new ShowerRoom(roomNumber, basePrice, roomCapacity, withShelf);
        if (validator.validate(room).size() == 0) {
            if (roomRepository.findById(room.roomNumber) == null) {
                final Room savedRoom = roomRepository.save(room);
                return savedRoom;
                }
        }
        return null;
    }

    public Room addBathRoom(String roomNumber, Double basePrice, int roomCapacity, bathType bathType){
        final Room room = new BathRoom(roomNumber, basePrice, roomCapacity, bathType);
        if (validator.validate(room).size() == 0) {
            if (roomRepository.findById(room.roomNumber) == null) {
                final Room savedRoom = roomRepository.save(room);
                return savedRoom;
            }
        }
        return null;
    }

    public void removeRoom(Room room) {
        if (validator.validate(room).size() == 0) {
            final Room room1 = roomRepository.findById(room.getRoomNumber());
            if(room1 != null) {
                roomRepository.remove(room1);
            }
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
