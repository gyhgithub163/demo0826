package com.example.demo0826;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
//@SpringBootApplication是@Configuration + @EnableAutoConfiguration + @ComponentScan的复合注解
@SpringBootApplication
//扫描持久层接口
@MapperScan("com.example.demo0826.dao")
//注册swagger
@EnableSwagger2
public class Demo0826Application {
    //web程序入口
    public static void main(String[] args) {
        SpringApplication.run(Demo0826Application.class, args);
    }

}
