package com.geekerstar.hutool.convert;

import cn.hutool.core.convert.Converter;
import cn.hutool.core.convert.ConverterRegistry;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author geekerstar
 * date: 2019/11/30 11:08
 * description:
 */
public class ConverterRegistryTest {

    @Test
    public void test1(){
        int a = 3423;
        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        String result = converterRegistry.convert(String.class, a);
        Assert.assertEquals("3423", result);
    }

    // 1.自定义转换器
    public static class CustomConverter implements Converter<String> {
        @Override
        public String convert(Object value, String defaultValue) throws IllegalArgumentException {
            return "Custom: " + value.toString();
        }
    }

    @Test
    public void test2(){
        // 2.注册转换器
        ConverterRegistry converterRegistry = ConverterRegistry.getInstance();
        //此处做为示例自定义String转换，因为Hutool中已经提供String转换，请尽量不要替换
        //替换可能引发关联转换异常（例如覆盖String转换会影响全局）
        converterRegistry.putCustom(String.class, CustomConverter.class);
        // 3.执行转换
        int a = 454553;
        String result = converterRegistry.convert(String.class, a);
        Assert.assertEquals("Custom: 454553", result);

    }



}

