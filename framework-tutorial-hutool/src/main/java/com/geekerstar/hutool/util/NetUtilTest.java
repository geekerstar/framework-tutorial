package com.geekerstar.hutool.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.net.NetUtil;
import org.junit.Test;

/**
 * @author geekerstar
 * date: 2019/11/30 16:43
 * description: NetUtil 工具中主要的方法包括：
 *
 * longToIpv4 根据long值获取ip v4地址
 * ipv4ToLong 根据ip地址计算出long型的数据
 * isUsableLocalPort 检测本地端口可用性
 * isValidPort 是否为有效的端口
 * isInnerIP 判定是否为内网IP
 * localIpv4s 获得本机的IP地址列表
 * toAbsoluteUrl 相对URL转换为绝对URL
 * hideIpPart 隐藏掉IP地址的最后一部分为 * 代替
 * buildInetSocketAddress 构建InetSocketAddress
 * getIpByHost 通过域名得到IP
 * isInner 指定IP的long是否在指定范围内
 */
public class NetUtilTest {
    @Test
    public void test(){
        String ip1= "127.0.0.1";
        long iplong = 2130706433L;

        //根据long值获取ip v4地址
        String ip2= NetUtil.longToIpv4(iplong);
        Console.log(ip2);

        //根据ip地址计算出long型的数据
        long ip3= NetUtil.ipv4ToLong(ip1);
        Console.log(ip3);

        //检测本地端口可用性
        boolean result1= NetUtil.isUsableLocalPort(6379);
        Console.log(result1);

        //是否为有效的端口
        boolean result2= NetUtil.isValidPort(6379);
        Console.log(result2);

        //隐藏掉IP地址
        String result3 =NetUtil.hideIpPart(ip1);
        Console.log(result3);


    }
}
