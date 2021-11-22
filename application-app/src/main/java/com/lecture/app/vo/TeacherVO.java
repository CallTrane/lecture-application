package com.lecture.app.vo;

import lombok.Data;

/**
 * @className: TeacherVO
 * @description: TODO
 * @author: Carl
 * @date: 2021/11/18 15:42
 */
@Data
public class TeacherVO {
    /**
     * 教师工号
     */
    private Long teacherId;

    /**
     * 教师姓名
     */
    private String teacherName;
}
