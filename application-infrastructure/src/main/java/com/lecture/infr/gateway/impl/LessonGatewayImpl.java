package com.lecture.infr.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.lecture.component.utils.DataUtils;
import com.lecture.domain.aggregates.lesson.LessonAggregate;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.dao.LessonDAO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.RedisGateway;
import com.lecture.infr.gateway.rabbitmq.mo.LessonMO;
import com.lecture.infr.query.LessonQuery;
import com.lecture.infr.gateway.rabbitmq.RabbitMQSender;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @className: LessonGatewayImpl
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/2 14:26
 */
@Slf4j
@Service
public class LessonGatewayImpl implements LessonGateway {

    @Autowired
    private LessonDAO lessonDAO;

    @Autowired
    private RedisGateway redisGateway;

    @Autowired
    private RabbitMQSender rabbitMQSender;

    @Override
    public List<LessonDO> getAllLesson() {
        return lessonDAO.selectList(null);
    }

    @Override
    public Integer getLessonCount() {
        return lessonDAO.getLessonCount();
    }

    @Override
    public List<LessonDO> getLessonsByMajorId(Integer majorId) {
        LambdaQueryWrapper<LessonDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LessonDO::getMajorId, majorId).eq(LessonDO::getClosed, 0);
        return lessonDAO.selectList(wrapper);
    }

    @Override
    public List<LessonDO> getLessonsByCollegeId(Integer collegeId) {
        LambdaQueryWrapper<LessonDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LessonDO::getCollegeId, collegeId).eq(LessonDO::getClosed, 0);
        return lessonDAO.selectList(wrapper);
    }

    @Override
    public List<LessonDO> getLessonsByCondition(LessonQuery lessonQuery) {
        return lessonDAO.getLessonsByCondition(lessonQuery);
    }

    @Override
    public List<LessonDO> getLessonsByStuId(Long stuId) {
        List<Integer> lessonIds = lessonDAO.getLessonIdByStudentId(stuId);
        if (DataUtils.isEmpty(lessonIds)) {
            return Collections.emptyList();
        }
        return lessonDAO.selectBatchIds(lessonIds);
    }

    @Override
    public List<LessonDO> getLessonsByTeacherId(Long teacherId) {
        LambdaQueryWrapper<LessonDO> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(LessonDO::getTId, teacherId).eq(LessonDO::getClosed, 0);
        return lessonDAO.selectList(wrapper);
    }

    @Override
    public LessonDO getLessonById(Integer lessonId) {
        return lessonDAO.selectById(lessonId);
    }

    @Override
    public void selectLesson(LessonMO lessonMO) {
        rabbitMQSender.sendSelectLessonMessage(lessonMO);
    }

    @Override
    public void dropLesson(LessonMO lessonMO) {
        rabbitMQSender.sendDropLessonMessage(lessonMO);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void closeLesson() {
        // 获取所有课程人数小于20，且之前未关班的l_id
        List<Integer> dropIds = getAllLesson().stream()
                .filter(lessonDO -> lessonDO.getTotalPeople() - lessonDO.getRemainPeople() < 20 & lessonDO.getClosed().equals(0))
                .map(LessonDO::getLId).collect(Collectors.toList());
        lessonDAO.closeLessons(dropIds);
        LessonAggregate.allLessonsCount = getLessonCount();
        redisGateway.removeKeyByPrefix("lesson:");
        dropIds.parallelStream().forEach(id -> log.warn("系统关闭课程 l_id: {}", id));
    }

    @Override
    public void closeLesson(Long teacherId, Integer lessonId) {
        lessonDAO.closeLessonByTeacher(teacherId, lessonId);
    }
}
