package com.geekerstar.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

/**
 * Spring的容器不扫描controller;父容器
 */
@ComponentScan(value = "com.geekerstar", excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
})
public class RootConfig {

}
