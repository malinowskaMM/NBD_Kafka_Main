package pl.nbd.hotel.db;

import redis.clients.jedis.DefaultJedisClientConfig;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisClientConfig;
import redis.clients.jedis.JedisPooled;

public class AbstractRedisRepository extends AbstractMongoRepository {
    private static JedisPooled pool;

    public void initRedisConnection() {
        JedisClientConfig clientConfig = DefaultJedisClientConfig.builder()
                .password("master123")
                .build();
        pool = new JedisPooled(new HostAndPort("localhost", 7001), clientConfig);
    }


    @Override
    public void close() throws Exception {

    }
}