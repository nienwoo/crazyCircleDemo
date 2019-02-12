package com.crazymakercircle.redis;

import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * create by 尼恩 @ 疯狂创客圈
 **/
public class SimpleJredisPoolDemo {

    private static JedisPool pool = null;

    public static JedisPool getPool() {
        if (pool == null) {
            JedisPoolConfig config = new JedisPoolConfig();
            config.setMaxTotal(500);
            config.setMaxIdle(5);
            config.setMaxWaitMillis(1000 * 10);
            //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
            config.setTestOnBorrow(true);
            //new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
            pool = new JedisPool(config, "[ip]", 8379, 10000, "[auth]");

        }
        return pool;
    }

    public synchronized static Jedis getResource() {
        if (pool == null) {
            pool = getPool();
        }


        return pool.getResource();
    }


    @Test
    public void testCRUD() {
        Jedis redis = null;
        int loop = 1;
        while (loop < 20) {
            try {
                long start = System.currentTimeMillis();
                redis = getResource();
                redis.set("k1", "v1");
                String ret = redis.get("k1");
                long end = System.currentTimeMillis();
                System.out.printf("Get ret from redis: %s with %d millis\n", ret, end - start);
            } finally {
                //使用后一定关闭，还给连接池

                if (redis != null) {
                    redis.close();
                }
            }
            loop++;
        }
    }

    @Test
    public void testCRUD2() {
        int loop = 1;
        while (loop < 20) {
            try (Jedis redis = getResource()) {
                long start = System.currentTimeMillis();
                redis.set("k1", "v1");
                String ret = redis.get("k1");
                long end = System.currentTimeMillis();
                System.out.printf("Get ret from redis: %s with %d millis\n", ret, end - start);
            }
            loop++;
        }
    }

}