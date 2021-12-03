package com.lecture.infr.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: LessonQueryDTO
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/3 0:59
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonQuery {

    /**
     * 校区
     */
    private Integer campusId;

    /**
     * 必修 / 选修
     */
    private Integer required;

    /**
     * 课程名称
     */
    private Integer lessonName;

    /**
     * 教师名字
     */
    private String teacherName;

    private Integer collegeId;

    private Integer majorId;


}
