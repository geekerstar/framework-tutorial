package com.geekerstar.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author geekerstar
 * @date 2018/12/11
 * description
 */

/**
 * 默认加在IOC容器中的组件，容器启动会调用午餐构造器创建对象，再进行初始化赋值操作
 */
@Component
public class Boss {
    private Car car;

    /**
     * 构造器要用的组件，都是从容器中获取
     * @param car
     */
    @Autowired
    public Boss(Car car){
        this.car = car;
        System.out.println("Boss...有参构造器");

    }

    public Car getCar() {
        return car;
    }

    /**
     * 标注在方法，Spring容器创建当前对象，就会调用方法，完成赋值
     * 方法使用的参数，自定义类型的值从IOC容器中获取
     * @param car
     */
//    @Autowired
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
