package com.lecture.infr.assembler;

import com.lecture.domain.aggregates.student.StudentAggregate;
import com.lecture.domain.entities.LessonDO;
import com.lecture.domain.entities.StudentDO;

import java.util.List;

/**
 * @classname: StudentAssembler
 * @description: TODO
 * @date: 2021/11/26 17:51
 * @author: Carl
 */
public class StudentAssembler {

    public StudentAggregate getStudentLessons(List<LessonDO> lessonDOs, StudentDO studentDO) {
        StudentAggregate studentAggregate = null;
        return studentAggregate;
    }
}
