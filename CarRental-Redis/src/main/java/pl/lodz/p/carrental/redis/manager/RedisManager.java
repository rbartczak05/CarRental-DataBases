package pl.lodz.p.carrental.redis.manager;

import jakarta.json.bind.Jsonb;
import jakarta.json.bind.JsonbBuilder;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class RedisManager {

    private static final Jsonb jsonb = JsonbBuilder.create();
    public static String REDIS_HOST = "localhost";
    public static int REDIS_PORT = 6379;
    public static String CACHE_PREFIX = "carrental:";
    public static int TTL_SECONDS = 300;
    private static JedisPool jedisPool;
    private static boolean isConnected = false;

    private static void loadProperties() {
        try (InputStream input = RedisManager.class.getClassLoader().getResourceAsStream("redis.properties")) {
            if (input != null) {
                Properties prop = new Properties();
                prop.load(input);
                REDIS_HOST = prop.getProperty("redis.host", REDIS_HOST);
                REDIS_PORT = Integer.parseInt(prop.getProperty("redis.port", String.valueOf(REDIS_PORT)));
                CACHE_PREFIX = prop.getProperty("redis.cache.prefix", CACHE_PREFIX);
                TTL_SECONDS = Integer.parseInt(prop.getProperty("redis.cache.ttl.seconds", String.valueOf(TTL_SECONDS)));
            } else {
                System.err.println("Nie znaleziono pliku redis.properties, używam wartości domyślnych");
            }
        } catch (IOException e) {
            System.err.println("Błąd odczytu redis.properties: " + e.getMessage());
        }
    }

    public static void init() {
        if (jedisPool != null) {
            return;
        }

        loadProperties();
        try {
            final JedisPoolConfig poolConfig = new JedisPoolConfig();
            poolConfig.setMaxTotal(10);
            poolConfig.setMaxIdle(5);
            poolConfig.setMinIdle(1);
            poolConfig.setBlockWhenExhausted(true);
            poolConfig.setMaxWaitMillis(5000);

            jedisPool = new JedisPool(poolConfig, REDIS_HOST, REDIS_PORT);

            try (Jedis jedis = getJedis()) {
                String pingResult = jedis.ping();
                System.out.println("Nawiązano połączenie z Redis: " + pingResult);
                isConnected = true;
            }

        } catch (JedisException e) {
            System.err.println("Nie udało się połączyć z Redis. Aplikacja będzie działać w trybie awaryjnym (bez cache'a). Błąd: " + e.getMessage());
            isConnected = false;
        }
    }

    private static Jedis getJedis() {
        if (jedisPool == null) {
            init();
        }
        return jedisPool.getResource();
    }

    public static void close() {
        if (jedisPool != null) {
            jedisPool.close();
            jedisPool = null;
            isConnected = false;
        }
    }

    public static String getRaw(String key) {
        if (!isConnected) return null;
        try (Jedis jedis = getJedis()) {
            return jedis.get(key);
        } catch (JedisException e) {
            System.err.println("Błąd odczytu z Redis: " + e.getMessage());
            isConnected = false;
            return null;
        }
    }

    public static <T> T deserialize(String json, Class<T> clazz) {
        if (json == null) return null;
        return jsonb.fromJson(json, clazz);
    }

    public static <T> T get(String key, Class<T> clazz) {
        String json = getRaw(key);
        return deserialize(json, clazz);
    }

    public static void set(String key, Object value, long ttlInSeconds) {
        if (!isConnected) return;

        try (Jedis jedis = getJedis()) {
            String json = jsonb.toJson(value);
            jedis.setex(key, ttlInSeconds, json);
        } catch (JedisException e) {
            System.err.println("Błąd zapisu do Redis (klucz: " + key + "): " + e.getMessage());
            isConnected = false;
        }
    }

    public static void delete(String key) {
        if (!isConnected) return;

        try (Jedis jedis = getJedis()) {
            jedis.del(key);
        } catch (JedisException e) {
            System.err.println("Błąd usuwania z Redis (klucz: " + key + "): " + e.getMessage());
            isConnected = false;
        }
    }

    public static boolean isConnected() {
        return isConnected;
    }
}