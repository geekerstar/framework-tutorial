package com.geekerstar.hutool.core.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import org.junit.Test;

/**
 * @author geekerstar
 * date: 2019/11/30 16:10
 * description: HexUtil主要以encodeHex和decodeHex两个方法为核心，提供一些针对字符串的重载方法。
 */
public class HexUtilTest {

    @Test
    public void test(){
        String str = "我是一个字符串";
        String hex = HexUtil.encodeHexStr(str, CharsetUtil.CHARSET_UTF_8);
        Console.log(hex);

        //hex是：
        //e68891e698afe4b880e4b8aae5ad97e7aca6e4b8b2
        String decodedStr = HexUtil.decodeHexStr(hex);
        Console.log(decodedStr);
        //解码后与str相同
    }
}
