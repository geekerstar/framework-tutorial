package com.geekerstar.bean;

/**
 * @author geekerstar
 * @date 2018/12/9
 * description
 */
public class Color {

    private Car car;

    @Override
    public String toString() {
        return "Color{" +
                "car=" + car +
                '}';
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
