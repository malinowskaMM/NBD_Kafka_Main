package pl.nbd.hotel.rent;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.BsonDocument;
import org.bson.BsonDocumentWriter;
import org.bson.codecs.Codec;
import org.bson.codecs.EncoderContext;
import org.bson.conversions.Bson;
import pl.nbd.hotel.client.Client;
import pl.nbd.hotel.db.AbstractMongoRepository;
import pl.nbd.hotel.repository.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.function.Predicate;

public class RentRepository extends AbstractMongoRepository implements Repository<Rent> {

    public RentRepository() {
        super.initDbConnection();
        this.rentMongoCollection = mongoDatabase.getCollection("rents", Rent.class);
    }

    private final MongoCollection<Rent> rentMongoCollection;

    @Override
    public Rent findById(String id) {
        Bson filter = Filters.eq("_id", UUID.fromString(id));
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
        return rentMongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public String getReport() {
        final StringBuilder description = new StringBuilder();
        for (Rent r : findAll()) {
            description.append(r.rentInfoGet());
            description.append(", ");
        }
        return description.toString();
    }

    @Override
    public int getSize() {
        return findAll().size();
    }

    @Override
    public void removeById(String id) {
        Bson filter = Filters.eq("_id", UUID.fromString(id));
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

    public void update(Rent rent) {
        Codec<Rent> clientCodec = rentMongoCollection.getCodecRegistry().get(Rent.class);
        BsonDocument bsonDocument = new BsonDocument();
        clientCodec.encode(new BsonDocumentWriter(bsonDocument), rent, EncoderContext.builder().build());
        rentMongoCollection.replaceOne(Filters.eq("_id", rent.getId()), rent);
    }
}
