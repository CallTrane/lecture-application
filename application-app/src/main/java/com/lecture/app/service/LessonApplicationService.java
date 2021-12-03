package com.lecture.app.service;

import com.lecture.app.assembler.LessonAssembler;
import com.lecture.component.utils.DataUtils;
import com.lecture.infr.query.LessonQuery;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.RedisGateway;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

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

    /**
     * 根据条件查询课程
     *
     * @param lessonQuery
     * @return
     */
    public List<LessonDO> queryLessons(LessonQuery lessonQuery) {
        String lessonKey = LessonAssembler.getRedisKey(lessonQuery);
        List<LessonDO> result = Optional.ofNullable(redisGateway.getList(lessonKey, LessonDO.class)).orElseGet(() -> {
            List<LessonDO> value = lessonGateway.getLessonsByCondition(lessonQuery);
            redisGateway.set(lessonKey, value);
            return value;
        });
        return result.stream().filter(lessonDO ->
                StringUtils.contains(lessonDO.getTeacherName(), Optional.ofNullable(lessonQuery.getTeacherName()).orElse("")) &
                        StringUtils.contains(lessonDO.getName(), Optional.ofNullable(lessonQuery.getLessonName()).orElse("")) &
                        (lessonQuery.getWeekday() == null || Objects.equals(lessonQuery.getWeekday(), lessonDO.getWeekday())) &
                        (lessonQuery.getRequired() == null || Objects.equals(lessonQuery.getRequired(), lessonDO.getRequired()))
        ).collect(Collectors.toList());
    }

    /**
     * 获取学生所选课程
     *
     * @param stuId
     * @return
     */
    public List<LessonDO> getLessonsByStuId(Long stuId) {
        String key = LessonAssembler.getRedisKey(stuId);
        return Optional.ofNullable(redisGateway.getList(key, LessonDO.class)).orElseGet(() -> {
            List<LessonDO> result = lessonGateway.getLessonsByStuId(stuId);
            redisGateway.set(key, result);
            return result;
        });
    }

    /**
     * 学生选课
     *
     * @param lessonId
     * @param stuId
     */
    public void selectLesson(Integer lessonId, Long stuId) {
        lessonGateway.selectLesson(lessonId, stuId);
    }

    /**
     * 学生退课
     *
     * @param lessonId
     * @param stuId
     */
    public void dropLesson(Integer lessonId, Long stuId) {
        lessonGateway.dropLesson(lessonId, stuId);
    }
}
