package com.geekerstar.service;

/**
 * 计算服务
 */
public interface CalculateService {
    /**
     * 从多个整数sum求和
     * @param values
     * @return sum累加值
     */
    Integer sum(Integer... values);
}
