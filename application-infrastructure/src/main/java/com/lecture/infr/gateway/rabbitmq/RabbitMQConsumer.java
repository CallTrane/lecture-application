package com.lecture.infr.gateway.rabbitmq;

import com.lecture.infr.config.RabbitMQConfig;
import com.lecture.infr.dao.LessonDAO;
import com.lecture.infr.gateway.RedisGateway;
import com.lecture.infr.gateway.rabbitmq.mo.LessonMO;
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
        lessonDAO.selectLesson(lessonMO.getLessonId(), lessonMO.getStuId());
        lessonDAO.decrLesson(lessonMO.getLessonId());
        redisGateway.decr(lessonMO.getLessonKey());
        redisGateway.remove(lessonMO.getStudentKey());
    }

    @Transactional(rollbackFor = Exception.class)
    @RabbitListener(queues = RabbitMQConfig.DROP_LESSON_QUEUE)
    @RabbitHandler
    public void handlerDropLessonMessage(LessonMO lessonMO) {
        lessonDAO.dropLesson(lessonMO.getLessonId(), lessonMO.getStuId());
        lessonDAO.incrLesson(lessonMO.getLessonId());
        redisGateway.incr(lessonMO.getLessonKey());
        redisGateway.remove(lessonMO.getStudentKey());
    }
}
