package pl.nbd.hotel.manager;

import lombok.AllArgsConstructor;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.rent.Rent;
import pl.nbd.hotel.repository.RentRepository;
import pl.nbd.hotel.room.Room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

@AllArgsConstructor
public class RentManager {
    private RentRepository currentRents;
    private RentRepository archiveRents;

    Rent rentRoom(Client client, Room room, LocalDateTime beginTime, LocalDateTime endTime) {
        return currentRents.save(new Rent(UUID.randomUUID(),beginTime, endTime, client, room));
    }

    void endRoomRent(Rent rent) {
        currentRents.remove(rent);
        archiveRents.save(rent);
    }

    List<Rent> getAllClientRents(Client client) {
        return currentRents.findAll().stream().filter(rent -> rent.getClient().equals(client)).toList();
    }

    List<Rent> getRoomRent(Room room) {
        return currentRents.findAll().stream().filter(rent -> rent.getRoom().equals(room)).toList();
    }

    Double getClientBalanceInValue(Client client) {
        List<Rent> rents = getAllClientRents(client);
        Double total = 0.0;
        for (Rent r: rents) {
            total += r.getRentCost();
        }
        return total;
    }

    List<Rent> findCurrentRents(Predicate<Rent> predicate) {
        return currentRents.findAll().stream().filter(predicate).toList();
    }

    List<Rent> findArchivedRents(Predicate<Rent> predicate) {
        return archiveRents.findAll().stream().filter(predicate).toList();
    }

    List<Rent> findAllRents() {
        return currentRents.findAll();
    }

    String getAllCurrentRentsReport() {
        return currentRents.getReport();
    }

    String getAllArchiveRentsReport() {
        return archiveRents.getReport();
    }

    void changeClientType(Client client) {
    }

    void changeEndTime(Rent rent, LocalDateTime newEndTime) {
        rent.changeEndTime(newEndTime);
        currentRents.save(rent);
    }

    Rent findRent(String id) {
        return currentRents.findById(id);
    }










}
