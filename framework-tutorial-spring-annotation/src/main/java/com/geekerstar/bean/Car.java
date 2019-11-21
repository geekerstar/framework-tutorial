package com.geekerstar.bean;

import org.springframework.stereotype.Component;

/**
 * @author geekerstar
 * @date 2018/12/9
 * description
 */
@Component
public class Car {

    public Car(){
        System.out.println("Car Constructor...");
    }

    public void init(){
        System.out.println("car ... init,,,");
    }

    public void destroy(){
        System.out.println("car ... destroy...");

    }
}
