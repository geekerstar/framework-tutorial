package com.geekerstar.config;

import com.geekerstar.bean.Person;
import org.springframework.context.annotation.*;

/**
 * @author geekerstar
 * @date 2018/12/8
 * description 等同于配置文件
 * @ComponentScan value:指定要扫描的包
 * excludeFilter = Filter[]，指定扫描的时候按照扫描规则排除那些组件
 * includeFilter = Filter[]，指定扫描的时候只需要包含哪些组件
 *
 * FilterType.ANNOTATION 按照注解
 * FilterType.ASSIGNABLE_TYPE 按照给定的类型
 * FilterType.ASPECTJ 使用ASPECTJ表达式
 * FilterType.REGEX 使用正则指定
 * FilterType.CUSTOM 使用自定义规则
 */
@Configuration  //告诉Spring这是个配置类
@ComponentScans(
        value = {
                @ComponentScan(value = "com.geekerstar", includeFilters = {
//                        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
//                        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {BookService.class}),
                        @ComponentScan.Filter(type = FilterType.CUSTOM,classes = {MyTypeFIlter.class})
                }, useDefaultFilters = false)
        }
)

public class MainConfig {
    /**
     * 给容器注册一共Bean，类型为返回值的类型，id默认用方法名作为id
     *
     * @return
     */
    @Bean(value = "person01")
    public Person person() {
        return new Person("geek", 20);
    }
}
