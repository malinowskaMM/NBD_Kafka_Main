package pl.nbd.hotel.db;

import redis.clients.jedis.*;

public class AbstractRedisRepository {
    protected static Jedis pool;

    public void initRedisConnection() {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        pool = new Jedis(new HostAndPort("localhost", 6379));
        //pool = new JedisPooled(new HostAndPort("localhost", 6379), clientConfig);
    }

    public void closeRedisConnection() {
        pool.close();
        //pool = null;
    }
}
