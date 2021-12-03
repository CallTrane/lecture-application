package com.lecture.infr.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @className: RedisConfig
 * @description: Redis配置
 * @author: carl
 * @date: 2021/11/15 14:50
 */
@Configuration
@EnableCaching
public class RedisConfig<K, V> {

    /**
     * 默认缓存时间
     */
    private static final Duration DEFAULT_TTL = Duration.ofHours(1);

    /**
     * 配置注入，key是缓存名称，value是缓存有效期
     */
    private Map<String, Long> ttlMap;

    @Bean
    public RedisTemplate<K, V> redisTemplate(LettuceConnectionFactory factory) {
        RedisTemplate<K, V> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(factory);
        // key序列化方式采用String
        redisTemplate.setKeySerializer(stringRedisSerializer());
        // value序列化方式采用jackson
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer());
        // hash序列化方式采用String
        redisTemplate.setHashKeySerializer(stringRedisSerializer());
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer());
        redisTemplate.afterPropertiesSet();
        // 开启事务支持
        redisTemplate.setEnableTransactionSupport(true);
        return redisTemplate;
    }

    /**
     * string序列化器
     * @return
     */
    @Bean
    public StringRedisSerializer stringRedisSerializer() {
        return new StringRedisSerializer();
    }

    /**
     * jackson序列化器
     * @return
     */
    @Bean
    public Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer() {
        Jackson2JsonRedisSerializer<Object> jacksonSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        objectMapper.activateDefaultTyping(objectMapper.getPolymorphicTypeValidator(), ObjectMapper.DefaultTyping.NON_FINAL);
        jacksonSerializer.setObjectMapper(objectMapper);
        return jacksonSerializer;
    }

    /**
     * 自定义CacheManager
     * 在业务中使用SpringCache注解，会采用此配置
     * @param redisConnectionFactory
     * @return
     */
    @Bean
    public CacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
        return new RedisCacheManager(
                RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory),
                // 默认策略，未配置的key会使用这个
                this.getRedisCacheConfigurationWithTtl(DEFAULT_TTL),
                // 指定key策略
                this.getRedisCacheConfigurationMap()
        );
    }

    /**
     * 获取Redis配置Map（map中保存的是ttl信息）
     * @return
     */
    private Map<String, RedisCacheConfiguration> getRedisCacheConfigurationMap() {
        Map<String, RedisCacheConfiguration> redisCacheConfigurationMap = new HashMap<>();
        if (ttlMap != null) {
            for(Map.Entry<String, Long> entry : ttlMap.entrySet()){
                String cacheName = entry.getKey();
                Long ttl = entry.getValue();
                redisCacheConfigurationMap.put(cacheName, this.getRedisCacheConfigurationWithTtl(Duration.ofSeconds(ttl)));
            }
        }
        return redisCacheConfigurationMap;
    }

    /**
     * 配置缓存的序列化方式，并为每一个缓存分别设置ttl
     * @param duration
     * @return
     */
    private RedisCacheConfiguration getRedisCacheConfigurationWithTtl(Duration duration){
        return RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(jackson2JsonRedisSerializer()))
                .entryTtl(duration);
    }
}
