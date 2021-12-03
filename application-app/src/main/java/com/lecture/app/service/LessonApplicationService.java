package com.lecture.app.service;

import com.lecture.infr.query.LessonQuery;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.RedisGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @className: CourseService
 * @description: TODO
 * @author: carl
 * @date: 2021/11/16 0:14
 */
@Service
public class LessonApplicationService {

    private static final ReentrantLock collegeLessonLock = new ReentrantLock();
    private static final ReentrantLock majorLessonsLock = new ReentrantLock();

    @Autowired
    private RedisGateway redisGateway;

    @Autowired
    LessonGateway lessonGateway;

    /**
     * 学院所教授的课程
     */
    private static ConcurrentHashMap<Integer, List<LessonDO>> collegeLesson = new ConcurrentHashMap<>(10);

    /**
     * 专业所教授的课程
     */
    private static ConcurrentHashMap<Integer, List<LessonDO>> majorLesson = new ConcurrentHashMap<>(10);

    /**
     * 获取专业课程
     *
     * @param majorId
     * @return
     */
    private List<LessonDO> getLessonsByMajorId(Integer majorId) {
        return Optional.ofNullable(majorLesson.get(majorId)).orElseGet(() -> {
            try {
                while (majorLessonsLock.tryLock()) {
                    return Optional.ofNullable(majorLesson.get(majorId)).orElseGet(() -> {
                        List<LessonDO> lessons = lessonGateway.getLessonsByMajorId(majorId);
                        majorLesson.put(majorId, lessons);
                        return lessons;
                    });
                }
            } finally {
                majorLessonsLock.unlock();
            }
            return getLessonsByMajorId(majorId);
        });
    }

    /**
     * 获取专业课程
     *
     * @param collegeId
     * @return
     */
    private List<LessonDO> getLessonsByCollegeId(Integer collegeId) {
        return Optional.ofNullable(collegeLesson.get(collegeId)).orElseGet(() -> {
            try {
                while (collegeLessonLock.tryLock()) {
                    return Optional.ofNullable(collegeLesson.get(collegeId)).orElseGet(() -> {
                        List<LessonDO> lessons = lessonGateway.getLessonsByCollegeId(collegeId);
                        collegeLesson.put(collegeId, lessons);
                        return lessons;
                    });
                }
            } finally {
                collegeLessonLock.unlock();
            }
            return getLessonsByCollegeId(collegeId);
        });
    }

    public List<LessonDO> queryLessons(LessonQuery lessonQuery) {
        return lessonGateway.getLessonsByCondition(lessonQuery);
    }
}
