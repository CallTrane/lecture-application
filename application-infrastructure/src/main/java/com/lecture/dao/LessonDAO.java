package com.lecture.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lecture.eneities.LessonDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @className: LessonDAO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:12
 */
@Mapper
public interface LessonDAO extends BaseMapper<LessonDO> {
}
