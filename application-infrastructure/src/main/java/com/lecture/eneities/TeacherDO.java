package com.lecture.eneities;

import lombok.Data;

/**
 * @className: TeacherDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:49
 */
@Data
public class TeacherDO {

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 教师工号
     */
    private Long tId;

    /**
     * 教师名字
     */
    private String name;

    /**
     * 学院id
     */
    private Integer collegeId;
}
