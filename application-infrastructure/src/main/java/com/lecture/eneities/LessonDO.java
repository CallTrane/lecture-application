package com.lecture.eneities;

import lombok.Data;

/**
 * @className: LessonDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 1:58
 */
@Data
public class LessonDO {

    /**
     * 课程id
     */
    private Integer lId;

    /**
     * 课程名字
     */
    private String name;

    /**
     * 星期几的课
     */
    private Integer weekday;

    /**
     * 当天第几节
     */
    private Integer classes;

    /**
     * 学院id
     */
    private Integer collegeId;

    /**
     * 专业id （如果为0，则表示该课程面向整个学院）
     */
    private Integer majorId;

    /**
     * 教师id
     */
    private Long tId;

    /**
     * 学年
     */
    private Integer period;
}
