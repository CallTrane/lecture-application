package com.lecture.app.service;

import com.lecture.app.assembler.LessonAssembler;
import com.lecture.app.vo.LessonVO;
import com.lecture.component.enums.ActionEnum;
import com.lecture.component.exception.BizException;
import com.lecture.component.utils.DataUtils;
import com.lecture.domain.aggregates.lesson.LessonAggregate;
import com.lecture.domain.entities.StudentDO;
import com.lecture.infr.gateway.SystemGateway;
import com.lecture.infr.gateway.rabbitmq.mo.LessonMO;
import com.lecture.infr.query.LessonQuery;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.RedisGateway;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
    public LessonVO queryLessons(LessonQuery lessonQuery) {
        String lessonKey = LessonAssembler.generateLessonListKey(lessonQuery);
        Integer index = lessonQuery.getPageIndex();
        if (DataUtils.isEmpty(index) || index <= 0) {
            index = 1;
        }
        Integer size = Optional.ofNullable(lessonQuery.getPageSize()).orElse(10);
        Integer filter = Optional.ofNullable(lessonQuery.getFilter()).orElse(0);
        List<LessonDO> origin = Optional.ofNullable(redisGateway.getList(lessonKey, LessonDO.class)).orElseGet(() -> {
            // todo:这里是查全部数据，应该改成根据条件查询 lessonGateway.getLessonsByCondition(lessonQuery) 但key就要生成保证能唯一的
            List<LessonDO> value = lessonGateway.getAllLesson().stream().filter(l -> l.getClosed().equals(0)).collect(Collectors.toList());
            redisGateway.set(lessonKey, value, 259200L);
            return value;
        });
        List<LessonDO> data = origin.parallelStream()
                .map(l -> {
                    Optional.ofNullable(redisGateway.get(LessonAssembler.generateLessonNumberKey(l.getLId()))).ifPresent(n -> l.setRemainPeople((Integer) n));
                    return l;
                }).filter(lessonDO ->
                        StringUtils.contains(lessonDO.getTeacherName(), Optional.ofNullable(lessonQuery.getTeacherName()).orElse("")) &
                                StringUtils.contains(lessonDO.getName(), Optional.ofNullable(lessonQuery.getLessonName()).orElse("")) &
                                (lessonQuery.getWeekday() == null || Objects.equals(lessonQuery.getWeekday(), lessonDO.getWeekday())) &
                                (lessonQuery.getMajorId() == null || Objects.equals(lessonQuery.getMajorId(), lessonDO.getMajorId())) &
                                (lessonQuery.getCampusId() == null || Objects.equals(lessonQuery.getCampusId(), lessonDO.getCampusId())) &
                                (lessonQuery.getRequired() == null || Objects.equals(lessonQuery.getRequired(), lessonDO.getRequired())) &
                                (filter == 1 ? lessonDO.getRemainPeople() > 0 : true)).collect(Collectors.toList());
        return new LessonVO(data.stream().skip((index - 1) * size).limit(size).collect(Collectors.toList()), index, size, data.size());
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
        List<LessonDO> origin = Optional.ofNullable(redisGateway.getList(key, LessonDO.class)).orElseGet(() -> {
            List<LessonDO> result = lessonGateway.getLessonsByTeacherId(tId);
            redisGateway.set(key, result, 86400L);
            return result;
        });
        return origin.stream().map(lessonDO -> {
            lessonDO.setRemainPeople(redisGateway.get(LessonAssembler.generateLessonNumberKey(lessonDO.getLId())));
            return lessonDO;
        }).collect(Collectors.toList());
    }

    /**
     * 清除redis所有key，同时将所有课程信息剩余人数预热到redis缓存中
     */
    public void preheatLessonNumber() {
        systemGateway.preheatLessonNumber("lesson:lesson_remain:");
    }

    /**
     * 学生选课
     *
     * @param lessonId
     * @param stuId
     */
    public void selectLesson(Integer lessonId, Long stuId) {
        String lessonNumberKey = LessonAssembler.generateLessonNumberKey(lessonId);
        LessonDO lessonDO = redisGateway.get(LessonAssembler.generateLessonKey(lessonId));
        // 这里Lesson在缓存的剩余人数是不准的，但没关系因为只要星期几的第几节课这个数据
        Optional.ofNullable((Integer) redisGateway.get(lessonNumberKey)).ifPresentOrElse(number -> {
            if (number <= 0) {
                throw new BizException("选课人数已满 请重新选择");
            }
            List<LessonDO> studentLessons = getLessonsByStuId(stuId);
            if (studentLessons.stream().anyMatch(sl -> sl.getLId().equals(lessonId) |
                    (sl.getWeekday().equals(lessonDO.getWeekday()) && sl.getClasses().equals(lessonDO.getClasses())))) {
                throw new BizException("该学生已选过该课程或跟已选课程时间冲突");
            }
            if (studentLessons.stream().mapToInt(LessonDO::getCredit).sum() + lessonDO.getCredit() > LessonAggregate.MAX_CREDIT) {
                throw new BizException("选课总学分不能大于35");
            }
            try {
                // 提交让消息队列执执行
                lessonGateway.selectLesson(new LessonMO(lessonNumberKey, lessonId,
                        LessonAssembler.generateStudentLessonKey(stuId), stuId,
                        LessonAssembler.genereteTeacherLessonKey(lessonDO.getTId()), lessonDO.getTId()));
            } catch (Exception e) {
                log.error("学生选课失败 stuId: {} lessonId: {}", stuId, lessonId);
                throw e;
            }
            // 成功发送后，让redis实现预减
            redisGateway.decr(lessonNumberKey);
        }, () -> { throw new BizException("找不到当前课程 请联系教务处处理"); });
    }

    /**
     * 学生退课
     *
     * @param lessonId
     * @param stuId
     */
    public void dropLesson(Integer lessonId, Long stuId) {
        String lessonNumberKey = LessonAssembler.generateLessonNumberKey(lessonId);
        LessonDO lessonDO = redisGateway.get(LessonAssembler.generateLessonKey(lessonId));
        if (!redisGateway.exists(lessonNumberKey)) {
            throw new BizException("非法的课程id 请联系教务处处理");
        }
        if (!getLessonsByStuId(stuId).stream().map(LessonDO::getLId).anyMatch(id -> id.equals(lessonId))) {
            throw new BizException("你没有选过这门课程");
        }
        try {
            lessonGateway.dropLesson(new LessonMO(lessonNumberKey, lessonId,
                    LessonAssembler.generateStudentLessonKey(stuId), stuId,
                    LessonAssembler.genereteTeacherLessonKey(lessonDO.getTId()), lessonDO.getTId()));
        } catch (Exception e) {
            log.error("学生退课失败 stuId: {} lessonId: {}", stuId, lessonId);
        }
        // redis加回去
        redisGateway.incr(lessonNumberKey);
    }

    public void closeLesson() {
        lessonGateway.closeLesson();
        preheatLessonNumber();
    }

    public void closeLesson(Long teacherId, Integer lessonId) {
        Optional.ofNullable((LessonDO) redisGateway.get(LessonAssembler.generateLessonKey(lessonId))).ifPresentOrElse(lessonDO -> {
            if (!lessonDO.getTId().equals(teacherId)) {
                throw new BizException("非法的教师工号 请联系教务处处理");
            }
            lessonGateway.closeLesson(teacherId, lessonId);
            preheatLessonNumber();
        }, () -> { throw new BizException("非法的课程id 请联系教务处处理"); });
    }

    public List<StudentDO> getStudentByLessonId(Long lessonId) {
        return lessonGateway.getStudentByLessonId(lessonId);
    }
}
