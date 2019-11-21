package com.geekerstar.config;

import com.geekerstar.bean.Color;
import com.geekerstar.bean.ColorFactoryBean;
import com.geekerstar.bean.Person;
import com.geekerstar.bean.Red;
import com.geekerstar.condition.LinuxCondition;
import com.geekerstar.condition.MyImportBeanDefinitionRegistrar;
import com.geekerstar.condition.MyImportSelector;
import com.geekerstar.condition.WindowsCondition;
import org.springframework.context.annotation.*;

/**
 * @author geekerstar
 * @date 2018/12/9
 * description 组件注册
 */
//@Conditional({WindowsCondition.class})  类中组件统一设置，满足当前条件，这个类中配置的所有bean注册才能生效

@Configuration
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

    /**
     * 默认是单实例的
     *
     * prototype  多实例  IOC容器启动并不会去调用方法创建对象放在容器中，而是每次获取的时候才会调用方法创建对象，获取几次就调几次，
     * singleton  单实例（默认） IOC启动会调用方法创建对象放到IOC容器中，以后每次获取直接从容器（map.get())中拿
     * request  同义词请求创建一个实例
     * session  同一个session创建一个实例
     *
     * 单实例Bean,默认在容器启动的时候创建对象
     * 懒加载：容器启动不创建对象，第一次使用（获取）Bean创建对象，并初始化，并且以后每次获取不创建，因为是单实例的
     */
//    @Scope("prototype")
    @Lazy
    @Bean("person")
    public Person person(){
        return new Person("com/geekerstar",22);
    }

    /**
     * @Conditional({Condition}) 按照一定的条件进行判断，满足条件给容器中注册Bean
     *
     * 如果系统是win,给容器注册geek
     * 如果是Linux，给容器注册geekerstar
     */
    @Conditional({WindowsCondition.class})
    @Bean("geek")
    public Person person01(){
        return new Person("geek",22);
    }

    @Conditional({LinuxCondition.class})
    @Bean("com/geekerstar")
    public Person person02(){
        return new Person("geekerstart",33);
    }

    /**
     * 给容器中注册组件：
     * 1、包扫描+组件标注注解（@Controller、@Service、@Repository、@Component） 【自己写的类】
     * 2、@Bean 【导入的第三方包里面的组件】
     * 3、@Import 【快速给容器中导入一个组件】
     *      1.@Import（要导入到容器中的组件）：容器中就会自动注册这个组件，id默认是全类名
     *      2.@ImportSelector：返回需要导入的组件的全类名数组
     *      3.ImportBeanDefinitionRegistrar：手动注册Bean到容器中
     * 4、使用Spring提供的FactoryBean（工厂Bean）
     *      1.默认获取到的是工厂Bean调用getObject创建的对象
     *      2.要获取工厂Bean本身，我们需要给id前面加一个&  &colorFactoryBean
     */
    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
