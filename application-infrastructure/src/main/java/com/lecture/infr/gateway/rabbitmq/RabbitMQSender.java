package com.lecture.infr.gateway.rabbitmq;

import com.lecture.infr.config.RabbitMQConfig;
import com.lecture.infr.gateway.rabbitmq.mo.LessonMO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: RabbitMQSender
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/4 19:47
 */
@Service
public class RabbitMQSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendSelectLessonMessage(LessonMO lessonMO) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.LESSON_EXCHANGE, RabbitMQConfig.SELECT_LESSON_KEY, lessonMO);
    }

    public void sendDropLessonMessage(LessonMO lessonMO) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.LESSON_EXCHANGE, RabbitMQConfig.DROP_LESSON_KEY, lessonMO);
    }

}
