package com.lecture.infr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lecture.domain.entities.LessonDO;
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
}
