package com.geekerstar.hutool.poi;

import cn.hutool.core.io.FileUtil;
import cn.hutool.poi.word.Word07Writer;
import org.junit.Test;

import java.awt.*;

/**
 * @author geekerstar
 * @date 2020/2/18 17:44
 * @description https://www.hutool.cn/docs/#/poi/Word%E7%94%9F%E6%88%90-Word07Writer
 */
public class Word07WriterTest {

    private String path ="/Users/geekerstar/work/ideaprojects/framework-tutorial/framework-tutorial-hutool/src/main/resources";

    @Test
    public void word(){
        Word07Writer writer = new Word07Writer();

        // 添加段落（标题）
        writer.addText(new Font("方正小标宋简体", Font.PLAIN, 22), "我是第一部分", "我是第二部分");
        // 添加段落（正文）
        writer.addText(new Font("宋体", Font.PLAIN, 22), "我是正文第一部分", "我是正文第二部分");
        // 写出到文件
        writer.flush(FileUtil.file(path+"/poi/wordWrite.docx"));
        // 关闭
        writer.close();

    }
}
