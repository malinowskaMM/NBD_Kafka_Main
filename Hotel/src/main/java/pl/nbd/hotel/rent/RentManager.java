package pl.nbd.hotel.rent;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.ClientRepository;
import pl.nbd.hotel.client.type.ClientTypeName;
import pl.nbd.hotel.room.Room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class RentManager {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ClientRepository clientRepository;
    private final RentRepository rentRepository;

    public RentManager() {
        this.rentRepository = new RentRepository();
        this.clientRepository = new ClientRepository();
    }

    public Rent rentRoom(Client client, Room room, LocalDateTime beginTime, LocalDateTime endTime) {
        if (validator.validate(client).size() == 0 && validator.validate(room).size() == 0) {
            if (beginTime.isBefore(endTime)) {
                    final List<Rent> rents = rentRepository.getRentsForRoom(room.getRoomNumber(), beginTime, endTime);
                    if (rents.size() == 0) {
                        final Rent rent = rentRepository.save(new Rent(UUID.randomUUID(), beginTime, endTime, client, room, client.applyDiscount(room.getPrice())));
                        return rent;
                    }
            }
        }
        return null;
    }

    public void endRoomRent(Rent rent) {
        if (validator.validate(rent).size() == 0) {
            final Rent rent1 = rentRepository.findById(rent.getId().toString());
            if(rent1 != null) {
                final Client client = rent1.getClient();
                client.setMoneySpent(rent1.client.getMoneySpent() + rent1.rentCost);
                rentRepository.removeById(rent1.id.toString());
                checkChangeClientType(client);
            }
        }
    }

    public List<Rent> getAllClientRents(Client client) {
        return rentRepository.find(rent -> rent.getClient().equals(client));
    }

    public List<Rent> getRoomRent(Room room) {
        return rentRepository.find(rent -> rent.getRoom().equals(room));
    }

    public List<Rent> findRents(Predicate<Rent> predicate) {
        return rentRepository.find(predicate);
    }

    public String getAllRentsReport() {
        return rentRepository.getReport();
    }

    void checkChangeClientType(Client client) {
        if (client.getMoneySpent() > 100000) {
            if (client.getClientType().getClientTypeName().equals(ClientTypeName.SAPPHIRE)) {
                client.getClientType().setClientTypeName(ClientTypeName.DIAMOND);
            }
        } else if (client.getMoneySpent() > 50000) {
            if (client.getClientType().getClientTypeName().equals(ClientTypeName.EMERALD)) {
                client.getClientType().setClientTypeName(ClientTypeName.SAPPHIRE);
            }
        } else if (client.getMoneySpent() > 10000) {
            if (client.getClientType().getClientTypeName().equals(ClientTypeName.GOLD)) {
                client.getClientType().setClientTypeName(ClientTypeName.EMERALD);
            }
        } else if (client.getMoneySpent() > 5000) {
            if (client.getClientType().getClientTypeName().equals(ClientTypeName.REGULAR)) {
                client.getClientType().setClientTypeName(ClientTypeName.GOLD);
            }
        }
        clientRepository.save(client);
    }
}
