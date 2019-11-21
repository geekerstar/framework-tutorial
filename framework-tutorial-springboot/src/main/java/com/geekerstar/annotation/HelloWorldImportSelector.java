package com.geekerstar.annotation;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * HelloWorld （@link ImportSelector)实现
 */
public class HelloWorldImportSelector implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[0];
    }
}
