package com.lecture.domain.aggregates.student;

import com.lecture.domain.entities.LessonDO;
import com.lecture.domain.entities.TeacherDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @classname: UserRepository
 * @description: TODO
 * @date: 2021/11/26 17:40
 * @author: Carl
 */
@Repository
public interface StudentRepository {

    /**
     * 查询学生所有课程信息
     */
    List<LessonDO> getLessons(Long studentId);

    /**
     * 获取教师基本信息
     */
    List<TeacherDO> getTeachers(List<Long> teacherIds);
}
