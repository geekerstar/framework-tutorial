package com.geekerstar.annotation;

import java.lang.annotation.*;

/**
 * 二级(@link Repository)
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@FirstLevelRepository
public @interface SecondLevelRepository {

    String value() default "";

}
