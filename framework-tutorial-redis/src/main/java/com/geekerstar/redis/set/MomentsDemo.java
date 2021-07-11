package com.geekerstar.redis.set;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 朋友圈点赞案例
 */
public class MomentsDemo {

    private Jedis jedis = new Jedis("127.0.0.1");

    /**
     * 对朋友圈进行点赞
     * @param userId
     * @param momentId
     */
    public void likeMoment(long userId, long momentId) {
        jedis.sadd("moment_like_users::" + momentId, String.valueOf(userId));
    }

    /**
     * 对朋友圈取消点赞
     * @param userId
     * @param momentId
     */
    public void dislikeMoment(long userId, long momentId) {
        jedis.srem("moment_like_users::" + momentId, String.valueOf(userId));
    }

    /**
     * 查看自己是否对某条朋友圈点赞过
     * @param userId
     * @param momentId
     * @return
     */
    public boolean hasLikedMoment(long userId, long momentId) {
        return jedis.sismember("moment_like_users::" + momentId, String.valueOf(userId));
    }

    /**
     * 获取你的一条朋友圈有哪些人点赞了
     * @param momentId
     * @return
     */
    public Set<String> getMomentLikeUsers(long momentId) {
        return jedis.smembers("moment_like_users::" + momentId);
    }

    /**
     * 获取你的一条朋友圈被几个人点赞了
     * @param momentId
     * @return
     */
    public long getMomentLikeUsersCount(long momentId) {
        return jedis.scard("moment_like_users::" + momentId);
    }

    public static void main(String[] args) throws Exception {
        MomentsDemo demo = new MomentsDemo();

        // 你的用户id
        long userId = 11;
        // 你的朋友圈id
        long momentId = 151;
        // 你的朋友1的用户id
        long friendId = 12;
        // 你的朋友2的用户id
        long otherFriendId = 13;

        // 你的朋友1对你的朋友圈进行点赞，再取消点赞
        demo.likeMoment(friendId, momentId);
        demo.dislikeMoment(friendId, momentId);
        boolean hasLikedMoment = demo.hasLikedMoment(friendId, momentId);
        System.out.println("朋友1刷朋友圈，看到是否对你的朋友圈点赞过：" + (hasLikedMoment ? "是" : "否"));

        // 你的朋友2对你的朋友圈进行点赞
        demo.likeMoment(otherFriendId, momentId);
        hasLikedMoment = demo.hasLikedMoment(otherFriendId, momentId);
        System.out.println("朋友2刷朋友圈，看到是否对你的朋友圈点赞过：" + (hasLikedMoment ? "是" : "否"));

        // 你自己刷朋友圈，看自己的朋友圈的点赞情况
        Set<String> momentLikeUsers = demo.getMomentLikeUsers(momentId);
        long momentLikeUsersCount = demo.getMomentLikeUsersCount(momentId);
        System.out.println("你自己刷朋友圈，看到自己发的朋友圈被" + momentLikeUsersCount + "个人点赞了，点赞的用户为：" + momentLikeUsers);
    }

}
