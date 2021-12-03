package com.lecture.infr.gateway;

import com.lecture.domain.entities.LessonDO;
import com.lecture.infr.query.LessonQuery;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @className: LessonGateway
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/2 14:24
 */
public interface LessonGateway {

    List<LessonDO> getAllLessons();

    /**
     * 获取学院对应课程
     *
     * @return
     */
    Map<Integer, List<LessonDO>> getCollegeLessonsMap();

    /**
     * 获取专业对应课程
     *
     * @return
     */
    Map<Integer, List<LessonDO>> getMajorLessonsMap();

    /**
     * 根据专业id获取课程
     *
     * @param majorId
     * @return
     */
    List<LessonDO> getLessonsByMajorId(Integer majorId);

    List<LessonDO> getLessonsByCollegeId(Integer collegeId);

    List<LessonDO> getLessonsByCondition(LessonQuery lessonQuery);

    Optional<List<LessonDO>> getLessonsByIds(List<Integer> ids);

    /**
     * 根据学生id查询课程信息
     *
     * @param stuId
     * @return
     */
    List<LessonDO> getLessonsByStuId(Long stuId);

    /**
     * 学生选课
     *
     * @param lessonId
     * @param stuId
     */
    void selectLesson(Integer lessonId, Long stuId);

    /**
     * 学生退课
     *
     * @param lessonId
     * @param stuId
     */
    void dropLesson(Integer lessonId, Long stuId);
}
