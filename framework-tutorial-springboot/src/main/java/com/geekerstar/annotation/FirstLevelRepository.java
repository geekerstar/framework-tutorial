package com.geekerstar.annotation;

import org.springframework.stereotype.Repository;

import java.lang.annotation.*;

/**
 * 一级 {@link Repository @Repository}
 */

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Repository
public @interface FirstLevelRepository {

    String value() default "";

}
