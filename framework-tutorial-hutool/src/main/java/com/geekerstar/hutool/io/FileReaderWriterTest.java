package com.geekerstar.hutool.io;

import cn.hutool.core.io.file.FileReader;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.lang.Console;
import org.junit.Test;

/**
 * @author geekerstar
 * date: 2019/11/30 15:14
 * description:
 */
public class FileReaderWriterTest {

    @Test
    public void FileReader(){
        //默认UTF-8编码，可以在构造中传入第二个参数做为编码
        FileReader fileReader = new FileReader("/Users/geekerstar/work/ideaprojects/framework-tutorial/framework-tutorial-hutool/src/main/resources/file/1.txt");
        String result = fileReader.readString();
        Console.log(result);

    }

    @Test
    public void FileWriter(){
        FileWriter writer = new FileWriter("/Users/geekerstar/work/ideaprojects/framework-tutorial/framework-tutorial-hutool/src/main/resources/file/2.txt");
        writer.write("test");

    }

}
