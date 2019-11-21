package com.geekerstar.poi;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

/**
 * 使用POI创建excel
 */
public class PoiTest01 {


    public static void main(String[] args) throws Exception {
        //1.创建工作簿  HSSFWorkbook -- 2003
        Workbook wb = new XSSFWorkbook(); //2007版本
        //2.创建表单sheet
        Sheet sheet = wb.createSheet("test");
        //3.文件流
        FileOutputStream pis = new FileOutputStream("E:\\excel\\poi\\test.xlsx");
        //4.写入文件
        wb.write(pis);
        pis.close();
    }
}
