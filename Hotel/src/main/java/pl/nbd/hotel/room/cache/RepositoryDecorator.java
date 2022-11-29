package pl.nbd.hotel.room.cache;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import pl.nbd.hotel.db.AbstractRedisRepository;
import pl.nbd.hotel.repository.Repository;
import pl.nbd.hotel.room.Room;
import pl.nbd.hotel.room.RoomRepository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisException;

import java.util.List;
import java.util.function.Predicate;


public class RepositoryDecorator extends AbstractRedisRepository implements Repository<Room> {

    private final Jsonb jsonb;
    protected Repository<Room> roomRepository;

    public RepositoryDecorator(RoomRepository roomRepository) {
        super.initRedisConnection();
        jsonb = JsonbBuilder.create();
        this.roomRepository = roomRepository;
    }

    @Override
    public Room findById(String id) {
        try{
            return jsonb.fromJson(pool.get("room:" + id), Room.class);
        } catch (JedisException | NullPointerException e) {
            return roomRepository.findById(id);
        }
    }

    @Override
    public Room save(Room object) {
        try {
            pool.jsonSet("room:" + object.getRoomNumber(), jsonb.toJson(object, Room.class));
            pool.expire("room:" + object.getRoomNumber(), 1200);
            return roomRepository.save(object);
        } catch (JedisException e) {
            return roomRepository.save(object);
        }
    }

    @Override
    public List<Room> find(Predicate<Room> predicate) {
        return null;
    }

    @Override
    public List<Room> findAll() {
        return null;
    }

    @Override
    public String getReport() {
        return null;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public void removeById(String id) {
        try {
        pool.del("room"+id);
        roomRepository.removeById(id);
        } catch (JedisException | NullPointerException e) {
            System.out.println("TU");
            roomRepository.removeById(id);
        }
    }

    public void flush() {
        Jedis jedis = new Jedis();
        jedis.flushAll();
    }
}
