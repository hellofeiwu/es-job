package com.imooc.task;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.dataflow.DataflowJob;

import java.util.ArrayList;
import java.util.List;

public class MyDataflowJob implements DataflowJob {
    @Override
    public List fetchData(ShardingContext shardingContext) {
        System.out.println("--------------@@@@@@@@@@ 抓取数据集合...--------------");
        List<String> list = new ArrayList<>();
        list.add("info1");
        list.add("info2");
        return list;
    }

    @Override
    public void processData(ShardingContext shardingContext, List list) {
        System.out.println("--------------@@@@@@@@@ 处理数据集合...--------------");
        System.out.println(list.toString());
    }
}
