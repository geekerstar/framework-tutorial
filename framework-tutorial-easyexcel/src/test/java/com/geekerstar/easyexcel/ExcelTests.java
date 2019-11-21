package com.geekerstar.easyexcel;

import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.metadata.Table;
import com.geekerstar.easyexcel.model.WriteModel;
import com.geekerstar.easyexcel.model.WriteModel2;
import com.geekerstar.easyexcel.util.DataUtil;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author geekerstar
 * date: 2019/9/19 21:37
 * description: Excel生成测试
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ExcelTests {

    private List<WriteModel> createModelList() {
        List<WriteModel> writeModels = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            WriteModel writeModel = WriteModel.builder()
                    .name("www.geekerstar.com"+i).password("hello easyexcel").age(i+1).build();
            writeModels.add(writeModel);
        }

        return writeModels;
    }

    private List<WriteModel2> createModel2List() {
        List<WriteModel2> writeModels2 = Lists.newArrayList();

        for (int i = 0; i < 100; i++) {
            WriteModel2 writeModel2 = WriteModel2.builder().orderNo(String.valueOf(i)).name("Geekerstar").createTime(LocalDateTime.now()).build();
            writeModels2.add(writeModel2);
        }

        return writeModels2;
    }

    /**
     * 无注解的实体类
     *
     * @return
     */
    private List<List<Object>> createComplexDataList() {
        List<List<Object>> datas = Lists.newArrayList();

        for (int i = 0; i < 90; i++) {
            List<Object> objects = new ArrayList<Object>();
            objects.add(i + 1);
            objects.add("按时间卢卡申科大街上来看的骄傲山东矿机奥斯卡了");
            objects.add("独立开发撒旦法卡视角拉丝机奥迪说的啊啊三大");
            objects.add("敬爱的谁看得见爱神的箭爱上了" + i);
            objects.add("阿斯达阿斯达实打实大声道范德萨份额啊");
            objects.add("的阿斯达连裤袜OK打破斯柯达怕是快递费");
            objects.add("福克斯解放路设定接口拉萨的建档立卡审计单位骄傲的就爱上了肯德基拉丝机的立刻就付款胜利大街发类似的就发了世纪大厦考虑到捡垃圾我骄傲");
            objects.add("蓝卡队结束啦开始交代理费撒娇打死了空间拉拉肥");
            objects.add(-1);
            objects.add(21);
            objects.add(i);
            objects.add(21);
            datas.add(objects);
        }

        return datas;
    }

    /**
     * 无注解的实体类
     *
     * @return
     */
    private List<List<Object>> createDynamicModelList() {
        // 所有行数据
        List<List<Object>> rows = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            // 一行数据
            List<Object> row = new ArrayList<>();
            row.add("字符串" + i);
            row.add(187837834L + i);
            row.add(2233 + i);
            row.add("Geekerstar");
            row.add("www.geekerstar.com");
            rows.add(row);
        }

        return rows;
    }

    /**
     * 简单场景
     * @throws Exception
     */
    @Test
    public void writeExcel1() throws Exception {
        // 文件输出位置
        OutputStream out = new FileOutputStream("/Users/geekerstar/Desktop/简单场景.xlsx");

        ExcelWriter writer = EasyExcelFactory.getWriter(out);

        // 写仅有一个 Sheet 的 Excel 文件, 此场景较为通用
        Sheet sheet1 = new Sheet(1, 0, WriteModel.class);

        // 第一个 sheet 名称
        sheet1.setSheetName("第一个sheet");

        // 写数据到 Writer 上下文中
        // 入参1: 创建要写入的模型数据
        // 入参2: 要写入的目标 sheet
        writer.write(createModelList(), sheet1);

        // 将上下文中的最终 outputStream 写入到指定文件中
        writer.finish();

        // 关闭流
        out.close();
    }

    /**
     * 动态生成
     * @throws Exception
     */
    @Test
    public void writeExcel2() throws Exception {
        // 文件输出位置
        OutputStream out = new FileOutputStream("/Users/geekerstar/Desktop/动态生成场景.xlsx");

        ExcelWriter writer = EasyExcelFactory.getWriter(out);

        // 动态添加表头，适用一些表头动态变化的场景
        Sheet sheet1 = new Sheet(1, 0);

        sheet1.setSheetName("第一个sheet");

        // 创建一个表格，用于 Sheet 中使用
        Table table1 = new Table(1);

        // 自定义表格样式
        table1.setTableStyle(DataUtil.createTableStyle());

        // 无注解的模式，动态添加表头
        table1.setHead(DataUtil.createTestListStringHead());
        // 写数据
        writer.write1(createDynamicModelList(), sheet1, table1);

        // 合并单元格
        writer.merge(5, 6, 0, 4);

        // 将上下文中的最终 outputStream 写入到指定文件中
        writer.finish();

        // 关闭流
        out.close();
    }


    /**
     * 数据存储到多个sheet
     * @throws Exception
     */
    @Test
    public void writeExcelSimple() throws Exception {
        OutputStream out = new FileOutputStream("/Users/geekerstar/Desktop/多个sheet场景.xlsx");
        ExcelWriter writer = EasyExcelFactory.getWriter(out);
        // ==================================== Start ====================================
        // 写仅有一个 Sheet 的 Excel, 此场景较为通用
        Sheet sheet1 = new Sheet(1, 0, WriteModel2.class);
        sheet1.setSheetName("第一个sheet");
        writer.write(createModel2List(), sheet1);
        // ===================================== End =====================================

        // ==================================== Start ====================================
        // 合并单元格
        Sheet sheet3 = new Sheet(3, 0, WriteModel.class, "第三个sheet", null);
        //writer.write1(null, sheet2);
        writer.write(createModelList(), sheet3);
        // 需要合并单元格
        writer.merge(5, 6, 1, 5);
        // ===================================== End =====================================

        // ==================================== Start ====================================
        // 单个 Sheet 中包含多个 Table
        Sheet sheet4 = new Sheet(4, 0);
        sheet4.setSheetName("第四个sheet");

        Table sheet4table1 = new Table(1);
        sheet4table1.setClazz(WriteModel.class);
        writer.write(createModelList(), sheet4, sheet4table1);

        Table sheet4table2 = new Table(2);
        sheet4table2.setClazz(WriteModel2.class);
        writer.write(createModel2List(), sheet4, sheet4table2);
        // ===================================== End =====================================

        writer.finish();
        out.close();
    }

    /**
     * Invalid row number (1048576) outside allowable range (0..1048575)
     * 07 版 excel 单个 sheet 允许的最大行数是 1048576
     *
     * @throws Exception
     */
    @Test
    public void writeComplexExcel() throws Exception {
        OutputStream out = new FileOutputStream("/Users/geekerstar/Desktop/复杂.xlsx");
        ExcelWriter writer = EasyExcelFactory.getWriter(out);

        Sheet sheet1 = new Sheet(1, 0);
        sheet1.setSheetName("复杂样式导出");
        // 设置列的宽度
        Map columnWidth = new HashMap();
        columnWidth.put(0, 10);
        sheet1.setColumnWidthMap(columnWidth);
        sheet1.setAutoWidth(true);
        Table table1 = new Table(1);

        // 自定义表格样式
        table1.setTableStyle(DataUtil.createComplexTableStyle());
        // 无注解的模式，动态添加表头
        table1.setHead(DataUtil.createTestListStringHead());

        // 内容
        for (int i = 0; i < 2; i++) {
            writer.write1(createComplexDataList(), sheet1, table1);
        }

        // 最后一行，总价合计
        List<List<Object>> lastLine = new ArrayList<>();
        List<Object> objects = new ArrayList<Object>();
        objects.add("总价合计：21");
        lastLine.add(objects);
        writer.write1(lastLine, sheet1, table1);

        // 合并单元格 todo
        writer.merge(12, 12, 0, 11);


        writer.finish();
        out.close();
    }


}
