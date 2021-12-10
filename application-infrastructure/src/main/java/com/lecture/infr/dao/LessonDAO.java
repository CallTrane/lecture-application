package com.lecture.infr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lecture.domain.entities.LessonDO;
import com.lecture.domain.entities.StudentDO;
import com.lecture.infr.query.LessonQuery;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @className: LessonDAO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:12
 */
@Mapper
public interface LessonDAO extends BaseMapper<LessonDO> {
    List<LessonDO> getLessonsByCondition(@Param("query") LessonQuery query);

    List<Integer> getLessonIdByStudentId(Long studentId);

    void selectLesson(Integer lessonId, Long stuId);

    void dropLesson(Integer lessonId, Long stuId);

    void closeLesson();

    void decrLesson(Integer lessonId);

    void incrLesson(Integer lessonId);

    void dropLessonByClose(@Param("list") List<Integer> lessonIds);

    void closeLessons(@Param("list") List<Integer> dropIds);

    Integer getLessonCount();

    void closeLessonByTeacher(Long teacherId, Integer lessonId);

    List<StudentDO> getStudentByLessonId(Long lessonId);

    void closeStudentLessonByTeacher(Integer lessonId);
}
