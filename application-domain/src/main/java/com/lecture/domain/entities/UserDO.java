package com.lecture.domain.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: UserDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/16 0:41
 */
@TableName("user")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDO {

    /**
     * 用户唯一uid
     */
    @TableId(value = "uid", type = IdType.AUTO)
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

    /**
     * 入学年份
     */
    private String year;
}
