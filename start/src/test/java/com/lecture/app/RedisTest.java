package com.lecture.app;

import com.lecture.StartApplicationTest;
import com.lecture.app.service.UserApplicationService;
import com.lecture.domain.aggregates.user.UserAggregate;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.RedisGateway;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @className: RedisTets
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/3 17:39
 */
public class RedisTest extends StartApplicationTest {

    @Autowired
    RedisGateway redisGateway;

    @Autowired
    UserApplicationService service;

    @Autowired
    LessonGateway lessonGateway;

    @Test
    public void test() {
        String key = "user:8";
        UserAggregate value = service.login("lizhendong", "lizhendong");
        redisGateway.set(key, value, 16156L);
        // =============================================
        UserAggregate user = redisGateway.get("user:8");
        System.out.println(user.toString());
    }

    @Test
    public void testSetList() {
        String key = "testLesson";
        redisGateway.setList(key, lessonGateway.getLessonsByMajorId(1011));
        redisGateway.getList(key, LessonDO.class).forEach(System.out::println);
    }
}
