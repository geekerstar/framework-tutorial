package com.geekerstar.redis.sortedset;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.Set;

/**
 * 推荐其他商品案例
 */
public class RecommendProductDemo {

    private Jedis jedis = new Jedis("127.0.0.1");

    /**
     * 继续购买商品
     * @param productId
     * @param otherProductId
     */
    public void continuePurchase(long productId, long otherProductId) {
        jedis.zincrby("continue_purchase_products::" + productId, 1, String.valueOf(otherProductId));
    }

    /**
     * 推荐其他人购买过的其他商品
     * @param productId
     * @return
     */
    public Set<Tuple> getRecommendProducts(long productId) {
        return jedis.zrevrangeWithScores("continue_purchase_products::" + productId, 0, 2);
    }

    public static void main(String[] args) throws Exception {
        RecommendProductDemo demo = new RecommendProductDemo();

        int productId = 1;

        for(int i = 0; i < 20; i++) {
            demo.continuePurchase(productId, i + 2);
        }
        for(int i = 0; i < 3; i++) {
            demo.continuePurchase(productId, i + 2);
        }

        Set<Tuple> recommendProducts = demo.getRecommendProducts(productId);
        System.out.println("推荐其他人购买过的商品：" + recommendProducts);
    }

}
