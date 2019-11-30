package com.geekerstar.hutool.util;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.XmlUtil;
import org.junit.Test;
import org.w3c.dom.Document;

import javax.xml.xpath.XPathConstants;
import java.io.BufferedInputStream;

/**
 * @author geekerstar
 * date: 2019/11/30 16:26
 * description: https://www.hutool.cn/docs/#/core/%E5%B7%A5%E5%85%B7%E7%B1%BB/XML%E5%B7%A5%E5%85%B7-XmlUtil
 */
public class XmlUtilTest {
    @Test
    public void test(){
        BufferedInputStream xmlFile = FileUtil.getInputStream("/Users/geekerstar/work/ideaprojects/framework-tutorial/framework-tutorial-hutool/src/main/resources/file/test.xml");
        Document docResult= XmlUtil.readXML(xmlFile);
        //结果为“ok”
        Object value = XmlUtil.getByXPath("//returnsms/message", docResult, XPathConstants.STRING);
        Console.log(value);
    }
}
