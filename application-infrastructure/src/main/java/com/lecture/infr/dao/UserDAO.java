package com.lecture.infr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lecture.domain.entities.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @className: UserDAO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:45
 */
@Mapper
public interface UserDAO extends BaseMapper<UserDO> {

    UserDO userLogin(String account, String password);
}
