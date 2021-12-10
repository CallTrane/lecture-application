package com.lecture.infr.gateway.rabbitmq.mo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

/**
 * @className: LessonMO
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/4 19:45
 */
@Data
@AllArgsConstructor
public class LessonMO implements Serializable {
    private String lessonKey;
    private Integer lessonId;
    private String studentKey;
    private Long stuId;
    private String teacherKey;
    private Long teacherId;
}
