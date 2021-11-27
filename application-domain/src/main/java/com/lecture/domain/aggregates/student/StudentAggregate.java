package com.lecture.domain.aggregates.student;

import com.lecture.component.utils.BeanUtils;
import com.lecture.domain.entities.LessonDO;
import com.lecture.domain.entities.StudentDO;

import java.util.List;

/**
 * @classname: StudentAggregate
 * @description: TODO
 * @date: 2021/11/26 17:33
 * @author: Carl
 */
public class StudentAggregate {

    private StudentRepository studentRepository = BeanUtils.getBean(StudentRepository.class);

    /**
     * 学生个人信息
     */
    private StudentDO studentDO;

    /**
     * 课程信息
     */
    private List<LessonDO> lessonDOs;

    public StudentAggregate getStudentLessons(StudentDO studentDO) {
        this.studentDO = studentDO;
        this.lessonDOs = studentRepository.getLessons(studentDO.getStuId());
        return this;
    }

}
