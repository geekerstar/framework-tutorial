package com.geekerstar.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author geekerstar
 * @date 2018/12/9
 * description
 */
@Component
public class Cat implements InitializingBean, DisposableBean {
    public Cat(){
        System.out.println("cat constructor ...");

    }

    public void destroy() throws Exception {
        System.out.println("cat...destroy...");

    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("cat...afterPropertiesSet...");

    }
}
