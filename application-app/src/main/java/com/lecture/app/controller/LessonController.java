package com.lecture.app.controller;

import com.lecture.app.vo.LessonVO;
import com.lecture.domain.aggregates.lesson.LessonAggregate;
import com.lecture.domain.entities.StudentDO;
import com.lecture.infr.query.LessonQuery;
import com.lecture.app.service.LessonApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * @className: LessonController
 * @description: TODO
 * @author: carl
 * @date: 2021/11/15 16:11
 */
@RestController
@RequestMapping("/lesson")
public class LessonController {

    @Autowired
    LessonApplicationService lessonApplicationService;

    /**
     * 查询课程
     *
     * @param lessonQueryDTO
     * @return
     */
    @PostMapping("/list")
    @ResponseBody
    public LessonVO getLessons(@RequestBody LessonQuery lessonQueryDTO) {
        return lessonApplicationService.queryLessons(lessonQueryDTO);
    }

    @GetMapping("/count")
    @ResponseBody
    public Integer getLessonCount() {
        return LessonAggregate.allLessonsCount;
    }

    /**
     * 获取当前课程班学生名单
     * @param lessonId
     * @return
     */
    @GetMapping("/get_student")
    @ResponseBody
    public List<StudentDO> getStudentByLessonId(Long lessonId) {
        return lessonApplicationService.getStudentByLessonId(lessonId);
    }
}
