package com.lecture.app.controller;

import com.lecture.infr.query.LessonQuery;
import com.lecture.app.service.LessonApplicationService;
import com.lecture.domain.entities.LessonDO;
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
    public List<LessonDO> getLessonsById(@RequestBody LessonQuery lessonQueryDTO) {
        return lessonApplicationService.queryLessons(lessonQueryDTO);
    }

}
