package com.lecture.domain.aggregates.student;

import com.lecture.component.utils.BeanUtils;
import com.lecture.domain.entities.LessonDO;
import com.lecture.domain.entities.StudentDO;
import com.lecture.domain.entities.TeacherDO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @classname: StudentAggregate
 * @description: TODO
 * @date: 2021/11/26 17:33
 * @author: Carl
 */
@Data
@NoArgsConstructor
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

    private Map<Long, TeacherDO> teacherDOMap;


}
