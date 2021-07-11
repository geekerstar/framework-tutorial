package com.geekerstar.redis.set;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 微博案例
 */
public class MicroBlogDemo {

    private Jedis jedis = new Jedis("127.0.0.1");

    /**
     * 关注别人
     * @param userId
     * @param followUserId
     */
    public void follow(long userId, long followUserId) {
        jedis.sadd("user::" + followUserId + "::followers", String.valueOf(userId));
        jedis.sadd("user::" + userId + "::follow_users", String.valueOf(followUserId));
    }

    /**
     * 取消关注别人
     * @param userId
     * @param followUserId
     */
    public void unfollow(long userId, long followUserId) {
        jedis.srem("user::" + followUserId + "::followers", String.valueOf(userId));
        jedis.srem("user::" + userId + "::follow_users", String.valueOf(followUserId));
    }

    /**
     * 查看有哪些人关注了自己
     * @param userId
     * @return
     */
    public Set<String> getFollowers(long userId) {
        return jedis.smembers("user::" + userId + "::followers");
    }

    /**
     * 查看关注了自己的人数
     * @param userId
     * @return
     */
    public long getFollowersCount(long userId) {
        return jedis.scard("user::" + userId + "::followers");
    }

    /**
     * 查看自己关注了哪些人
     * @param userId
     * @return
     */
    public Set<String> getFollowUsers(long userId) {
        return jedis.smembers("user::" + userId + "::follow_users");
    }

    /**
     * 查看自己关注的人数
     * @param userId
     * @return
     */
    public long getFollowUsersCount(long userId) {
        return jedis.scard("user::" + userId + "::follow_users");
    }

    /**
     * 获取用户跟其他用户之间共同关注的人有哪些
     * @param userId
     * @param otherUserId
     * @return
     */
    public Set<String> getSameFollowUsers(long userId, long otherUserId) {
        return jedis.sinter("user::" + userId + "::follow_users",
                "user::" + otherUserId + "::follow_users");
    }

    /**
     * 获取给我推荐的可关注人
     * 我关注的某个好友关注的一些人，我没关注那些人，此时推荐那些人给我
     * @param userId
     * @return
     */
    public Set<String> getRecommendFollowUsers(long userId, long otherUserId) {
        return jedis.sdiff("user::" + otherUserId + "::follow_users",
                "user::" + userId + "::follow_users");
    }

    public static void main(String[] args) throws Exception {
        MicroBlogDemo demo = new MicroBlogDemo();

        // 定义用户id
        long userId = 31;
        long friendId = 32;
        long superstarId = 33;
        long classmateId = 34;
        long motherId = 35;

        // 定义关注的关系链
        demo.follow(userId, friendId);
        demo.follow(userId, motherId);
        demo.follow(userId, superstarId);
        demo.follow(friendId, superstarId);
        demo.follow(friendId, classmateId);

        // 明星看看自己被哪些人关注了
        Set<String> superstarFollowers = demo.getFollowers(superstarId);
        long superstarFollowersCount = demo.getFollowersCount(superstarId);
        System.out.println("明星被哪些人关注了：" + superstarFollowers + "，关注自己的人数为：" + superstarFollowersCount);

        // 朋友看看自己被哪些人关注了，自己关注了哪些人
        Set<String> friendFollowers = demo.getFollowers(friendId);
        long friendFollowersCount = demo.getFollowersCount(friendId);

        Set<String> friendFollowUsers = demo.getFollowUsers(friendId);
        long friendFollowUsersCount = demo.getFollowUsersCount(friendId);

        System.out.println("朋友被哪些人关注了：" + friendFollowers + "，被多少人关注了：" + friendFollowersCount
                + "，朋友关注了哪些人：" + friendFollowUsers + "，关注了多少人：" + friendFollowUsersCount);

        // 查看我自己关注了哪些
        Set<String> myFollowUsers = demo.getFollowUsers(userId);
        long myFollowUsersCount = demo.getFollowUsersCount(userId);
        System.out.println("我关注了哪些人：" + myFollowUsers + ", 我关注的人数：" + myFollowUsersCount);

        // 获取我和朋友共同关注的好友
        Set<String> sameFollowUsers = demo.getSameFollowUsers(userId, friendId);
        System.out.println("我和朋友共同关注的人有哪些：" + sameFollowUsers);

        // 获取推荐给我的可以关注的人，就是我关注的人关注的其他人
        Set<String> recommendFollowUsers = demo.getRecommendFollowUsers(userId, friendId);
        System.out.println("推荐给我的关注的人有哪些：" + recommendFollowUsers);
    }

}
