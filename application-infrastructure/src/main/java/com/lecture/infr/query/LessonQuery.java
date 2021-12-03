package com.lecture.infr.query;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @className: LessonQueryDTO
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/3 0:59
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
public class LessonQuery {

    private Integer campusId;

    private Integer collegeId;

    private Integer majorId;

    private Integer required;
}
