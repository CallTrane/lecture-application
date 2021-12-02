package com.lecture.infr.gateway;

import com.lecture.domain.entities.LessonDO;

import java.util.List;
import java.util.Map;

/**
 * @className: LessonGateway
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/2 14:24
 */
public interface LessonGateway {

    Map<Integer, List<LessonDO>> getAllLesson();

    /**
     * 根据专业id获取课程
     *
     * @param majorId
     * @return
     */
    List<LessonDO> getLessonsByMajorId(Integer majorId);

    List<LessonDO> getLessonsByCollegeId(Integer collegeId);
}
