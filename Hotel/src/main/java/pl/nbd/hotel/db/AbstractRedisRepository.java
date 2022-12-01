package pl.nbd.hotel.db;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;

public class AbstractRedisRepository {
    protected static JedisPooled pool;

    public void initRedisConnection() {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder().build();
        pool = new JedisPooled(new HostAndPort("localhost", 7001), clientConfig);
    }

    public void closeRedisConnection() {
        pool.getPool().close();
        //pool = null;
    }
}
