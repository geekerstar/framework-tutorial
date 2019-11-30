package com.geekerstar.hutool.io;

import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.io.File;

/**
 * @author geekerstar
 * date: 2019/11/30 15:10
 * description: https://www.hutool.cn/docs/#/core/IO/%E6%96%87%E4%BB%B6%E7%B1%BB%E5%9E%8B%E5%88%A4%E6%96%AD-FileTypeUtil
 */
public class FileTypeUtilTest {
    @Test
    public void test(){
        File file = FileUtil.file("/Users/geekerstar/work/ideaprojects/framework-tutorial/framework-tutorial-hutool/src/main/resources/file/1.txt");
        String type = FileTypeUtil.getType(file);
        //输出 jpg则说明确实为jpg文件
        Console.log(type);
    }
}
