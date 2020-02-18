package com.geekerstar.hutool.core.date;

import cn.hutool.core.date.*;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * @author geekerstar
 * date: 2019/11/30 11:22
 * description:
 */
public class DateUtilTest {

    @Test
    public void test1(){
        /**
         * Date、long、Calendar之间的相互转换
         */
        //当前时间
        Date date = DateUtil.date();
        System.out.println(date);

        //当前时间
        Date date2 = DateUtil.date(Calendar.getInstance());
        System.out.println(Calendar.getInstance());
        System.out.println(date2);

        //当前时间
        Date date3 = DateUtil.date(System.currentTimeMillis());
        System.out.println(System.currentTimeMillis());
        System.out.println(date3);

        //当前时间字符串，格式：yyyy-MM-dd HH:mm:ss
        String now = DateUtil.now();
        System.out.println(now);

        //当前日期字符串，格式：yyyy-MM-dd
        String today= DateUtil.today();
        System.out.println(today);


        /**
         * 字符串转日期
         *
         * DateUtil.parse方法会自动识别一些常用格式，包括：
         *
         * yyyy-MM-dd HH:mm:ss
         * yyyy-MM-dd
         * HH:mm:ss
         * yyyy-MM-dd HH:mm
         * yyyy-MM-dd HH:mm:ss.SSS
         */
        String dateStr = "2017-03-01";
        Date date4 = DateUtil.parse(dateStr);
        System.out.println(date4);

        String dateStr1 = "2017-03-02";
        Date date5 = DateUtil.parse(dateStr1, "yyyy-MM-dd");
        System.out.println(date5);


        System.out.println("----------");

        /**
         * 格式化日期输出
         */
        String dateStr2 = "2017-03-01";
        Date date6 = DateUtil.parse(dateStr2);
        //结果 2017/03/01
        String format = DateUtil.format(date6, "yyyy/MM/dd");
        System.out.println(format);

        //常用格式的格式化，结果：2017-03-01
        String formatDate = DateUtil.formatDate(date6);
        System.out.println(formatDate);

        //结果：2017-03-01 00:00:00
        String formatDateTime = DateUtil.formatDateTime(date6);
        System.out.println(formatDateTime);

        //结果：00:00:00
        String formatTime = DateUtil.formatTime(date6);
        System.out.println(formatTime);

        System.out.println("----------------");


        /**
         * 获取Date对象的某个部分
         */
        Date date7 = DateUtil.date();
        //获得年的部分
        DateUtil.year(date7);
        //获得月份，从0开始计数
        DateUtil.month(date7);
        //获得月份枚举
        Month month = DateUtil.monthEnum(date7);
        System.out.println(month);

        System.out.println("-----------------------");

        /**
         * 开始和结束时间
         */
        String dateStr3 = "2017-03-01 22:33:23";
        Date date8 = DateUtil.parse(dateStr);
        System.out.println(date8);

        //一天的开始，结果：2017-03-01 00:00:00
        Date beginOfDay = DateUtil.beginOfDay(date);
        System.out.println(beginOfDay);

        //一天的结束，结果：2017-03-01 23:59:59
        Date endOfDay = DateUtil.endOfDay(date);
        System.out.println(endOfDay);

        System.out.println("--------------------");


        /**
         * 日期时间偏移
         */
        String dateStr4 = "2017-03-01 22:33:23";
        Date date9 = DateUtil.parse(dateStr);
        System.out.println(date9);


        //结果：2017-03-03 22:33:23
        Date newDate = DateUtil.offset(date9, DateField.DAY_OF_MONTH, 2);
        System.out.println(newDate);


        //常用偏移，结果：2017-03-04 22:33:23
        DateTime newDate2 = DateUtil.offsetDay(date9, 3);
        System.out.println(newDate2);


        //常用偏移，结果：2017-03-01 19:33:23
        DateTime newDate3 = DateUtil.offsetHour(date9, -3);
        System.out.println(newDate3);


//        //昨天
//        DateUtil.yesterday()
//        //明天
//        DateUtil.tomorrow()
//        //上周
//        DateUtil.lastWeek()
//        //下周
//        DateUtil.nextWeek()
//        //上个月
//        DateUtil.lastMonth()
//        //下个月
//        DateUtil.nextMonth()

        System.out.println("------------------");


        // 日期时间差
        String dateStr11 = "2017-03-01 22:33:23";
        Date date11 = DateUtil.parse(dateStr11);
        System.out.println(date11);

        String dateStr21 = "2017-04-01 23:33:23";
        Date date21 = DateUtil.parse(dateStr21);
        System.out.println(date21);

        //相差一个月，31天
        long betweenDay = DateUtil.between(date11, date21, DateUnit.DAY);
        System.out.println(betweenDay);

        /**
         * 格式化时间差
         */
//        //Level.MINUTE表示精确到分
//        String formatBetween = DateUtil.formatBetween(between, Level.MINUTE);
////输出：31天1小时
//        Console.log(formatBetween);

        System.out.println("--------------------");


        /**
         * 计时器
         */
        TimeInterval timer = DateUtil.timer();
        //---------------------------------
        //-------这是执行过程
        //---------------------------------
        timer.interval();//花费毫秒数
        timer.intervalRestart();//返回花费时间，并重置开始时间
        timer.intervalMinute();//花费分钟数


        //年龄
        DateUtil.ageOfNow("1990-01-30");

        //是否闰年
        DateUtil.isLeapYear(2017);



    }
}
