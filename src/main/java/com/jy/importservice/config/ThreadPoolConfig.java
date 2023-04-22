package com.jy.importservice.config;

import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @类名 ThreadPoolConfig
 * @描述 TODO
 * @作者 白条君
 * @创建日期 2023/4/19 16:03
 * @版本 1.0
 */
@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        return new ThreadPoolExecutor(
                10,
                40,
                30,
                TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1000, true),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()
        );
    }
}
