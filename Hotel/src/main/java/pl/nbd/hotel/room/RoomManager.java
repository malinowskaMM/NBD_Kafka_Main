package pl.nbd.hotel.room;

import lombok.AllArgsConstructor;

import java.util.List;
import java.util.function.Predicate;

@AllArgsConstructor
public class RoomManager {
    RoomRepository roomRepository;

    Room addShowerRoom(String roomNumber, Double basePrice, int roomCapacity, boolean withShelf) {
        Room room = new ShowerRoom(roomNumber, basePrice, roomCapacity, withShelf);
        return roomRepository.save(room);
    }

    Room addBathRoom(String roomNumber, Double basePrice, int roomCapacity, bathType bathType){
        Room room = new BathRoom(roomNumber, basePrice, roomCapacity, bathType);
        return roomRepository.save(room);
    }

    void removeRoom(Room room) {
        roomRepository.remove(room);
    }

    List<Room> findRooms(Predicate<Room> predicate) {
        return roomRepository.find(predicate);
    }

    List<Room> findAllRooms() {
        return roomRepository.findAll();
    }

    String getAllRoomsReport() {
        return roomRepository.getReport();
    }

}
