package com.lecture.infr.gateway.impl;

import com.lecture.domain.entities.UserDO;
import com.lecture.infr.dao.UserDAO;
import com.lecture.infr.gateway.UserGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @className: UserGatewayImpl
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/11/21 14:19
 */
@Service
public class UserGatewayImpl implements UserGateway {

    @Autowired
    UserDAO userDAO;

    @Override
    public Integer registerUser(UserDO userDO) {
        userDAO.insert(userDO);
        return userDO.getUid();
    }

    @Override
    public UserDO userLogin(String account, String password) {
        return userDAO.userLogin(account, password);
    }
}
