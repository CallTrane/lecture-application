package com.lecture.domain.entities;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: LessonDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 1:58
 */
@TableName("lesson")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LessonDO {

    /**
     * 课程id
     */
    @TableId(value = "l_id", type = IdType.AUTO)
    private Integer lId;

    /**
     * 课程名字
     */
    private String name;

    /**
     * 星期几的课
     */
    private Integer weekday;

    /**
     * 当天第几节
     */
    private Integer classes;

    /**
     * 学院id
     */
    private Integer collegeId;

    /**
     * 专业id （如果为0，则表示该课程面向整个学院）
     */
    private Integer majorId;

    /**
     * 教师名字
     */
    private String teacherName;

    /**
     * 学年
     */
    private Integer period;

    /**
     * 学分
     */
    private Integer credit;

    /**
     * 剩余人数
     */
    private Integer remainPeople;

    /**
     * 总人数
     */
    private Integer totalPeople;

    /**
     * 1为必修，0为选修
     */
    private Integer required;

    /**
     * 校区id
     */
    private Integer campusId;
}
