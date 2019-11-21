package com.geekerstar.poi;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 读取excel并解析
 *      sheet.getLastRowNum() : 最后一行的索引
 *      row.getLastCellNum() ： 最后一个单元格的号码
 */
public class PoiTest05 {

    public static void main(String[] args) throws Exception {
        //1.根据Excel文件创建工作簿
        Workbook wb = new XSSFWorkbook("C:\\Users\\ThinkPad\\demo.xlsx");
        //2.获取Sheet
        Sheet sheet = wb.getSheetAt(0);//参数：索引
        //3.获取Sheet中的每一行，和每一个单元格
        for (int rowNum = 0; rowNum<= sheet.getLastRowNum() ;rowNum ++) {
            Row row = sheet.getRow(rowNum);//根据索引获取每一个行
            StringBuilder sb = new StringBuilder();
            for(int cellNum=2;cellNum< row.getLastCellNum(); cellNum ++) {
                //根据索引获取每一个单元格
                Cell cell = row.getCell(cellNum);
                //获取每一个单元格的内容
                Object value = getCellValue(cell);
                sb.append(value).append("-");
            }
            System.out.println(sb.toString());
        }
    }

    public static Object getCellValue(Cell cell) {
        //1.获取到单元格的属性类型
        CellType cellType = cell.getCellType();
        //2.根据单元格数据类型获取数据
        Object value = null;
        switch (cellType) {
            case STRING:
                value = cell.getStringCellValue();
                break;
            case BOOLEAN:
                value = cell.getBooleanCellValue();
                break;
            case NUMERIC:
                if(DateUtil.isCellDateFormatted(cell)) {
                    //日期格式
                    value = cell.getDateCellValue();
                }else{
                    //数字
                    value = cell.getNumericCellValue();
                }
                break;
            case FORMULA: //公式
                value = cell.getCellFormula();
                break;
            default:
                break;
        }
        return value;
    }
}
