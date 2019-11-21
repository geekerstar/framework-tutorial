package com.geekerstar.configuration;

import org.springframework.context.annotation.Bean;

/**
 * HelloWorld 配置
 */
public class HelloWorldConfiguration {

    @Bean
    public String helloWorld() { // 方法名即 Bean 名称
        return "Hello,World 2019";
    }

}
