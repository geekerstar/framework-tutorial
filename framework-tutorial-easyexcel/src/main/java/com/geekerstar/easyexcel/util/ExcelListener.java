package com.geekerstar.easyexcel.util;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author geekerstar
 * date: 2019/9/22 13:37
 * description: 监听类，可以根据需要，自定义处理获取到的数据
 */
public class ExcelListener extends AnalysisEventListener {

    //自定义用于暂时存储data
    //可以通过实例获取该值
    private List<Object> dataList = new ArrayList<>();

    /**
     * 通过 AnalysisContext 对象还可以获取当前 sheet，当前行等数据
     */
    @Override
    public void invoke(Object object, AnalysisContext context) {
        if(!checkObjAllFieldsIsNull(object)) {
            //数据存储到list，供批量处理，或后续自己业务逻辑处理。
            dataList.add(object);
        }
                /*
        如数据过大，可以进行定量分批处理
        if(datas.size()<=100){
            datas.add(object);
        }else {
            doSomething();
            datas = new ArrayList<Object>();
        }
         */
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //do something
        /*
            dataList.clear();
            解析结束销毁不用的资源
         */
    }

    private static final String SERIAL_VERSION_UID = "serialVersionUID";

    /**
     * 判断对象中属性值是否全为空
     */
    private static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }
        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);
                //只校验带ExcelProperty注解的属性
                ExcelProperty property = f.getAnnotation(ExcelProperty.class);
                if(property == null || SERIAL_VERSION_UID.equals(f.getName())){
                    continue;
                }
                if (f.get(object) != null && MyStringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }
            }
        } catch (Exception e) {
            //do something
        }
        return true;
    }

    public List<Object> getDataList() {
        return dataList;
    }
}
