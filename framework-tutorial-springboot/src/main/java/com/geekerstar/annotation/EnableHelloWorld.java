package com.geekerstar.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 激活HeloWorld模块
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(HelloWorldImportSelector.class)
public @interface EnableHelloWorld {
}
