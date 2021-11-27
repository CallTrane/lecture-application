package com.lecture.app.service;

import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.gateway.RedisGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @className: CourseService
 * @description: TODO
 * @author: carl
 * @date: 2021/11/16 0:14
 */
@Service
public class LessonApplicationService {
    @Autowired
    private RedisGateway redisGateway;

    /**
     * 学院所教授的课程
     */
    private static ConcurrentHashMap<Integer, List<LessonDO>> collegeLesson;

    /**
     * 专业所教授的课程
     */
    private static ConcurrentHashMap<Integer, List<LessonDO>> majorLesson;


}
