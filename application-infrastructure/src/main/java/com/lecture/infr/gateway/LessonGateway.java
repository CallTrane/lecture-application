package com.lecture.infr.gateway;

import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.gateway.rabbitmq.mo.LessonMO;
import com.lecture.infr.query.LessonQuery;

import java.util.List;

/**
 * @className: LessonGateway
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/2 14:24
 */
public interface LessonGateway {

    List<LessonDO> getAllLesson();

    /**
     * 根据专业id获取课程
     *
     * @param majorId
     * @return
     */
    List<LessonDO> getLessonsByMajorId(Integer majorId);

    List<LessonDO> getLessonsByCollegeId(Integer collegeId);

    List<LessonDO> getLessonsByCondition(LessonQuery lessonQuery);

    /**
     * 根据学生id查询课程信息
     *
     * @param stuId
     * @return
     */
    List<LessonDO> getLessonsByStuId(Long stuId);

    /**
     * 根据教师工号查询课程信息
     *
     * @param teacherId
     * @return
     */
    List<LessonDO> getLessonsByTeacherId(Long teacherId);

    /**
     * 学生选课
     */
    void selectLesson(LessonMO lessonMO);

    /**
     * 学生退课
     */
    void dropLesson(LessonMO lessonMO);

    void closeLesson();
}
