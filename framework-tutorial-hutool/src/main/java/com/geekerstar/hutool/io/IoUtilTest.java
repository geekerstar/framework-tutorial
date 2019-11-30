package com.geekerstar.hutool.io;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;

/**
 * @author geekerstar
 * date: 2019/11/30 14:45
 * description: https://www.hutool.cn/docs/#/core/IO/IO%E5%B7%A5%E5%85%B7%E7%B1%BB-IoUtil
 */
public class IoUtilTest {

    @Test
    public void test(){
        /**
         * 拷贝
         * 流的读写可以总结为从输入流读取，从输出流写出，这个过程我们定义为拷贝。这个是一个基本过程，也是文件、流操作的基础。
         * copy方法同样针对Reader、Writer、Channel等对象有一些重载方法，并提供可选的缓存大小。默认的，缓存大小为1024个字节，如果拷贝大文件或流数据较大，可以适当调整这个参数。
         */
        BufferedInputStream in = FileUtil.getInputStream("/Users/geekerstar/work/ideaprojects/framework-tutorial/framework-tutorial-hutool/src/main/resources/file/1.txt");
        BufferedOutputStream out = FileUtil.getOutputStream("/Users/geekerstar/work/ideaprojects/framework-tutorial/framework-tutorial-hutool/src/main/resources/file/2.txt");
        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
        Console.log(copySize);

        /**
         * Stream转Reader、Writer
         * IoUtil.getReader：将InputStream转为BufferedReader用于读取字符流，它是部分readXXX方法的基础。
         * IoUtil.getWriter：将OutputStream转为OutputStreamWriter用于写入字符流，它是部分writeXXX的基础。
         */


    }
}
