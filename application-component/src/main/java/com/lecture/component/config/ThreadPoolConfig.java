package com.lecture.component.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @classname: ThreadPoolConfig
 * @description: TODO
 * @date: 2021/11/26 18:11
 * @author: Carl
 */
@EnableAsync
@Configuration
public class ThreadPoolConfig {

    @Bean("globalExecutor")
    public Executor globalExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(8);
        executor.setMaxPoolSize(20);
        executor.setKeepAliveSeconds(3);
        executor.setQueueCapacity(1000);
        executor.setThreadNamePrefix("Global-ThreadExecutor-");
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        return executor;
    }
}
