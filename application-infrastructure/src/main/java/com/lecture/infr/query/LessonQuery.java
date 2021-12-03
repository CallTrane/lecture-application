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
public class LessonQuery extends BaseQuery {

    /**
     * 校区
     */
    private Integer campusId;

    /**
     * 必修1 / 选修0
     */
    private Integer required;

    /**
     * 课程名称
     */
    private String lessonName;

    /**
     * 教师名字
     */
    private String teacherName;

    /**
     * 星期几
     */
    private Integer weekday;

    private Integer collegeId;

    /**
     * 专业id
     */
    private Integer majorId;


}
