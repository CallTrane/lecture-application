package com.lecture.app.service;

import com.lecture.StartApplicationTest;
import com.lecture.app.dto.LessonQueryDTO;
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

    @Test
    public void baseTest() {
        LessonQueryDTO major = new LessonQueryDTO("", 1011);
        System.out.println(applicationService.getLessonsById(major).toString());
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
}
