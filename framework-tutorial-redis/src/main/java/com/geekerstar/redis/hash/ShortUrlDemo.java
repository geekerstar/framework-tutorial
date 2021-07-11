package com.geekerstar.redis.hash;

import redis.clients.jedis.Jedis;

/**
 * 短网址追踪案例
 */
public class ShortUrlDemo {

    private static final String[] X36_ARRAY = "0,1,2,3,4,5,6,7,8,9,A,B,C,D,E,F,G,H,I,J,K,L,M,N,O,P,Q,R,S,T,U,V,W,X,Y,Z".split(",");

    private Jedis jedis = new Jedis("127.0.0.1");

    public ShortUrlDemo() {
        jedis.set("short_url_seed", "51167890045");
    }

    /**
     * 获取短连接网址
     * @param url
     * @return
     */
    public String getShortUrl(String url) {
        long shortUrlSeed = jedis.incr("short_url_seed");

        StringBuffer buffer = new StringBuffer();
        while(shortUrlSeed > 0) {
            buffer.append(X36_ARRAY[(int)(shortUrlSeed % 36)]);
            shortUrlSeed = shortUrlSeed / 36;
        }
        String shortUrl = buffer.reverse().toString();

        jedis.hset("short_url_access_count", shortUrl, "0");
        jedis.hset("url_mapping", shortUrl, url);

        return shortUrl;
    }

    /**
     * 给短连接地址进行访问次数的增长
     * @param shortUrl
     */
    public void incrementShortUrlAccessCount(String shortUrl) {
        jedis.hincrBy("short_url_access_count", shortUrl, 1);
    }

    /**
     * 获取短连接地址的访问次数
     * @param shortUrl
     */
    public long getShortUrlAccessCount(String shortUrl) {
        return Long.valueOf(jedis.hget("short_url_access_count", shortUrl));
    }

    public static void main(String[] args) throws Exception {
        ShortUrlDemo demo = new ShortUrlDemo();

        String shortUrl = demo.getShortUrl("http://redis.com/index.html");
        System.out.println("页面上展示的短链接地址为：" + shortUrl);

        for(int i = 0; i < 152; i++) {
            demo.incrementShortUrlAccessCount(shortUrl);
        }

        long accessCount = demo.getShortUrlAccessCount(shortUrl);
        System.out.println("短链接被访问的次数为：" + accessCount);
    }

}
