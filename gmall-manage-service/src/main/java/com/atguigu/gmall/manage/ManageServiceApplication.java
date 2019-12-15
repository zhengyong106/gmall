package com.atguigu.gmall.manage;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@ComponentScan("com.atguigu.gmall")
@MapperScan("com.atguigu.gmall.manage.mapper")
public class ManageServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ManageServiceApplication.class);
    }
}
