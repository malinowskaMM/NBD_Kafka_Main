package pl.nbd.hotel.rent;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import pl.nbd.hotel.db.AbstractMongoRepository;
import pl.nbd.hotel.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RentRepository extends AbstractMongoRepository implements Repository<Rent> {

    public RentRepository() {
        this.rentMongoCollection = mongoDatabase.getCollection("rents", Rent.class);
    }

    private final MongoCollection<Rent> rentMongoCollection;

    @Override
    public Rent findById(String id) {
        Bson filter = Filters.eq("id", id);
        FindIterable<Rent> rents = rentMongoCollection.find(filter);
        return rents.first();
    }

    @Override
    public Rent save(Rent object) {
        rentMongoCollection.insertOne(object);
        return object;
    }

    @Override
    public List<Rent> find(Predicate<Rent> predicate) {
        final List<Rent> rents = findAll();
        return rents.stream().filter(predicate).toList();
    }

    @Override
    public List<Rent> findAll() {
        return rentMongoCollection.aggregate(List.of(Aggregates.replaceRoot("$rent")),Rent.class).into(new ArrayList<>());
    }

    @Override
    public String getReport() {
        final StringBuilder description = new StringBuilder();
        for (Rent r : findAll()) {
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
        Bson filter = Filters.eq("id", object.getId());
        rentMongoCollection.deleteOne(filter);
    }

    public List<Rent> getRentsForRoom(String roomNumber, LocalDateTime beginTime, LocalDateTime endTime) {
        return find(
                rent -> ((rent.getRoom().getRoomNumber().equals(roomNumber))
                        &&
                        ((beginTime.isAfter(rent.getBeginTime()) && beginTime.isBefore(rent.getEndTime()))
                || (endTime.isAfter(rent.getBeginTime()) && endTime.isBefore(rent.getEndTime()))
                || (beginTime.isEqual(rent.getBeginTime()) && endTime.isEqual(rent.getEndTime()))))
        );
    }


    @Override
    public void close() throws Exception {

    }
}
