package com.geekerstar.configuration;

import com.geekerstar.annotation.EnableHelloWorld;
import com.geekerstar.condition.ConditionalOnSystemProperty;
import org.springframework.context.annotation.Configuration;

/**
 * HelloWorld 自动装配
 */
@Configuration // Spring 模式注解装配
@EnableHelloWorld // Spring @Enable 模块装配
@ConditionalOnSystemProperty(name = "user.name", value = "Mercy") // 条件装配
public class HelloWorldAutoConfiguration {
}
