package com.atguigu.gmall.service.util;


import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.params.SetParams;

public class RedisUtil {
    private JedisPool jedisPool;


    RedisUtil (JedisPool jedisPool) {
        this.jedisPool = jedisPool;
    }

    public static RedisUtil init(String host, int port, JedisPoolConfig poolConfig) {
        return new RedisUtil(new JedisPool(poolConfig, host, port));
    }

    private Jedis getJedis(){
        return jedisPool.getResource();
    }

    public String get(String key) {
        Jedis jedis = getJedis();
        String result = jedis.get(key);
        jedis.close();
        return result;
    }

    public String set(String key, String value, int expireSeconds) {
        Jedis jedis = getJedis();
        String result = jedis.set(key, value, SetParams.setParams().ex(expireSeconds));
        jedis.close();
        return result;
    }

    public String set(String key, String value) {
        Jedis jedis = getJedis();
        String result = jedis.set(key, value);
        jedis.close();
        return result;
    }
}
