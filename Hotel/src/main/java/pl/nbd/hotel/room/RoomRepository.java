package pl.nbd.hotel.room;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import org.bson.conversions.Bson;
import pl.nbd.hotel.db.AbstractMongoRepository;
import pl.nbd.hotel.repository.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class RoomRepository extends AbstractMongoRepository implements Repository<Room> {

    public RoomRepository() {
        super.initDbConnection();
        this.roomMongoCollection = mongoDatabase.getCollection("rooms", Room.class);
    }

    private final MongoCollection<Room> roomMongoCollection;

    @Override
    public Room findById(String id) {
        Bson filter = Filters.eq("_id", id);
        FindIterable<Room> rooms = roomMongoCollection.find(filter);
        return rooms.first();
    }

    @Override
    public Room save(Room room) {
        roomMongoCollection.insertOne(room);
        return room;
    }

    @Override
    public List<Room> find(Predicate<Room> predicate) {
        return findAll().stream().filter(predicate).toList();
    }

    @Override
    public List<Room> findAll() {
        return roomMongoCollection.find().into(new ArrayList<>());
    }

    @Override
    public String getReport() {
        final StringBuilder description = new StringBuilder();
        for (Room r: findAll()) {
            description.append(r.getRoomInfo());
            description.append(", ");
        }
        return description.toString();
    }

    @Override
    public int getSize() {
        return findAll().size();
    }

    @Override
    public void remove(Room object) {
        Bson filter = Filters.eq("roomNumber", object.roomNumber);
        roomMongoCollection.deleteOne(filter);
    }

    @Override
    public void close() throws Exception {

    }
}
