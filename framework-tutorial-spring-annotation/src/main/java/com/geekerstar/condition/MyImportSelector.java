package com.geekerstar.condition;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author geekerstar
 * @date 2018/12/9
 * description 自定义逻辑返回需要导入的组件
 */
public class MyImportSelector implements ImportSelector {
    /**
     * 返回值就是导入到容器中的组件全类名
     *
     * @param annotationMetadata 当前标注@Import注解的类的所有注解信息
     * @return
     */
    public String[] selectImports(AnnotationMetadata annotationMetadata) {

        //annotationMetadata.get
        //方法不要返回null值
        return new String[]{"com.geekerstar.bean.Blue","com.geekerstar.bean.Yellow"};
    }
}
