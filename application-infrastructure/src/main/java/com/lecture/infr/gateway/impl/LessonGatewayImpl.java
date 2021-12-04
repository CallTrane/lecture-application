package com.lecture.infr.gateway.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.lecture.component.utils.DataUtils;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.dao.LessonDAO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.rabbitmq.mo.LessonMO;
import com.lecture.infr.query.LessonQuery;
import com.lecture.infr.gateway.rabbitmq.RabbitMQSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @className: LessonGatewayImpl
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/2 14:26
 */
@Service
public class LessonGatewayImpl implements LessonGateway {

    @Autowired
    private LessonDAO lessonDAO;

    @Autowired
    RabbitMQSender rabbitMQSender;

    @Override
    public List<LessonDO> getAllLesson() {
        return lessonDAO.selectList(null);
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
    public void selectLesson(LessonMO lessonMO) {
        rabbitMQSender.sendSelectLessonMessage(lessonMO);
    }

    @Override
    public void dropLesson(LessonMO lessonMO) {
        rabbitMQSender.sendDropLessonMessage(lessonMO);
    }

    @Override
    public void closeLesson() {
        lessonDAO.closeLesson();
    }
}
