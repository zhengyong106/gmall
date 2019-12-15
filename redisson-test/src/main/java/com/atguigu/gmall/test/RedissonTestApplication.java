package com.atguigu.gmall.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@ComponentScan(basePackages =  {"com.atguigu.gmall"})
public class RedissonTestApplication{
    public static void main(String[] args) {
        SpringApplication.run(RedissonTestApplication.class, args);
    }
}
