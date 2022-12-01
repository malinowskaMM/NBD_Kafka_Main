package pl.nbd.hotel.db;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public class AbstractRedisRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(Slf4j.class);
    protected static JedisPooled pool;

    public void initRedisConnection() {
        try (InputStream inputStream = new FileInputStream("src/main/resources/META-INF/redisConfig.properties")) {
            Properties redisConnectionProperties = new Properties();
            redisConnectionProperties.load(inputStream);
            int liczba = Integer.parseInt(redisConnectionProperties.getProperty("jedis.port"));
            String host = redisConnectionProperties.getProperty("jedis.host");
            pool = new JedisPooled(new HostAndPort(host, liczba), DefaultJedisClientConfig.builder().build());
        } catch (FileNotFoundException e) {
            LOGGER.error("File with redis config not found!");
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//            pool = new JedisPooled(new HostAndPort("localhost", 6379));
    }

    public void closeRedisConnection() {
        pool.close();
    }
}
