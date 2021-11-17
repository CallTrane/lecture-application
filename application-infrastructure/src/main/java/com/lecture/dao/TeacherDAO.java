package com.lecture.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lecture.eneities.TeacherDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @className: TeacherDAO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:52
 */
@Mapper
public interface TeacherDAO extends BaseMapper<TeacherDO> {
}
