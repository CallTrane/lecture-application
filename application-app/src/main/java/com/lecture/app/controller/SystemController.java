package com.lecture.app.controller;

import com.lecture.app.dto.UserRegisterDTO;
import com.lecture.app.service.LessonApplicationService;
import com.lecture.component.enums.ActionEnum;
import com.lecture.app.service.UserApplicationService;
import com.lecture.domain.aggregates.user.UserAggregate;
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

    @Autowired
    LessonApplicationService lessonApplicationService;

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
    public UserAggregate userLogin(@Validated String account, @Validated String password) {
        return userApplicationService.login(account, password);
    }

    @GetMapping("/preheat")
    public ActionEnum preheatLesson() {
        lessonApplicationService.preheatLessonNumber();
        return ActionEnum.SUCCESS;
    }

    @GetMapping("/close")
    public ActionEnum closeLesson() {
        lessonApplicationService.closeLesson();
        return ActionEnum.SUCCESS;
    }
}
