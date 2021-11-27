package com.lecture.infr.gateway;

import com.lecture.domain.entities.UserDO;

/**
 * @className: UserGateway
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/21 14:18
 */
public interface UserGateway {

    /**
     * 注册新用户
     *
     * @param userDO
     * @return 用户uid
     */
    Integer registerUser(UserDO userDO);

    /**
     * 用户登录
     *
     * @param account
     * @param password
     * @return
     */
    UserDO userLogin(String account, String password);

}
