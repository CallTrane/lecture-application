package com.lecture.infr.repository;

import com.google.inject.internal.util.Lists;
import com.lecture.domain.aggregates.student.StudentRepository;
import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.StudentGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @classname: StudentRepositoryImpl
 * @description: TODO
 * @date: 2021/11/26 19:26
 * @author: Carl
 */
@Slf4j
@Service
public class StudentRepositoryImpl implements StudentRepository {

    @Autowired
    StudentGateway studentGateway;

    @Autowired
    LessonGateway lessonGateway;

    @Override
    public List<LessonDO> getLessons(Long studentId) {
        List<Integer> ids = studentGateway.getLessonIds(studentId);
        return lessonGateway.getLessonsByIds(ids).orElseGet(() -> {
            log.info("学生{} 查询不到课程", studentId);
            return Lists.newArrayList();
        });
    }

}
