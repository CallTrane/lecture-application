package com.lecture.app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;


/**
 * @className: UserRegisterDTO
 * @description: TODO
 * @author: Carl
 * @date: 2021/11/20 17:41
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRegisterDTO {

    /**
     * 账户
     */
    @NonNull
    private String account;

    /**
     * 密码
     */
    @NonNull
    private String password;

    /**
     * 姓名
     */
    @NonNull
    private String name;

    /**
     * 类型
     */
    @NonNull
    private Integer type;

    /**
     * 手机号
     */
    @NonNull
    private String phone;

    /**
     * 邮箱
     */
    @NonNull
    private String email;

    /**
     * 学院id
     */
    @NonNull
    private Integer collegeId;

    /**
     * 专业id（注册学生需要传）
     */
    private Integer majorId;

    /**
     * 校区id
     */
    @NonNull
    private Integer campusId;

    /**
     * 入学年份
     */
    @NonNull
    private String year;
}
