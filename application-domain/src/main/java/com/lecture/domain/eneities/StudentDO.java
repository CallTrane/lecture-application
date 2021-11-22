package com.lecture.domain.eneities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: StudentDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/16 23:34
 */
@TableName("student")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDO {

    /**
     * 学号
     */
    @TableId(value = "stu_id", type = IdType.AUTO)
    private Long stuId;

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 名字
     */
    private String name;

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
