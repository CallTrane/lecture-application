package com.lecture.eneities;

import lombok.Data;

/**
 * @className: CollegeMajorDO
 * @description: TODO
 * @author: carl
 * @date: 2021/11/17 12:01
 */
@Data
public class CollegeMajorDO {

    /**
     * 学院id
     */
    private Integer collegeId;

    /**
     * 学院名称
     */
    private String collegeName;

    /**
     * 专业id
     */
    private Integer majorId;

    /**
     * 专业名称
     */
    private String majorName;
}
