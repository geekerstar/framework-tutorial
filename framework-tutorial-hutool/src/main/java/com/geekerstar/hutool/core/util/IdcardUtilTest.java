package com.geekerstar.hutool.core.util;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.IdcardUtil;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author geekerstar
 * date: 2019/11/30 16:58
 * description:
 * IdcardUtil现在支持大陆15位、18位身份证，港澳台10位身份证。
 *
 * 工具中主要的方法包括：
 *
 * isValidCard 验证身份证是否合法
 * convert15To18 身份证15位转18位
 * getBirthByIdCard 获取生日
 * getAgeByIdCard 获取年龄
 * getYearByIdCard 获取生日年
 * getMonthByIdCard 获取生日月
 * getDayByIdCard 获取生日天
 * getGenderByIdCard 获取性别
 * getProvinceByIdCard 获取省份
 */
public class IdcardUtilTest {
    @Test
    public void test(){
        String ID_18 = "321083197812162119";
        String ID_15 = "150102880730303";

        //是否有效
        boolean valid = IdcardUtil.isValidCard(ID_18);
        boolean valid15 = IdcardUtil.isValidCard(ID_15);
        Console.log(valid);
        Console.log(valid15);

        //转换
        String convert15To18 = IdcardUtil.convert15To18(ID_15);
        Assert.assertEquals(convert15To18, "150102198807303035");

        //年龄
        DateTime date = DateUtil.parse("2017-04-10");

        int age = IdcardUtil.getAgeByIdCard(ID_18, date);
        Assert.assertEquals(age, 38);

        int age2 = IdcardUtil.getAgeByIdCard(ID_15, date);
        Assert.assertEquals(age2, 28);

        //生日
        String birth = IdcardUtil.getBirthByIdCard(ID_18);
        Assert.assertEquals(birth, "19781216");

        String birth2 = IdcardUtil.getBirthByIdCard(ID_15);
        Assert.assertEquals(birth2, "19880730");

        //省份
        String province = IdcardUtil.getProvinceByIdCard(ID_18);
        Assert.assertEquals(province, "江苏");

        String province2 = IdcardUtil.getProvinceByIdCard(ID_15);
        Assert.assertEquals(province2, "内蒙古");

    }
}
