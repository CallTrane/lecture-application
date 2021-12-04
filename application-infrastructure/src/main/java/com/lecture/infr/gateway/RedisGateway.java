package com.lecture.infr.gateway;

import java.util.List;
import java.util.Set;

/**
 * @className: RedisGateway
 * @description: TODO
 * @author: carl
 * @date: 2021/11/16 0:28
 */
public interface RedisGateway {

    <V> boolean set(String key, V value);

    <V> boolean set(String key, V value, Long expireTime);

    <V> boolean setList(String key, List<V> list);

    void remove(String... keys);

    /**
     * 批量删除key
     *
     * @param pattern
     */
    void removePattern(String pattern);

    /**
     * 删除对应的value
     *
     * @param key
     */
    void remove(String key);

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    <V> V get(String key);

    /**
     * 获取List列表
     *
     * @param key
     * @param clazz
     * @param <V>
     * @return
     */
    <V> List<V> getList(String key, Class<V> clazz);

    /**
     * 哈希 添加
     *
     * @param key
     * @param hashKey
     * @param value
     */
    void hmSet(String key, Object hashKey, Object value);

    /**
     * 哈希获取数据
     *
     * @param key
     * @param hashKey
     * @return
     */
    Object hmGet(String key, Object hashKey);

    /**
     * 列表添加
     *
     * @param k
     * @param v
     */
    void lPush(String k, Object v);

    /**
     * 列表获取
     *
     * @param k
     * @param l
     * @param l1
     * @return
     */
    List<Object> lRange(String k, long l, long l1);

    /**
     * 集合添加
     *
     * @param key
     * @param value
     */
    void setArray(String key, Object value);

    /**
     * 集合获取
     *
     * @param key
     * @return
     */
    Set<Object> getArray(String key);

    /**
     * 有序集合添加
     *
     * @param key
     * @param value
     * @param scoure
     */
    void zAdd(String key, Object value, double scoure);

    /**
     * 有序集合获取
     *
     * @param key
     * @param scoure
     * @param scoure1
     * @return
     */
    Set<Object> rangeByScore(String key, double scoure, double scoure1);

    Long incr(String key);

    Long decr(String key);
}
