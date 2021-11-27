package com.lecture.app.vo;

/**
 * @classname: StudentLessonVO
 * @description: TODO
 * @date: 2021/11/26 19:01
 * @author: Carl
 */
public class StudentLessonVO {
    /**
     * 校区名字
     */
    private String campusName;

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
     * 教师信息
     */
    private TeacherVO teacherVO;
}
