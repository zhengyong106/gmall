package com.atguigu.gmall.test.controller;

import com.atguigu.gmall.service.util.RedisUtil;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class RedissonTestController {
    @Autowired
    RedisUtil redisUtil;

    @Autowired
    RedissonClient redissonClient;

    private final String REDIS_KEY_PREFIX = "RedissonTest";

    @GetMapping("test")
    public String test(HttpServletRequest request){
        String count;
        RLock lock = null;
        try {
            lock = redissonClient.getLock(REDIS_KEY_PREFIX + ":lock");
            lock.lock();

            count = redisUtil.get(REDIS_KEY_PREFIX + ":count");
            String newCount;
            if (null != count) {
                newCount = String.valueOf(Integer.parseInt(count) + 1);
            } else {
                newCount = "1";
            }
            redisUtil.set(REDIS_KEY_PREFIX + ":count", newCount);
            count = newCount;
        } finally {
            if (null != lock) lock.unlock();
        }
        System.out.println(count);
        return "Count " + count;
    }
}
