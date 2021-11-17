package com.lecture.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lecture.eneities.UserDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @className: UserDAO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:45
 */
@Mapper
public interface UserDAO extends BaseMapper<UserDO> {
}
