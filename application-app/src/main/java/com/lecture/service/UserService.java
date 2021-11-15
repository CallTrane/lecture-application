package com.lecture.service;

import com.lecture.gateway.RedisGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * @className: UserService
 * @description: TODO
 * @author: carl
 * @date: 2021/11/16 0:14
 */
@Service
public class UserService {

    @Autowired
    private RedisGateway redisGateway;

    public Object getByToken(String token, HttpServletResponse response) {
        return null;
    }
}
