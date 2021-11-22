package com.lecture.app.vo;

import lombok.Data;

/**
 * @className: UserLoginVO
 * @description: TODO
 * @author: Carl
 * @date: 2021/11/18 12:44
 */
@Data
public class UserLoginVO {
    /**
     * 用户uid
     */
    private Integer uid;

    /**
     * token
     */
    private String token;

    /**
     * 学生学号 / 教师工号
     */
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 权限
     */
    private Integer permissions;
}
