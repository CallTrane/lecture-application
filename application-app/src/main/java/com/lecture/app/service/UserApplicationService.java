package com.lecture.app.service;

import com.lecture.app.assembler.UserAssembler;
import com.lecture.app.dto.UserRegisterDTO;
import com.lecture.component.exception.Assert;
import com.lecture.component.exception.BizException;
import com.lecture.domain.aggregates.student.StudentAggregate;
import com.lecture.domain.aggregates.user.UserAggregate;
import com.lecture.domain.entities.StudentDO;
import com.lecture.domain.entities.UserDO;
import com.lecture.infr.gateway.RedisGateway;
import com.lecture.infr.gateway.StudentGateway;
import com.lecture.infr.gateway.SystemGateway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

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

    @Autowired
    private StudentGateway studentGateway;

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

    /**
     * 用户登录
     *
     * @param account
     * @param password
     * @return
     */
    public UserDO login(String account, String password) {
        return Optional.ofNullable(new UserAggregate().userLogin(account, password).getUserDO())
                .orElseThrow(() -> new BizException("用户登陆信息有误，请重新输入"));
    }

    /**
     * 获取学生课程信息
     *
     * @param studentDO
     * @return
     */
    public StudentAggregate getStudentLessons(StudentDO studentDO) {
        return new StudentAggregate().getStudentLessons(studentDO);
    }

}
