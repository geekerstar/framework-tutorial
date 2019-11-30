package com.geekerstar.hutool.date;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.Month;
import cn.hutool.core.lang.Console;
import org.junit.Test;

import java.util.Date;

/**
 * @author geekerstar
 * date: 2019/11/30 14:26
 * description:
 */
public class DateTimeTest {

    @Test
    public void test(){
        /**
         * 新建对象
         */
        Date date = new Date();

        //new方式创建
        DateTime time = new DateTime(date);
        Console.log(time);

        //of方式创建
        DateTime now = DateTime.now();
        Console.log(now);

        DateTime dt = DateTime.of(date);
        Console.log(dt);

        System.out.println("--------------------");


        /**
         * 使用对象
         */
        DateTime dateTime = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);

        //年，结果：2017
        int year = dateTime.year();
        Console.log(year);
        //季度（非季节），结果：Season.SPRING
//        Season season = dateTime.seasonEnum();

        //月份，结果：Month.JANUARY
        Month month = dateTime.monthEnum();
        Console.log(month);

        //日，结果：5
        int day = dateTime.dayOfMonth();
        Console.log(day);

        System.out.println("------------");


        /**
         * 对象的可变性
         * DateTime对象默认是可变对象（调用offset、setField、setTime方法默认变更自身），但是这种可变性有时候会引起很多问题（例如多个地方共用DateTime对象）。我们可以调用setMutable(false)方法使其变为不可变对象。在不可变模式下，offset、setField方法返回一个新对象，setTime方法抛出异常
         */
        DateTime dateTime1 = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);

        //默认情况下DateTime1为可变对象，此时offset == dateTime1
        DateTime offset = dateTime1.offset(DateField.YEAR, 0);
        Console.log(offset);

        //设置为不可变对象后变动将返回新对象，此时offset != dateTime1
        dateTime.setMutable(false);
        offset = dateTime.offset(DateField.YEAR, 0);
        Console.log(offset);

        /**
         * 格式化为字符串
         * 调用toString()方法即可返回格式为yyyy-MM-dd HH:mm:ss的字符串，调用toString(String format)可以返回指定格式的字符串
         */
        DateTime dateTime2 = new DateTime("2017-01-05 12:34:23", DatePattern.NORM_DATETIME_FORMAT);
        //结果：2017-01-05 12:34:23
        String dateStr = dateTime2.toString();
        Console.log(dateStr);
        //结果：2017/01/05
    }
}
