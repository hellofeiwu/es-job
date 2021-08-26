package com.imooc.config;

import com.dangdang.ddframe.job.api.dataflow.DataflowJob;
import com.dangdang.ddframe.job.config.JobCoreConfiguration;
import com.dangdang.ddframe.job.config.dataflow.DataflowJobConfiguration;
import com.dangdang.ddframe.job.event.JobEventConfiguration;
import com.dangdang.ddframe.job.exception.JobConfigurationException;
import com.dangdang.ddframe.job.lite.api.JobScheduler;
import com.dangdang.ddframe.job.lite.config.LiteJobConfiguration;
import com.dangdang.ddframe.job.lite.spring.api.SpringJobScheduler;
import com.dangdang.ddframe.job.reg.zookeeper.ZookeeperRegistryCenter;
import com.imooc.task.MyDataflowJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataflowJobConfig {
    @Autowired
    private ZookeeperRegistryCenter registryCenter;

    @Autowired
    private JobEventConfiguration jobEventConfiguration;

    @Bean
    public DataflowJob dataflowJob() {
        return new MyDataflowJob();
    }

    @Bean(initMethod = "init")
    public JobScheduler dataJobScheduler(
            final DataflowJob dataflowJob,
            @Value("${dataflowJob.cron}") final String cron,
            @Value("${dataflowJob.shardingTotalCount}") final int shardingTotalCount,
            @Value("${dataflowJob.shardingItemParameters}") final String shardingItemParameters

    ) {
        return new SpringJobScheduler(
                dataflowJob,
                registryCenter,
                getLiteJobConfiguration(
                        dataflowJob.getClass(),
                        cron,
                        shardingTotalCount,
                        shardingItemParameters
                ),
                jobEventConfiguration
        );
    }

    private LiteJobConfiguration getLiteJobConfiguration(
            final Class<? extends DataflowJob> jobClass,
            final String cron,
            final int shardingTotalCount,
            final String shardingItemParameters
    ) {
        JobCoreConfiguration jobCoreConfiguration = JobCoreConfiguration
                .newBuilder(jobClass.getName(), cron, shardingTotalCount)
                .shardingItemParameters(shardingItemParameters)
                .build();

        DataflowJobConfiguration dataflowJobConfiguration = new DataflowJobConfiguration(
                jobCoreConfiguration,
                jobClass.getCanonicalName(),
                false // data streaming
        );

        LiteJobConfiguration liteJobConfiguration = LiteJobConfiguration
                .newBuilder(dataflowJobConfiguration)
                .overwrite(false)
                .build();

        return liteJobConfiguration;
    }
}
