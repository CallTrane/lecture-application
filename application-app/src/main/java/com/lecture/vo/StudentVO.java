package com.lecture.vo;

import lombok.Data;

/**
 * @className: StudentVO
 * @description: TODO
 * @author: Carl
 * @date: 2021/11/18 15:39
 */
@Data
public class StudentVO {
    /**
     * 学生学号
     */
    private Long studentId;

    /**
     * 学生姓名
     */
    private String studentName;
}
