package com.geekerstar.hutool.core.convert;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.lang.TypeReference;
import cn.hutool.core.util.CharsetUtil;
import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author geekerstar
 * date: 2019/11/30 10:24
 * description:
 */
public class ConvertTest {

    @Test
    public void test() {
        // 转换为字符串
        int a = 1;
        String aStr = Convert.toStr(a);
        //aStr为"1"
        System.out.println(aStr.getClass());

        long[] b = {1,2,3,4,5};
        String bStr = Convert.toStr(b);
        //bStr为："[1, 2, 3, 4, 5]"
        System.out.println(bStr);

        System.out.println("----------------------");


        // 转换为指定类型数组
        String[] b1 = { "1", "2", "3", "4" };
        //结果为Integer数组
        Integer[] intArray = Convert.toIntArray(b1);
        System.out.println(intArray[0]);


        long[] c1 = {1,2,3,4,5};
        //结果为Integer数组
        Integer[] intArray2 = Convert.toIntArray(c1);
        System.out.println(intArray2);

        System.out.println("-------------------------");

        // 转换为日期对象
        String a3 = "2019-05-06";
        Date value = Convert.toDate(a3);
        System.out.println(value);

        System.out.println("-------------------------");

        // 转换为集合
        Object[] a4 = {"a", "你", "好", "", 1};
        List<?> list4 = Convert.convert(List.class, a4);
        System.out.println(list4);

        //从4.1.11开始可以这么用
        List<?> list5 = Convert.toList(a4);
        System.out.println(list5);


        System.out.println("---------------");

        // 泛型类型转换
        Object[] a5 = { "a", "你", "好", "", 1 };
        List<String> list = Convert.convert(new TypeReference<List<String>>() {}, a5);
        System.out.println(list);

        System.out.println("----------------");

        // 半角转全角
        String a6 = "123456789";
        //结果为："１２３４５６７８９"
        String sbc = Convert.toSBC(a6);
        System.out.println(sbc);

        // 全角转半角
        String a7 = "１２３４５６７８９";
        //结果为"123456789"
        String dbc = Convert.toDBC(a7);
        System.out.println(dbc);

        // 转16进制
        String a8 = "我是一个小小的可爱的字符串";
        //结果："e68891e698afe4b880e4b8aae5b08fe5b08fe79a84e58fafe788b1e79a84e5ad97e7aca6e4b8b2"
        String hex = Convert.toHex(a8, CharsetUtil.CHARSET_UTF_8);
        System.out.println(hex);

        System.out.println("----------------------");


        // 16进制转换
        String hex1 = "e68891e698afe4b880e4b8aae5b08fe5b08fe79a84e58fafe788b1e79a84e5ad97e7aca6e4b8b2";
        //结果为："我是一个小小的可爱的字符串"
        String raw1 = Convert.hexStrToStr(hex1, CharsetUtil.CHARSET_UTF_8);
        System.out.println(raw1);

        //注意：在4.1.11之后hexStrToStr将改名为hexToStr
        String raw2 = Convert.hexToStr(hex1, CharsetUtil.CHARSET_UTF_8);
        System.out.println(raw2);

        System.out.println("----------------------------");


        // Unicode和字符串转换
        String a9 = "我是一个小小的可爱的字符串";
        //结果为："\\u6211\\u662f\\u4e00\\u4e2a\\u5c0f\\u5c0f\\u7684\\u53ef\\u7231\\u7684\\u5b57\\u7b26\\u4e32"
        String unicode = Convert.strToUnicode(a9);
        //结果为："我是一个小小的可爱的字符串"
        String raw3 = Convert.unicodeToStr(unicode);
        System.out.println(raw3);


        // 编码转换
        String a10 = "我不是乱码";
//转换后result为乱码
        String result = Convert.convertCharset(a10, CharsetUtil.UTF_8, CharsetUtil.ISO_8859_1);
        String raw = Convert.convertCharset(result, CharsetUtil.ISO_8859_1, "UTF-8");
        System.out.println(raw.equals(a10));

        System.out.println("------------------------");


        // 时间单位转换
        long a11 = 4535345;
        //结果为：75
        long minutes = Convert.convertTime(a11, TimeUnit.MILLISECONDS, TimeUnit.MINUTES);
        System.out.println(minutes);


        // 金额大小写
        double a12 = 67556.32;
        //结果为："陆万柒仟伍佰伍拾陆元叁角贰分"
        String digitUppercase = Convert.digitToChinese(a12);
        System.out.println(digitUppercase);

        System.out.println("---------------------");


        // 原始类和包装类转换
        //去包装
        Class<?> wrapClass = Integer.class;
        //结果为：int.class
        Class<?> unWraped = Convert.unWrap(wrapClass);
        System.out.println(unWraped);

        //包装
        Class<?> primitiveClass = long.class;
        //结果为：Long.class
        Class<?> wraped = Convert.wrap(primitiveClass);
        System.out.println(wraped);



    }
}
