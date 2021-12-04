package com.lecture.infr.config;

import net.sf.jsqlparser.statement.drop.Drop;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @className: RabbitMQConfig
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/4 19:21
 */
@Configuration
public class RabbitMQConfig {

    public static final String SELECT_LESSON_QUEUE = "SELECT_LESSON_QUEUE";
    public static final String SELECT_LESSON_KEY = "SELECT_LESSON_KEY";
    public static final String SELECT_LESSON_EXCHANGE = "LESSON_EXCHANGE";

    public static final String DROP_LESSON_QUEUE = "DROP_LESSON_QUEUE";
    public static final String DROP_LESSON_KEY = "DROP_LESSON_KEY";
    public static final String DROP_LESSON_EXCHANGE = "DROP_LESSON_EXCHANGE";



    /**
     * 队列
     *
     * @return
     */
    @Bean
    public Queue selectLessonQueue() {
        // 名字 持久化
        return new Queue(SELECT_LESSON_QUEUE, true);
    }

    @Bean
    public Queue dropLessonQueue() {
        // 名字 持久化
        return new Queue(DROP_LESSON_QUEUE, true);
    }

    /**
     * 直连交换机
     * @return
     */
     @Bean
     public DirectExchange selectLessonExchange() {
     return new DirectExchange(SELECT_LESSON_EXCHANGE, true, false);
     }

    @Bean
    public DirectExchange dropLessonExchange() {
        return new DirectExchange(DROP_LESSON_EXCHANGE, true, false);
    }

     @Bean public Binding selectLessonBinding() {
     return BindingBuilder.bind(selectLessonQueue()).to(selectLessonExchange()).with(SELECT_LESSON_KEY);
     }

     @Bean public Binding dropLessonBinding() {
     return BindingBuilder.bind(dropLessonQueue()).to(dropLessonExchange()).with(DROP_LESSON_KEY);
     }

}
