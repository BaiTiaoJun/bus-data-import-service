package com.jy.importservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.util.Properties;

@Configuration
public class ScheduleConfig {

    //调度工厂，创建调度器对象scheduler
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();

        //配置数据源，任务调度器需要访问数据库中的表
        factoryBean.setDataSource(dataSource);

        Properties properties = new Properties();

        //设置quartz参数
        //设置实例名称
        properties.put("org.quartz.scheduler.instanceName", "WitlinkedScheduler");
        //设置实例id自动生成,每一个任务实例的id不同
        properties.put("org.quartz.scheduler.instanceId", "AUTO");

        //线程池配置
        //接池实现类
        properties.put("org.quartz.threadPool.class", "org.quartz.simpl.SimpleThreadPool");
        //线程数，一般为1-100，根据系统资源配置
        properties.put("org.quartz.threadPool.threadCount", "60");
        //线程优先级(1-10之间的整数)
        properties.put("org.quartz.threadPool.threadPriority", "5");

        //JobStore配置，采用数据库持久化存储
        properties.put("org.quartz.jobStore.class", "org.quartz.impl.jdbcjobstore.JobStoreTX");

        //集群配置
        //是否加入集群
        properties.put("org.quartz.jobStore.isClustered", "true");
        //调度实例失效的检查时间间隔ms
        properties.put("org.quartz.jobStore.clusterCheckinInterval", "15000");
        properties.put("org.quartz.jobStore.maxMisfiresToHandleAtATime", "1");

        properties.put("org.quartz.jobStore.misfireThreshold", "12000");
        //操作的表前缀
        properties.put("org.quartz.jobStore.tablePrefix", "QRTZ_");
        //指定sql, 操作QRTZ_LOCK表,用来锁定
        properties.put("org.quartz.jobStore.selectWithLockSQL", "select * from {0}LOCKS updlock where lock_name = ?");

        factoryBean.setQuartzProperties(properties);

        //设置scheduler对象名称
        factoryBean.setSchedulerName("scheduler");

        //延时启动
//        factoryBean.setStartupDelay(3);

        factoryBean.setApplicationContextSchedulerContextKey("applicationContextKey");
        factoryBean.setOverwriteExistingJobs(true);

        //设置自动启动，默认为true
        factoryBean.setAutoStartup(true);
        return factoryBean;
    }
}