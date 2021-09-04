package com.geekerstar.flink.partitioner;

import org.apache.flink.api.common.functions.Partitioner;

/**
 * @author geekerstar
 * @date 2021/9/4 20:12
 * @description
 */
public class PKPartitioner implements Partitioner<String> {
    @Override
    public int partition(String key, int numPartitions) {
        System.out.println("numPartitions:" + numPartitions);
        if("imooc.com".equals(key)) {
            return 0;
        } else if("a.com".equals(key)) {
            return 1;
        } else {
            return 2;
        }
    }
}
