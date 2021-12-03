package com.lecture.infr.gateway;

import com.lecture.domain.entities.UserDO;

/**
 * @className: UserGateway
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/21 14:18
 */
public interface UserGateway {

    Integer registerUser(UserDO userDO);

    UserDO userLogin(String account, String password);
}
