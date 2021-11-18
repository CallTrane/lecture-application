package com.lecture.vo;

import lombok.Data;

/**
 * @className: LessonVO
 * @description: 课程信息
 * @author: carl
 * @date: 2021/11/18 12:11
 */
@Data
public class LessonVO {
    /**
     * 校区
     */
    private String campus;

    /**
     * 课程名
     */
    private String lessonName;

    /**
     * 星期几
     */
    private String weekday;

    /**
     * 当天第几节
     */
    private String classes;

    /**
     * 教师名字
     */
    private String teacherName;
}
