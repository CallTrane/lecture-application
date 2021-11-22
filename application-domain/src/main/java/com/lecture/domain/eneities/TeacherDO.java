package com.lecture.domain.eneities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: TeacherDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 11:49
 */
@TableName("teacher")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherDO {

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 教师工号
     */
    @TableId(value = "t_id", type = IdType.AUTO)
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
