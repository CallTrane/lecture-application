package com.lecture.app.controller;

import com.lecture.app.dto.LessonQueryDTO;
import com.lecture.app.service.LessonApplicationService;
import com.lecture.domain.entities.LessonDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
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
     *
     * @param lessonQueryDTO
     * @return
     */
    @GetMapping("/getById")
    @ResponseBody
    public List<LessonDO> getLessonsById(@RequestBody @Validated LessonQueryDTO lessonQueryDTO) {
        return lessonApplicationService.getLessonsById(lessonQueryDTO);
    }

}
