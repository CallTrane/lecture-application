package com.lecture.app.service;

import com.lecture.app.assembler.LessonAssembler;
import com.lecture.component.exception.BizException;
import com.lecture.infr.query.LessonQuery;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.RedisGateway;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
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
    private LessonGateway lessonGateway;

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
        String lessonKey = LessonAssembler.generateLessonListKey(lessonQuery);
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
        ).map(l -> {
            Optional.ofNullable(redisGateway.get(LessonAssembler.generateLessonNumberKey(l.getLId()))).ifPresent(n -> l.setRemainPeople((Integer) n));
            return l;
        }).collect(Collectors.toList());
    }

    /**
     * 获取学生所选课程
     *
     * @param stuId
     * @return
     */
    public List<LessonDO> getLessonsByStuId(Long stuId) {
        String key = LessonAssembler.generateStudentLessonKey(stuId);
        return Optional.ofNullable(redisGateway.getList(key, LessonDO.class)).orElseGet(() -> {
            List<LessonDO> result = lessonGateway.getLessonsByStuId(stuId);
            redisGateway.set(key, result);
            return result;
        });
    }

    /**
     * 将所有课程信息剩余人数预热到redis缓存中
     */
    public void preheatLessonNumber() {
        lessonGateway.getAllLessons().forEach(lessonDO ->
            redisGateway.set(LessonAssembler.generateLessonNumberKey(lessonDO.getLId()), lessonDO.getRemainPeople())
        );
    }

    /**
     * 学生选课
     *
     * @param lessonId
     * @param stuId
     */
    public void selectLesson(Integer lessonId, Long stuId) {
        String key = LessonAssembler.generateLessonNumberKey(lessonId);
        Optional.ofNullable((Integer)redisGateway.get(key)).ifPresentOrElse(number -> {
            if (number <= 0) {
                throw new BizException("选课人数已满、请重新选择");
            }
            if (getLessonsByStuId(stuId).stream().anyMatch(sl -> sl.getLId().equals(lessonId))){
                throw new BizException("该学生已选过该课程");
            }
            // todo:这个操作用MQ，学生成功选课后应该刷新学生个人课程信息缓存
            lessonGateway.selectLesson(lessonId, stuId);
            redisGateway.decr(key);
            redisGateway.remove(LessonAssembler.generateStudentLessonKey(stuId));
        }, () -> { throw new BizException("找不到当前课程、请联系教务处处理"); });
    }

    /**
     * 学生退课
     *
     * @param lessonId
     * @param stuId
     */
    public void dropLesson(Integer lessonId, Long stuId) {
        String key = LessonAssembler.generateLessonNumberKey(lessonId);
        if (!redisGateway.exists(key)) {
            throw new BizException("非法的课程id");
        }
        // todo:这个操作用MQ，学生成功退课后应该刷新学生个人课程信息缓存
        lessonGateway.dropLesson(lessonId, stuId);
        redisGateway.incr(key);
    }

}
