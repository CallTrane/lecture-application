package com.lecture.vo;

import lombok.Data;

import java.util.List;

/**
 * @className: StudentLessonVO
 * @description: 学生所选课程信息
 * @author: carl
 * @date: 2021/11/18 12:01
 */
@Data
public class StudentLessonVO {
    /**
     * 学生学号
     */
    private Long studentId;

    /**
     * 学生名字
     */
    private String studentName;

    /**
     * 课程信息
     */
    private List<LessonVO> lessonVOs;
}
