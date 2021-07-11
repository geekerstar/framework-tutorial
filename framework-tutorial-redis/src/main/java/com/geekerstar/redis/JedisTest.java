package com.geekerstar.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.List;

public class JedisTest {

    public static void main(String[] args) throws Exception {
        Jedis jedis = new Jedis("127.0.0.1");

        // 最简单的缓存读写示例
        jedis.set("key1", "value1");
        System.out.println(jedis.get("key1"));

        // 最简单的基于nx选项实现的分布式锁
        jedis.del("lock_test");

        String result = jedis.set("lock_test", "value_test",
                SetParams.setParams().nx());
        System.out.println("第一次加锁的结果：" + result);

        result = jedis.set("lock_test", "value_test",
                SetParams.setParams().nx());
        System.out.println("第二次加锁的结果：" + result);

        jedis.del("lock_test");

        result = jedis.set("lock_test", "value_test",
                SetParams.setParams().nx());
        System.out.println("第二次加锁的结果：" + result);

        // 博客的发布、修改与查看
        Long publishBlogResult = jedis.msetnx("article:1:title", "学习Redis",
                "article:1:content", "如何学好redis的使用",
                "article:1:author", "中华石杉",
                "article:1:time", "2020-01-01 00:00:00");
        System.out.println("发布博客的结果：" + publishBlogResult);

        List<String> blog = jedis.mget("article:1:title", "article:1:content",
                "article:1:author", "article:1:time");
        System.out.println("查看博客：" + blog);

        String updateBlogResult = jedis.mset("article:1:title", "修改后的学习redis",
                "article:1:content", "修改后的如何学好redis的使用");
        System.out.println("修改博客的结果：" + updateBlogResult);

        blog = jedis.mget("article:1:title", "article:1:content",
                "article:1:author", "article:1:time");
        System.out.println("再次查看博客：" + blog);

        Long blogLength = jedis.strlen("article:1:content");
        System.out.println("博客的长度统计：" + blogLength);

        String blogContentPreview = jedis.getrange(
                "article:1:content", 0, 5);
        System.out.println("博客内容预览：" + blogContentPreview);

        // 操作日志的审计功能
        jedis.del("operation_log_2020_01_01");
        jedis.setnx("operation_log_2020_01_01", "");

        for(int i = 0; i < 10; i++) {
            jedis.append("operation_log_2020_01_01", "今天的第" + (i + 1) + "条操作日志\n");
        }

        String operationLog = jedis.get("operation_log_2020_01_01");
        System.out.println("今天所有的操作日志：\n" + operationLog);

        // 唯一ID生成器
        jedis.del("order_id_counter");

        for(int i = 0; i < 10; i++) {
            Long orderId = jedis.incr("order_id_counter");
            System.out.println("生成的第" + (i + 1) + "个唯一ID：" + orderId);
        }

        // 博客的点赞计数器
        jedis.del("article:1:dianzan");

        for(int i = 0; i < 10; i++) {
            jedis.incr("article:1:dianzan");
        }
        Long dianzanCounter = Long.valueOf(jedis.get("article:1:dianzan"));
        System.out.println("博客的点赞次数为：" + dianzanCounter);

        jedis.decr("article:1:dianzan");
        dianzanCounter = Long.valueOf(jedis.get("article:1:dianzan"));
        System.out.println("再次查看博客的点赞次数为：" + dianzanCounter);
    }

}
