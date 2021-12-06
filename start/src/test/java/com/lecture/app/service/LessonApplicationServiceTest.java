package com.lecture.app.service;

import com.lecture.StartApplicationTest;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.query.LessonQuery;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * @className: LessonApplicationServiceTest
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/2 14:43
 */
public class LessonApplicationServiceTest extends StartApplicationTest {
    @Autowired
    LessonApplicationService applicationService;
    @Autowired
    LessonGateway lessonGateway;

    @Test
    public void baseTest() {
        LessonQuery lessonQuery = new LessonQuery();
        //lessonQuery.setCampusId(1);
        //lessonQuery.setCollegeId();
        //lessonQuery.setMajorId();
        //lessonQuery.setRequired();
        //applicationService.queryLessons(lessonQuery).forEach(System.out::println);
    }

    @Test
    public void test() {
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                long start = System.currentTimeMillis();
                baseTest();
                System.out.println(System.currentTimeMillis() - start);
            },String.valueOf(i)).start();
        }
    }

    @Test
    public void testGetLesson() {
        System.out.println(lessonGateway.getLessonById(150001).toString());
    }

    @Test
    public void testLesson() {
        applicationService.dropLesson(15, 2L);
    }
}
