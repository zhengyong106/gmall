package com.atguigu.gmall.service.config;

import com.atguigu.gmall.service.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisUtilConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedisUtilConfig.class);

    @Value("${spring.redis.host:disable}")
    String host;

    @Value("${spring.redis.port:6379}")
    Integer port;

    @Bean
    public RedisUtil redisUtil() {
        if (host.equals("disable")) {
            return null;
        }
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(200); // 设置最大连接数
        poolConfig.setMaxIdle(30);  // 设置最大空闲连接数
        poolConfig.setBlockWhenExhausted(true); // 设置连接耗尽时阻塞
        poolConfig.setMaxWaitMillis(10*1000); // 设置最大等待时间
        poolConfig.setTestOnBorrow(true); // 在获取连接的时候检查有效性
        return RedisUtil.init(host, port, new JedisPoolConfig());
    }
}
