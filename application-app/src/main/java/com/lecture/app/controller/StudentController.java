package com.lecture.app.controller;

import com.lecture.app.service.LessonApplicationService;
import com.lecture.app.service.UserApplicationService;
import com.lecture.component.enums.ActionEnum;
import com.lecture.domain.aggregates.student.StudentAggregate;
import com.lecture.domain.entities.LessonDO;
import com.lecture.domain.entities.StudentDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    @ResponseBody
    public List<LessonDO> getByStuId(@RequestParam Long stuId) {
        return lessonApplicationService.getLessonsByStuId(stuId);
    }

    /**
     * 选课
     *
     * @param lessonId
     * @param stuId
     * @return
     */
    @PostMapping("/select_lesson")
    public ActionEnum selectLesson(@RequestParam Integer lessonId, @RequestParam Long stuId) {
        lessonApplicationService.selectLesson(lessonId, stuId);
        return ActionEnum.SUCCESS;
    }

    /**
     * 退课
     *
     * @param lessonId
     * @param stuId
     * @return
     */
    @PostMapping("/drop_lesson")
    public ActionEnum dropLesson(@RequestParam Integer lessonId, @RequestParam Long stuId) {
        lessonApplicationService.dropLesson(lessonId, stuId);
        return ActionEnum.SUCCESS;
    }
}
