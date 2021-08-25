package com.imooc.listener;

import com.dangdang.ddframe.job.executor.ShardingContexts;
import com.dangdang.ddframe.job.lite.api.listener.ElasticJobListener;

import com.alibaba.fastjson.JSON;

/**
 * 第3步：（可选）写一个job的listener，用来做pre job，post job操作
 */
public class SimpleJobListener implements ElasticJobListener {
    @Override
    public void beforeJobExecuted(ShardingContexts shardingContexts) {
        System.out.println("-----------------执行任务之前：" + JSON.toJSONString(shardingContexts));
    }

    @Override
    public void afterJobExecuted(ShardingContexts shardingContexts) {
        System.out.println("-----------------执行任务之后：" + JSON.toJSONString(shardingContexts));
    }
}
