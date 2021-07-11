package com.geekerstar.redis.set;

import redis.clients.jedis.Jedis;

import java.util.Set;

/**
 * 投票统计案例
 */
public class VoteDemo {

    private Jedis jedis = new Jedis("127.0.0.1");

    /**
     * 投票
     * @param userId
     * @param voteItemId
     */
    public void vote(long userId, long voteItemId) {
        jedis.sadd("vote_item_users::" + voteItemId, String.valueOf(userId));
    }

    /**
     * 检查用户对投票项是否投过票
     * @param userId
     * @param voteItemId
     * @return
     */
    public boolean hasVoted(long userId, long voteItemId) {
        return jedis.sismember("vote_item_users::" + voteItemId, String.valueOf(userId));
    }

    /**
     * 获取一个投票项被哪些人投票了
     * @param voteItemId
     * @return
     */
    public Set<String> getVoteItemUsers(long voteItemId) {
        return jedis.smembers("vote_item_users::" + voteItemId);
    }

    /**
     * 获取一个投票项被多少人投票了
     * @param voteItemId
     * @return
     */
    public long getVoteItemUsersCount(long voteItemId) {
        return jedis.scard("vote_item_users::" + voteItemId);
    }

    public static void main(String[] args) throws Exception {
        VoteDemo demo = new VoteDemo();

        // 定义用户id
        long userId = 1;
        // 定义投票项id
        long voteItemId = 110;

        // 进行投票
        demo.vote(userId, voteItemId);
        // 检查我是否投票过
        boolean hasVoted = demo.hasVoted(userId, voteItemId);
        System.out.println("用户查看自己是否投票过：" +(hasVoted ? "是" : "否"));
        // 归票统计
        Set<String> voteItemUsers = demo.getVoteItemUsers(voteItemId);
        long voteItemUsersCount = demo.getVoteItemUsersCount(voteItemId);
        System.out.println("投票项有哪些人投票：" + voteItemUsers + "，有几个人投票：" + voteItemUsersCount);
    }

}
