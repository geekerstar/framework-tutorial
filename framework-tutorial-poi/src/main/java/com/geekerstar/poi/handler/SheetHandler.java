package com.geekerstar.poi.handler;

import com.geekerstar.poi.entity.PoiEntity;
import org.apache.poi.xssf.eventusermodel.XSSFSheetXMLHandler;
import org.apache.poi.xssf.usermodel.XSSFComment;

/**
 * 自定义的事件处理器
 *  处理每一行数据读取
 *      实现接口
 */
public class SheetHandler implements XSSFSheetXMLHandler.SheetContentsHandler {

    private PoiEntity entity;
    /**
     * 当开始解析某一行的时候触发
     *      i:行索引
     */
    @Override
    public void startRow(int i) {
        //实例化对象
        if(i>0) {
            entity = new PoiEntity();
        }
    }

    /**
     * 当结束解析某一行的时候触发
     *      i:行索引
     */
    @Override
    public void endRow(int i) {
        //使用对象进行业务操作
        System.out.println(entity);
    }

    /**
     * 对行中的每一个表格进行处理
     *      cellReference: 单元格名称
     *      value：数据
     *      xssfComment：批注
     */
    @Override
    public void cell(String cellReference, String value, XSSFComment xssfComment) {
        //对对象属性赋值
        if(entity != null) {
            String pix = cellReference.substring(0,1);
            switch (pix) {
                case "A":
                    entity.setId(value);
                    break;
                case "B":
                    entity.setBreast(value);
                    break;
                case "C":
                    entity.setAdipocytes(value);
                    break;
                case "D":
                    entity.setNegative(value);
                    break;
                case "E":
                    entity.setStaining(value);
                    break;
                case "F":
                    entity.setSupportive(value);
                    break;
                default:
                    break;
            }
        }
    }
}
