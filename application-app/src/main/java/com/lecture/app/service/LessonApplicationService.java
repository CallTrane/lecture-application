package com.lecture.app.service;

import com.lecture.app.assembler.LessonAssembler;
import com.lecture.component.exception.BizException;
import com.lecture.infr.gateway.SystemGateway;
import com.lecture.infr.gateway.rabbitmq.mo.LessonMO;
import com.lecture.infr.query.LessonQuery;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.RedisGateway;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
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

    @Autowired
    private SystemGateway systemGateway;

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
            redisGateway.set(lessonKey, value, 259200L);
            return value;
        });
        return result.stream().filter(lessonDO ->
                lessonDO.getMajorId().equals(lessonQuery.getMajorId()) & lessonDO.getCampusId().equals(lessonQuery.getCampusId()) &
                StringUtils.contains(lessonDO.getTeacherName(), Optional.ofNullable(lessonQuery.getTeacherName()).orElse("")) &
                        StringUtils.contains(lessonDO.getName(), Optional.ofNullable(lessonQuery.getLessonName()).orElse("")) &
                        (lessonQuery.getWeekday() == null || Objects.equals(lessonQuery.getWeekday(), lessonDO.getWeekday())) &
                        (lessonQuery.getRequired() == null || Objects.equals(lessonQuery.getRequired(), lessonDO.getRequired()))
        ).map(l -> {
            Optional.ofNullable(redisGateway.get(LessonAssembler.generateLessonNumberKey(l.getLId()))).ifPresent(n -> l.setRemainPeople((Integer) n));
            return l;
        }).collect(Collectors.toList()).subList((lessonQuery.getPageIndex()-1) * lessonQuery.getPageIndex(), lessonQuery.getPageIndex() * lessonQuery.getPageSize());
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
            redisGateway.set(key, result, 86400L);
            return result;
        });
    }

    /**
     * 获取教师所教的课程
     *
     * @param tId
     * @return
     */
    public List<LessonDO> getLessonsByTeacherId(Long tId) {
        String key = LessonAssembler.genereteTeacherLessonKey(tId);
        return Optional.ofNullable(redisGateway.getList(key, LessonDO.class)).orElseGet(() -> {
            List<LessonDO> result = lessonGateway.getLessonsByTeacherId(tId);
            redisGateway.set(key, result, 86400L);
            return result;
        });
    }

    /**
     * 清除redis所有key，同时将所有课程信息剩余人数预热到redis缓存中
     */
    public void preheatLessonNumber() {
        systemGateway.preheatLessonNumber(LessonAssembler.generateLessonNumberKey(null));
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
            lessonGateway.selectLesson(new LessonMO(key, lessonId, LessonAssembler.generateStudentLessonKey(stuId), stuId));
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
        lessonGateway.dropLesson(new LessonMO(key, lessonId, LessonAssembler.generateStudentLessonKey(stuId), stuId));
    }

    public void closeLesson() {
        lessonGateway.closeLesson();
    }
}
