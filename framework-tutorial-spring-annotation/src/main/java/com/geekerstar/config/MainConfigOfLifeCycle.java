package com.geekerstar.config;

import com.geekerstar.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author geekerstar
 * @date 2018/12/9
 * description
 *
 * bean的生命周期：Bean创建--初始化--销毁的过程
 * 容器管理Bean的生命周期
 * 我们可以自定义初始化和销毁方法，容器在bean进行到当前生命周期的时候来调用我们自定义的初始化和销毁方法
 *
 * 构造（对象创建）
 *      单实例：容器启动的时候创建对象
 *      多实例：在每次获取的时候创建对象
 *
 * BeanPostProcessor.postProcessBeforeInitialization
 * 初始化：对象创建完成，并赋值好，调用初始化方法
 * BeanPostProcessor.postProcessAfterInitialization
 *
 * 销毁：
 *      单实例：容器关闭的时候
 *      多实例：容器不会管理这个Bean，容器不会调用销毁方法
 *
 *
 * 遍历得到容器中所有的BeanPostProcessor；挨个执行beforeInitialization，
 * 一但返回null，跳出for循环，不会执行后面的BeanPostProcessor.postProcessorsBeforeInitialization
 *
 *
 * BeanPostProcessor原理
 * populateBean(beanName, mbd, instanceWrapper);给bean进行属性赋值
 * initializeBean
 * {
 * applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 * invokeInitMethods(beanName, wrappedBean, mbd);执行自定义初始化
 * applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *}
 *
 *
 * 1、指定初始化和销毁方法
 *      配置的方式：通过@Bean指定init-method和destroy-method
 * 2、通过让Bean实现InitialIzingBean(定义初始化逻辑），DisposableBean(定义销毁）
 * 3、可以用JSR250
 *      @PostConstruct，在Bean创建完成并且属性赋值完成，来执行初始化方法
 *      @PreDestroy:在容器销毁Bean之前通知进行清理工作
 * 4、BeanPostProcessor,bean的后置处理器：在bean初始化前后进行一些处理工作
 *      postProcessBeforeInitialization:在初始化之前工作
 *      postProcessAfterInitialization:在初始化之后工作
 *
 *
 */

@ComponentScan("com.geekerstar.bean")
@Configuration
public class MainConfigOfLifeCycle {

//    @Scope("prototype")
    @Bean(initMethod = "init",destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }
}
