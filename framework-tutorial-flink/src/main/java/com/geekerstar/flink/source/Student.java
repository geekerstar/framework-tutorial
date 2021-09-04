package com.geekerstar.flink.source;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author geekerstar
 * @date 2021/9/4 15:09
 * @description
 */
@Data
@AllArgsConstructor
public class Student {

    private int id;
    private String name;
    private int age;

}
