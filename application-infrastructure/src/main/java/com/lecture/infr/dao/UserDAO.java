package com.lecture.infr.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lecture.domain.eneities.UserDO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @className: UserDAO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:45
 */
@Repository
@Mapper
public interface UserDAO extends BaseMapper<UserDO> {

    /**
     * 注册新用户
     *
     * @param userDO
     * @return 插入成功返回的uid
     */
    Integer register(UserDO userDO);
}
