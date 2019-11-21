package com.geekerstar;

import com.geekerstar.bean.Boss;
import com.geekerstar.bean.Car;
import com.geekerstar.bean.Color;
import com.geekerstar.config.MainConfigOfAutowired;
import com.geekerstar.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author geekerstar
 * @date 2018/12/11
 * description
 */
public class IOCTest_Autowired {

    @Test
    public void test01(){
        //1.创建IOC容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);

        BookService bookService = applicationContext.getBean(BookService.class);
        System.out.println(bookService);

//        BookDao bean = applicationContext.getBean(BookDao.class);
//        System.out.println(bean);


        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);

        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);


        Color color = applicationContext.getBean(Color.class);
        System.out.println(color);
        System.out.println(applicationContext);


        applicationContext.close();
    }
}
