package com.lecture.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @className: StudentService
 * @description: TODO
 * @author: carl
 * @date: 2021/11/15 18:39
 */
@Service
public class StudentService {

    @Autowired
    private RedisTemplate redisTemplate;



}
