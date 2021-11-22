package com.lecture.app.service;

import com.lecture.app.assembler.UserAssembler;
import com.lecture.app.dto.UserRegisterDTO;
import com.lecture.app.vo.UserLoginVO;
import com.lecture.component.exception.Assert;
import com.lecture.infr.gateway.RedisGateway;
import com.lecture.infr.gateway.SystemGateway;
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
public class UserApplicationService {

    @Autowired
    private RedisGateway redisGateway;

    @Autowired
    private SystemGateway systemGateway;

    public Object getByToken(String token, HttpServletResponse response) {
        return null;
    }

    /**
     * 注册新用户
     *
     * @param userRegisterDTO
     */
    public void register(UserRegisterDTO userRegisterDTO) {
        // 验证用户类型
        Assert.isTrueOrElseThrow(UserAssembler.validateUser(userRegisterDTO.getType()), "非法用户类型，请重新注册");
        // 聚合根注册
        UserAssembler.toAggregate(userRegisterDTO).registerUser();
    }

    public UserLoginVO login(String account, String password) {
        return null;
    }
}
