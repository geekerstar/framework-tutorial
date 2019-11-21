package com.geekerstar.condition;

import com.geekerstar.bean.RainBow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author geekerstar
 * @date 2018/12/9
 * description
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     *
     * @param annotationMetadata  当前类的注解信息
     * @param beanDefinitionRegistry  BeanDefinition注册类，把所有需要添加到容器中的bean，调用BeanDefinitionRegistry。registerBeanDefinition手工注册进来
     */
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry) {
        boolean definition = beanDefinitionRegistry.containsBeanDefinition("com.geekerstar.bean.Red");
        boolean definition1 = beanDefinitionRegistry.containsBeanDefinition("com.geekerstar.bean.Blue");
        if (definition && definition1){
            //指定Bean的定义信息（Bean的类型，Bean。。。）
            RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
            //注册一共Bean，指定Bean名
            beanDefinitionRegistry.registerBeanDefinition("rainBow",beanDefinition);
        }
    }
}
