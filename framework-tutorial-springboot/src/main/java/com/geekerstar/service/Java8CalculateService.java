package com.geekerstar.service;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * java 8 lambda 循环实现(@link CalculateService)
 */
@Profile("Java8")
@Service
public class Java8CalculateService implements CalculateService {
    @Override
    public Integer sum(Integer... values) {
        System.out.println("Java8 lambda循环实现");

        int sum = Stream.of(values).reduce(0,Integer::sum);
        return sum;
    }

    public static void main(String[] args) {
        CalculateService calculateService = new Java8CalculateService();
        System.out.println(calculateService.sum(1,2,3,4,5,6,7,8,9,10));

    }
}
