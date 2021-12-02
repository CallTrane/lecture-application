package com.lecture.app.controller;

import com.lecture.app.dto.UserRegisterDTO;
import com.lecture.component.enums.ActionEnum;
import com.lecture.app.service.UserApplicationService;
import com.lecture.domain.entities.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @className: SystemController
 * @description: TODO
 * @author: Carl
 * @date: 2021/11/20 17:19
 */
@RestController
@RequestMapping("/system")
public class SystemController {

    @Autowired
    UserApplicationService userApplicationService;

    /**
     * 新用户注册
     *
     * @param userRegisterDTO
     * @return
     */
    @PostMapping("/register")
    @ResponseBody
    public ActionEnum userRegister(@RequestBody @Validated UserRegisterDTO userRegisterDTO) {
        userApplicationService.register(userRegisterDTO);
        return ActionEnum.SUCCESS;
    }

    @PostMapping("/login")
    public UserDO userLogin(@Validated String account, @Validated String password) {
        return userApplicationService.login(account, password);
    }
}
