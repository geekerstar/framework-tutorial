package com.geekerstar.easyexcel;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author geekerstar
 * date: 2019/9/19 21:37
 * description:
 */

@MapperScan("com.geekerstar.easyexcel.mapper")
@SpringBootApplication
public class EasyexcelApplication {
    public static void main(String[] args) {
        SpringApplication.run(EasyexcelApplication.class,args);
    }
}
