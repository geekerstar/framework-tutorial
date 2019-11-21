package com.geekerstar.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;

/**
 * 单元格样式处理
 */
public class PoiTest03 {


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

        //样式处理
        //创建样式对象
        CellStyle style = wb.createCellStyle();
        style.setBorderTop(BorderStyle.THIN);//上边框
        style.setBorderBottom(BorderStyle.THIN);//下边框
        style.setBorderLeft(BorderStyle.THIN);//左边框
        style.setBorderRight(BorderStyle.THIN);//右边框
        //创建字体对象
        Font font = wb.createFont();
        font.setFontName("华文行楷"); //字体
        font.setFontHeightInPoints((short)28);//字号
        style.setFont(font);

        //行高和列宽
        row.setHeightInPoints(50);//行高
        //列宽的宽度  字符宽度
        sheet.setColumnWidth(2,31 * 256);//列宽

        //剧中显示
        style.setAlignment(HorizontalAlignment.CENTER);//水平居中
        style.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        //向单元格设置样式
        cell.setCellStyle(style);

        //文件流
        FileOutputStream pis = new FileOutputStream("E:\\excel\\poi\\test2.xlsx");
        //写入文件
        wb.write(pis);
        pis.close();
    }
}
