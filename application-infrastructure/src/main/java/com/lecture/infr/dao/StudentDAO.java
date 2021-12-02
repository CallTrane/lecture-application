package com.lecture.infr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lecture.domain.entities.StudentDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @className: StudentDAO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:28
 */
@Mapper
public interface StudentDAO extends BaseMapper<StudentDO> {

    /**
     * 根据学生学号，查询该学生所选的所有课程id
     *
     * @param stuId 学生学号
     * @return 课程id列表
     */
    List<Integer> getLessonIdsByStuId(Long stuId);
}
