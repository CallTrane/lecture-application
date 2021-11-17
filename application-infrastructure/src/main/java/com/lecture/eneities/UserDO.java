package com.lecture.eneities;

import lombok.Data;

/**
 * @className: UserDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/16 0:41
 */
@Data
public class UserDO {

    /**
     * 用户唯一uid
     */
    private Integer uid;

    /**
     * 账户
     */
    private String account;

    /**
     * 密码
     */
    private String password;

    /**
     * 类型
     */
    private Integer type;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;
}
