package com.geekerstar.elasticjob.job;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

import java.time.LocalTime;

public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        LocalTime time = LocalTime.now();
        System.out.println(time+",我是分片项："+shardingContext.getShardingItem()+
                ",总分片项："+shardingContext.getShardingTotalCount()+",taskId"+
                shardingContext.getTaskId());
    }
}
