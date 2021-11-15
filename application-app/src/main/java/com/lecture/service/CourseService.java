package com.lecture.service;

import com.lecture.gateway.RedisGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: CourseService
 * @description: TODO
 * @author: carl
 * @date: 2021/11/16 0:14
 */
@Service
public class CourseService {
    @Autowired
    private RedisGateway redisGateway;
}
