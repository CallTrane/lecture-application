package com.lecture.infr.repository;

import com.google.inject.internal.util.Lists;
import com.lecture.domain.aggregates.student.StudentRepository;
import com.lecture.domain.entities.LessonDO;
import com.lecture.domain.entities.TeacherDO;
import com.lecture.infr.gateway.LessonGateway;
import com.lecture.infr.gateway.StudentGateway;
import com.lecture.infr.gateway.TeacherGateway;
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
    TeacherGateway teacherGateway;

    @Autowired
    LessonGateway lessonGateway;

    public List<TeacherDO> getTeachers(List<Long> teacherIds) {
        return teacherGateway.getTeachersByIds(teacherIds).get();
    }

}
