package com.xiaoyang;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
@MapperScan("com.xiaoyang.mapper")
@Transactional
public class PersonalBlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(PersonalBlogApplication.class, args);
    }


}
