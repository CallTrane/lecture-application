package com.lecture.component.redis.key;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.context.annotation.Configuration;

/**
 * @className: RedisKeyGeneratorConfig
 * @description: 默认键的生成策略配置
 * @author: carl
 * @date: 2021/11/15 16:13
 */
@Slf4j
@Configuration
public class RedisKeyGeneratorConfig extends CachingConfigurerSupport {

}
