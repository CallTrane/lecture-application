package com.lecture.infr.gateway.rabbitmq;

import com.lecture.infr.config.RabbitMQConfig;
import com.lecture.infr.dao.LessonDAO;
import com.lecture.infr.gateway.RedisGateway;
import com.lecture.infr.gateway.rabbitmq.mo.LessonMO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @className: RabbitMQConsumer
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/4 19:47
 */
@Slf4j
@Service
public class RabbitMQConsumer {

    @Autowired
    RedisGateway redisGateway;

    @Autowired
    LessonDAO lessonDAO;

    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = RabbitMQConfig.SELECT_LESSON_QUEUE)
    @RabbitHandler
    public void handlerSelectLessonMessage(LessonMO lessonMO) {
        Integer lessonId = lessonMO.getLessonId();
        Long stuId = lessonMO.getStuId();
        try {
            lessonDAO.selectLesson(lessonId, stuId);
            lessonDAO.decrLesson(lessonId);
        } catch (Exception e) {
            log.error("学生选课失败 stuId: {} lessonId: {}", stuId, lessonId);
            // 前面发送消息已经预减，失败了要重新加回去
            redisGateway.incr(lessonMO.getLessonKey());
            throw e;
        }
        // 成功消费就清空学生课表缓存
        redisGateway.remove(lessonMO.getStudentKey());
        log.info("学生选课成功 stuId: {} lessonId: {}", stuId, lessonId);
    }

    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = RabbitMQConfig.DROP_LESSON_QUEUE)
    @RabbitHandler
    public void handlerDropLessonMessage(LessonMO lessonMO) {
        Integer lessonId = lessonMO.getLessonId();
        Long stuId = lessonMO.getStuId();
        try {
            lessonDAO.dropLesson(lessonId, stuId);
            lessonDAO.incrLesson(lessonId);
        } catch (Exception e) {
            redisGateway.decr(lessonMO.getLessonKey());
            log.error("学生退课失败 stuId: {} lessonId: {}", stuId, lessonId);
        }
        redisGateway.remove(lessonMO.getStudentKey());
        log.info("学生退课成功 stuId: {} lessonId: {}", stuId, lessonId);
    }
}
