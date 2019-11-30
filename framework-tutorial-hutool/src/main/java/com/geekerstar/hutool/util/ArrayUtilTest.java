package com.geekerstar.hutool.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ArrayUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author geekerstar
 * date: 2019/11/30 16:36
 * description:
 */
public class ArrayUtilTest {
    @Test
    public void test(){
        // 判空
        int[] a = {};
        int[] b = null;
        boolean empty = ArrayUtil.isEmpty(a);
        boolean empty1 = ArrayUtil.isEmpty(b);
        Console.log(empty);
        Console.log(empty1);
        // 判断非空
        int[] c = {1,2};
        ArrayUtil.isNotEmpty(c);

        // 新建泛型数组
        String[] newArray = ArrayUtil.newArray(String.class, 3);
        Console.log(newArray);

        // 泛型数组调用原生克隆
        Integer[] d = {1,2,3};
        Integer[] cloneB = ArrayUtil.clone(d);
        Assert.assertArrayEquals(d, cloneB);

        // 非泛型数组（原始类型数组）调用第二种重载方法
        int[] e = {1,2,3};
        int[] clone = ArrayUtil.clone(e);
        Assert.assertArrayEquals(e, clone);


    }
}
