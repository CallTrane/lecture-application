package com.lecture.infr.gateway.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lecture.domain.entities.CollegeMajorDO;
import com.lecture.infr.gateway.RedisGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @className: RedisGatewayImpl
 * @description: TODO
 * @author: carl
 * @date: 2021/11/16 0:28
 */
@Slf4j
@Service
public class RedisGatewayImpl implements RedisGateway {

    @Autowired
    RedisTemplate redisTemplate;

    private static ObjectMapper mapper = new ObjectMapper();

    /**
     * 删除前缀的所有key
     *
     * @param prefixKey
     */
    @Override
    public void removeKeyByPrefix(String prefixKey) {
        redisTemplate.delete(redisTemplate.keys(prefixKey + "*"));
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     * @return boolean
     */
    @Override
    public <V> boolean set(String key, V value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 写入缓存设置时效时间
     * @param key
     * @param value
     * @return boolean
     */
    @Override
    public <V> boolean set(String key, V value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public <V> boolean setList(String key, List<V> list) {
        try {
            redisTemplate.opsForValue().set(key, mapper.writeValueAsString(list));
        } catch (Exception e) {
            log.info("写入Redis数据失败 key:{} value:{}", key, list);
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 批量删除对应的value
     * @param keys
     */
    @Override
    public void remove(String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     * @param pattern
     */
    @Override
    public void removePattern(String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     * @param key
     */
    @Override
    public void remove(String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     * @param key
     * @return
     */
    @Override
    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     * @param key
     * @return
     */
    @Override
    public <V> V get(String key) {
        ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
        return (V) operations.get(key);
    }

    @Override
    public <V> List<V> getList(String key, Class<V> clazz) {
        List<V> result = null;
        JavaType javaType = mapper.getTypeFactory().constructParametricType(List.class, clazz);
        try {
            // result = mapper.readValue(key, new TypeReference<List<V>>() {});
            result = get(key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    /**
     * 哈希 添加
     * @param key
     * @param hashKey
     * @param value
     */
    @Override
    public void hmSet(String key, Object hashKey, Object value){
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        hash.put(key,hashKey,value);
    }

    /**
     * 哈希获取数据
     * @param key
     * @param hashKey
     * @return
     */
    @Override
    public Object hmGet(String key, Object hashKey){
        try {
            mapper.readValue(key, new TypeReference<List<CollegeMajorDO>>() {
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        HashOperations<String, Object, Object> hash = redisTemplate.opsForHash();
        return hash.get(key,hashKey);
    }

    /**
     * 列表添加
     * @param k
     * @param v
     */
    @Override
    public void lPush(String k,Object v){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        list.rightPush(k,v);
    }

    /**
     * 列表获取
     * @param k
     * @param l
     * @param l1
     * @return
     */
    @Override
    public List<Object> lRange(String k, long l, long l1){
        ListOperations<String, Object> list = redisTemplate.opsForList();
        return list.range(k,l,l1);
    }

    /**
     * 集合添加
     * @param key
     * @param value
     */
    @Override
    public void setArray(String key,Object value){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        set.add(key,value);
    }

    /**
     * 集合获取
     * @param key
     * @return
     */
    @Override
    public Set<Object> getArray(String key){
        SetOperations<String, Object> set = redisTemplate.opsForSet();
        return set.members(key);
    }

    /**
     * 有序集合添加
     * @param key
     * @param value
     * @param scoure
     */
    @Override
    public void zAdd(String key,Object value,double scoure){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        zset.add(key,value,scoure);
    }

    /**
     * 有序集合获取
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    @Override
    public Set<Object> rangeByScore(String key, double scoure, double scoure1){
        ZSetOperations<String, Object> zset = redisTemplate.opsForZSet();
        return zset.rangeByScore(key, scoure, scoure1);
    }

    /**
     * 自增 +1
     *
     * @param key
     * @return
     */
    @Override
    public Long incr(String key) {
        return redisTemplate.opsForValue().increment(key);
    }

    /**
     * 自减 -1
     *
     * @param key
     * @return
     */
    @Override
    public Long decr(String key) {
        return redisTemplate.opsForValue().decrement(key);
    }
}
