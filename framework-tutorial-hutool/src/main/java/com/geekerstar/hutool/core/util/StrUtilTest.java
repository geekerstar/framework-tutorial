package com.geekerstar.hutool.core.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.StrUtil;
import org.junit.Test;

/**
 * @author geekerstar
 * date: 2019/11/30 15:46
 * description:
 */
public class StrUtilTest {

    @Test
    public void test(){
        // removePrefix、removeSuffix方法
        String fileName = StrUtil.removeSuffix("pretty_girl.jpg", ".jpg");  //fileName -> pretty_girl
        Console.log(fileName);

        // 字符串截取
        String str = "abcdefgh";
        String strSub1 = StrUtil.sub(str, 2, 3); //strSub1 -> c
        Console.log(strSub1);
        String strSub2 = StrUtil.sub(str, 2, -3); //strSub2 -> cde
        Console.log(strSub2);
        String strSub3 = StrUtil.sub(str, 3, 2); //strSub2 -> c
        Console.log(strSub3);

        // format方法
        String template = "{}爱{}，就像老鼠爱大米";
        String str1 = StrUtil.format(template, "我", "你"); //str -> 我爱你，就像老鼠爱大米
        Console.log(str1);


    }
}
