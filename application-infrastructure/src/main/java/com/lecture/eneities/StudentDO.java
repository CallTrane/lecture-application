package com.lecture.eneities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @className: StudentDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/16 23:34
 */
@TableName("student")
@Data
public class StudentDO {

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 学号
     */
    @TableId(value = "stu_id", type = IdType.AUTO)
    private Long stuId;

    /**
     * 校区id
     */
    private Integer campusId;

    /**
     * 学院id
     */
    private Integer collegeId;

    /**
     * 专业id
     */
    private Integer majorId;

    /**
     * 入学年份
     */
    private Integer enrollment;

    /**
     * 毕业年份
     */
    private Integer graduation;
}
