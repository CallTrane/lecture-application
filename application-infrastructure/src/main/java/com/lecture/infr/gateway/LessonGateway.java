package com.lecture.infr.gateway;

import com.lecture.domain.entities.LessonDO;

import java.util.List;
import java.util.Optional;

/**
 * @classname: LessonGateway
 * @description: TODO
 * @date: 2021/11/26 17:54
 * @author: Carl
 */
public interface LessonGateway {
    /**
     * 根据学生id获取所选的课程信息
     *
     * @return
     */
    Optional<List<LessonDO>> getLessonsByIds(List<Integer> ids);
}
