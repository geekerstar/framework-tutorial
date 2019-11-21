package com.geekerstar;

import com.geekerstar.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author geekerstar
 * @date 2018/12/9
 * description
 */
public class IOCTest_LifeCycle {

    @Test
    public void test01(){
        //1.创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
        System.out.println("容器创建完成。。。");

//        applicationContext.getBean("car");
        //关闭容器
        applicationContext.close();
    }
}
