package com.geekerstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan(basePackages = "com.geekerstar.learnspringboot.web.servlet")
public class LearnSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearnSpringBootApplication.class, args);
	}
}
