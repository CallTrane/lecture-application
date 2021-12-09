package com.lecture.domain.aggregates.lesson;

import com.lecture.domain.entities.LessonDO;

import java.util.ArrayList;
import java.util.List;

/**
 * @className: LessonAggregate
 * @description: TODO
 * @author: Carl Tong
 * @date: 2021/12/2 14:21
 */
public class LessonAggregate {

    public static final Integer MAX_CREDIT = 35;

    public static Integer allLessonsCount;
    public static List<LessonDO> allLessons = new ArrayList<>(16);
}
