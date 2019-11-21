package com.geekerstar.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * 插入图片
 */
public class PoiTest04 {


    public static void main(String[] args) throws Exception {
        //创建工作簿  HSSFWorkbook -- 2003
        Workbook wb = new XSSFWorkbook(); //2007版本
        //创建表单sheet
        Sheet sheet = wb.createSheet("test");

        //读取图片流
        FileInputStream stream = new FileInputStream("E:\\excel\\poi\\logo.jpg");
        //转化二进制数组
        byte[] bytes = IOUtils.toByteArray(stream);
        stream.read(bytes);
        //向POI内存中添加一张图片，返回图片在图片集合中的索引
        int index = wb.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);//参数一：图片的二进制数据，参数二：图片类型
        //绘制图片工具类
        CreationHelper helper = wb.getCreationHelper();
        //创建一个绘图对象
        Drawing<?> patriarch = sheet.createDrawingPatriarch();
        //创建锚点，设置图片坐标
        ClientAnchor anchor = helper.createClientAnchor();
        anchor.setRow1(0);
        anchor.setCol1(0);
        //绘制图片
        Picture picture = patriarch.createPicture(anchor, index);//图片位置，图片的索引
        picture.resize();//自适应渲染图片

        //文件流
        FileOutputStream pis = new FileOutputStream("E:\\excel\\poi\\test3.xlsx");
        //写入文件
        wb.write(pis);
        pis.close();
    }
}
