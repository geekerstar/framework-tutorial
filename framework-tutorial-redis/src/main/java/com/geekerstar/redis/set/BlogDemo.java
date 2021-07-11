package com.geekerstar.redis.set;

import redis.clients.jedis.Jedis;

import java.util.*;

/**
 * 博客网站案例
 */
public class BlogDemo {

    private Jedis jedis = new Jedis("127.0.0.1");

    /**
     * 获取博客id
     * @return
     */
    public long getBlogId() {
        return jedis.incr("blog_id_counter");
    }

    /**
     * 发表一篇博客
     */
    public boolean publishBlog(long id, Map<String, String> blog, String[] tags) {
        if(jedis.hexists("article::" + id, "title")) {
            return false;
        }
        blog.put("content_length", String.valueOf(blog.get("content").length()));

        jedis.hmset("article::" + id, blog);
        jedis.lpush("blog_list", String.valueOf(id));
        jedis.sadd("article::" + id + "::tags", tags);

        return true;
    }

    /**
     * 查看一篇博客
     * @param id
     * @return
     */
    public Map<String, String> findBlogById(long id) {
        Map<String, String> blog = jedis.hgetAll("article::" + id);

        Set<String> tags = jedis.smembers("article::" + id + "::tags");
        blog.put("tags", tags.toString());

        incrementBlogViewCount(id);

        return blog;
    }

    /**
     * 更新一篇博客
     */
    public void updateBlog(long id, Map<String, String> updatedBlog) {
        String updatedContent = updatedBlog.get("content");
        if(updatedContent != null && !"".equals(updatedContent)) {
            updatedBlog.put("content_length", String.valueOf(updatedContent.length()));
        }

        jedis.hmset("article::" + id, updatedBlog);
    }

    /**
     * 对博客进行点赞
     * @param id
     */
    public void incrementBlogLikeCount(long id) {
        jedis.hincrBy("article::" + id, "like_count", 1);
    }

    /**
     * 增加博客浏览次数
     * @param id
     */
    public void incrementBlogViewCount(long id) {
        jedis.hincrBy("article::" + id, "view_count", 1);
    }

    /**
     * 分页查询博客
     * @param pageNo
     * @param pageSize
     * @return
     */
    public List<String> findBlogByPage(int pageNo, int pageSize) {
        int startIndex = (pageNo - 1) * pageSize;
        int endIndex = pageNo * pageSize - 1;
        return jedis.lrange("blog_list", startIndex, endIndex);
    }

    public static void main(String[] args) {
        BlogDemo demo = new BlogDemo();

        // 发表一篇博客
        long id = demo.getBlogId();

        Map<String, String> blog = new HashMap<String, String>();
        blog.put("id", String.valueOf(id));
        blog.put("title", "我喜欢学习Redis");
        blog.put("content", "学习Redis是一件特别快乐的事情");
        blog.put("author", "石杉");
        blog.put("time", "2020-01-01 10:00:00");

        demo.publishBlog(id, blog, new String[]{"中间件", "Redis", "缓存"});

        // 更新一篇博客
        Map<String, String> updatedBlog = new HashMap<String, String>();
        updatedBlog.put("title", "我特别的喜欢学习Redis");
        updatedBlog.put("content", "我平时喜欢到官方网站上去学习Redis");

        demo.updateBlog(id, updatedBlog);

        // 构造20篇博客数据
        for(int i = 0; i < 20; i++) {
            id = demo.getBlogId();

            blog = new HashMap<String, String>();
            blog.put("id", String.valueOf(id));
            blog.put("title", "第" + (i + 1) + "篇博客");
            blog.put("content", "学习第" + (i + 1) + "篇博客，是一件很有意思的事情");
            blog.put("author", "石杉");
            blog.put("time", "2020-01-01 10:00:00");

            demo.publishBlog(id, blog, new String[]{"中间件", "Redis", "缓存"});
        }

        // 有人分页浏览所有的博客，先浏览第一页
        int pageNo = 1;
        int pageSize = 10;

        List<String> blogPage = demo.findBlogByPage(pageNo, pageSize);
        System.out.println("展示第一页的博客......");
        for(String blogId : blogPage) {
            blog = demo.findBlogById(Long.valueOf(blogId));
            System.out.println(blog);
        }

        pageNo = 2;

        blogPage = demo.findBlogByPage(pageNo, pageSize);
        System.out.println("展示第二页的博客......");
        for(String blogId : blogPage) {
            blog = demo.findBlogById(Long.valueOf(blogId));
            System.out.println(blog);
        }

        // 有别人点击进去查看你的博客的详细内容，并且进行点赞
        Random random = new Random();
        int blogIndex = random.nextInt(blogPage.size());
        String blogId = blogPage.get(blogIndex);

        Map<String, String> blogResult = demo.findBlogById(Long.valueOf(blogId));
        System.out.println("查看博客的详细内容：" + blogResult);
        demo.incrementBlogLikeCount(Long.valueOf(blogId));

        // 你自己去查看自己的博客，看看浏览次数和点赞次数
        blogResult = demo.findBlogById(Long.valueOf(blogId));
        System.out.println("自己查看博客的详细内容：" + blogResult);
    }

}
