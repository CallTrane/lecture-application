package com.lecture.app.controller;

import com.lecture.app.service.LessonApplicationService;
import com.lecture.component.enums.ActionEnum;
import com.lecture.domain.entities.LessonDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @className: TeacherController
 * @description: TODO
 * @author: carl
 * @date: 2021/11/15 16:09
 */
@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Autowired
    LessonApplicationService lessonApplicationService;

    @PostMapping("/get_lesson")
    public List<LessonDO> getLessonsByTeacherId(@RequestParam Long teacherId) {
        return lessonApplicationService.getLessonsByTeacherId(teacherId);
    }

    @PostMapping("/close_lesson")
    public ActionEnum closeLessonByTeacher(@Validated Long teacherId, @Validated Integer lessonId) {
        lessonApplicationService.closeLesson(teacherId, lessonId);
        return ActionEnum.SUCCESS;
    }
}
