package com.lecture.app.controller;

import com.lecture.app.service.UserApplicationService;
import com.lecture.domain.aggregates.student.StudentAggregate;
import com.lecture.domain.entities.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @className: StudentController
 * @description: TODO
 * @author: carl
 * @date: 2021/11/15 16:04
 */
@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    UserApplicationService userApplicationService;

    @GetMapping("list_lesson")
    public StudentAggregate getLessons(@Validated StudentDO studentDO) {
        return userApplicationService.getStudentLessons(studentDO);
    }
}
