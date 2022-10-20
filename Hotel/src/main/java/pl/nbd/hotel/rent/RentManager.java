package pl.nbd.hotel.rent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.RollbackException;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.client.ClientRepository;
import pl.nbd.hotel.client.type.ClientType;
import pl.nbd.hotel.client.type.ClientTypeName;
import pl.nbd.hotel.room.Room;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class RentManager {
    private final RentRepository rentRepository;
    private final EntityManager entityManager;
    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    private final ClientRepository clientRepository;

    public RentManager(EntityManager entityManager) {
        this.entityManager = entityManager;
        this.rentRepository = new RentRepository(entityManager);
        this.clientRepository = new ClientRepository(entityManager);
    }

    public Rent rentRoom(Client client, Room room, LocalDateTime beginTime, LocalDateTime endTime) {
        if (validator.validate(client).size() == 0 && validator.validate(room).size() == 0) {
            if (beginTime.isBefore(endTime)) {
                try {
                    entityManager.getTransaction().begin();
                    entityManager.lock(room, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
                    List<Rent> rents = rentRepository.getRentsForRoom(room.getRoomNumber(), beginTime, endTime);
                    if (rents.size() == 0) {
                        final Rent rent = rentRepository.save(new Rent(UUID.randomUUID(), beginTime, endTime, client, room, client.applyDiscount(room.getPrice())));
                        entityManager.getTransaction().commit();
                        return rent;
                    } else {
                        System.out.println("W tym czasie ten pokoj jest juz zarezerwowany.");
                        entityManager.getTransaction().rollback();
                    }
                } catch (IllegalArgumentException e) {
                entityManager.getTransaction().rollback();
            }
            } else {
                System.out.printf("Czas poczatkowy rezerwacji %s nie jest wczesniejszy niz czas koncowy rezerwacji %s%n", beginTime, endTime);
            }
        } else {
            System.out.println("Podane parametry nie spelniaja zalozen!");
        }
        return null;
    }

    public void endRoomRent(Rent rent) {
        if (validator.validate(rent).size() == 0) {
            entityManager.getTransaction().begin();
            Rent rent1 = rentRepository.findById(rent.getId().toString());
            if(rent1 != null) {
                Client client = rent1.getClient();
                client.setMoneySpent(rent1.client.getMoneySpent() + rent1.rentCost);
                entityManager.lock(rent1.getRoom(), LockModeType.NONE);
                rentRepository.remove(rent1);
                checkChangeClientType(client);
                entityManager.getTransaction().commit();
            } else {
                entityManager.getTransaction().rollback();
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
        clientRepository.save(client);//sprawdzic czy to nie wyrzuci bledu.
    }
}
