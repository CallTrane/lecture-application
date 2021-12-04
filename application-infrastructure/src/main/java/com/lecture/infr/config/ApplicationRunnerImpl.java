package com.lecture.infr.config;

import com.lecture.domain.aggregates.lesson.LessonAggregate;
import com.lecture.domain.aggregates.user.UserAggregate;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.SystemGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @className: ApplicationRunnerImpl
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/3 14:05
 */
@Component
public class ApplicationRunnerImpl  implements ApplicationRunner {

    @Autowired
    SystemGateway systemGateway;

    @Autowired
    LessonGateway lessonGateway;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserAggregate.collegeMajorMap = systemGateway.getCollegeMajorMap();
        LessonAggregate.allLessonsCount = lessonGateway.getLessonCount();
        //LessonAggregate.allLessons = lessonGateway.getAllLessons();
        systemGateway.preheatLessonNumber("lesson:id:");
    }
}
