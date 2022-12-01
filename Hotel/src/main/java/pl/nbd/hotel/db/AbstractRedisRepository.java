package pl.nbd.hotel.db;

import redis.clients.jedis.*;

public class AbstractRedisRepository {
    protected static Jedis pool;

    public void initRedisConnection() {
        pool = new Jedis(new HostAndPort("localhost", 6379));
    }

    public void closeRedisConnection() {
        pool.close();
    }
}
