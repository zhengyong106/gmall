package com.atguigu.gmall.service.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedissonConfig {
    private static final Logger logger = LoggerFactory.getLogger(RedissonConfig.class);

    @Value("${spring.redis.host:disable}")
    String host;

    @Value("${spring.redis.port:6379}")
    Integer port;

    @Bean
    public RedissonClient redissonClient() {
        if (host.equals("disable")) {
            return null;
        }
        Config config = new Config();
        config.useSingleServer().setAddress("redis://" + host + ":" + port);
        return Redisson.create(config);
    }
}
