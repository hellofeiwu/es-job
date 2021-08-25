package com.imooc.task;

import com.alibaba.fastjson.JSON;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

public class MySimpleJob implements SimpleJob {
    @Override
    public void execute(ShardingContext shardingContext) {
        System.out.println("---------	开始任务 MySimpleJob	---------");
        System.out.println(JSON.toJSONString(shardingContext));
    }
}
