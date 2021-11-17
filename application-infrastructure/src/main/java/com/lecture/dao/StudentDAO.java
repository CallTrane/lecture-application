package com.lecture.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lecture.eneities.StudentDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @className: StudentDAO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:28
 */
@Mapper
public interface StudentDAO extends BaseMapper<StudentDO> {
}
