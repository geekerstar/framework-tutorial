package com.geekerstar.redis.sortedset;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * 新闻浏览案例
 */
public class NewsDemo {

    private Jedis jedis = new Jedis("127.0.0.1");

    /**
     * 加入一篇新闻
     * @param newsId
     */
    public void addNews(long newsId, long timestamp) {
        jedis.zadd("news", timestamp, String.valueOf(newsId));
    }

    /**
     * 搜索新闻
     * @param maxTimestamp
     * @param minTimestamp
     * @param index
     * @param count
     * @return
     */
    public Set<Tuple> searchNews(long maxTimestamp, long minTimestamp, int index , int count) {
        return jedis.zrevrangeByScoreWithScores("news", maxTimestamp, minTimestamp, index, count);
    }

    public static void main(String[] args) throws Exception {
        NewsDemo demo = new NewsDemo();

        for(int i = 0; i < 20; i++) {
            demo.addNews(i + 1, i + 1);
        }

        long maxTimestamp = 18;
        long minTimestamp = 2;

        int pageNo = 1;
        int pageSize = 10;
        int startIndex = (pageNo - 1) * 10;

        Set<Tuple> searchResult = demo.searchNews(
                maxTimestamp, minTimestamp, startIndex, pageSize);

        System.out.println("搜索指定时间范围内的新闻的第一页：" + searchResult);
    }

}
