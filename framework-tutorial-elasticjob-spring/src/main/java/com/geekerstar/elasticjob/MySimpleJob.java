package com.geekerstar.elasticjob;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        log.info("我是分片项："+shardingContext.getShardingItem());
    }
}
