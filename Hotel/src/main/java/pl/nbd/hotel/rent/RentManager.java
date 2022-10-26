package pl.nbd.hotel.rent;

import com.mongodb.client.MongoCollection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
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

    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4j.class);
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ClientRepository clientRepository;
    private final RentRepository rentRepository;

    public RentManager(MongoCollection<Rent> rentMongoCollection, MongoCollection<Client> clientMongoCollection) {
        this.rentRepository = new RentRepository(rentMongoCollection);
        this.clientRepository = new ClientRepository(clientMongoCollection);
    }

    public Rent rentRoom(Client client, Room room, LocalDateTime beginTime, LocalDateTime endTime) {
        if (validator.validate(client).size() == 0 && validator.validate(room).size() == 0) {
            if (beginTime.isBefore(endTime)) {
                    final List<Rent> rents = rentRepository.getRentsForRoom(room.getRoomNumber(), beginTime, endTime);
                    if (rents.size() == 0) {
                        final Rent rent = rentRepository.save(new Rent(UUID.randomUUID(), UUID.randomUUID(), beginTime, endTime, client, room, client.applyDiscount(room.getPrice())));
                        return rent;
                    } else {
                        LOGGER.warn("Room {} is already reserved", room.getRoomNumber());
                    }
            } else {
                LOGGER.warn("Begin time of reservation {} is not earlier than end time of reservation {}", beginTime, endTime);
            }
        } else {
            LOGGER.error("Parameters validation failed");
        }
        return null;
    }

    public void endRoomRent(Rent rent) {
        if (validator.validate(rent).size() == 0) {
            final Rent rent1 = rentRepository.findById(rent.getId().toString());
            if(rent1 != null) {
                final Client client = rent1.getClient();
                client.setMoneySpent(rent1.client.getMoneySpent() + rent1.rentCost);
                rentRepository.remove(rent1);
                checkChangeClientType(client);
            } else {
                LOGGER.warn("Rent {} does not exist in the database", rent.getId());
            }
        } else {
            LOGGER.error("Rent {} validation failed", rent.getId());
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
