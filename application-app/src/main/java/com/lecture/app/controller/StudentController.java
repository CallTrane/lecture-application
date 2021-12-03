package com.lecture.app.controller;

import com.lecture.app.service.LessonApplicationService;
import com.lecture.app.service.UserApplicationService;
import com.lecture.domain.aggregates.student.StudentAggregate;
import com.lecture.domain.entities.LessonDO;
import com.lecture.domain.entities.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    LessonApplicationService lessonApplicationService;

    @GetMapping("/get_lessons")
    public List<LessonDO> getByStuId(@RequestParam Long stuId) {
        return lessonApplicationService.getLessonsByStuId(stuId);
    }
}
