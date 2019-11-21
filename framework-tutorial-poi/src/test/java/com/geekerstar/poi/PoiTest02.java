package com.geekerstar.poi;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

/**
 * 创建单元格写入内容
 */
public class PoiTest02 {


    public static void main(String[] args) throws Exception {
        //创建工作簿  HSSFWorkbook -- 2003
        Workbook wb = new XSSFWorkbook(); //2007版本
        //创建表单sheet
        Sheet sheet = wb.createSheet("test");
        //创建行对象  参数：索引（从0开始）
        Row row = sheet.createRow(2);
        //创建单元格对象  参数：索引（从0开始）
        Cell cell = row.createCell(2);
        //向单元格中写入内容
        cell.setCellValue("Geekerstar");
        //文件流
        FileOutputStream pis = new FileOutputStream("E:\\excel\\poi\\test1.xlsx");
        //写入文件
        wb.write(pis);
        pis.close();
    }
}
